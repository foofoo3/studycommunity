package com.demo.community.sercive;

import com.demo.community.dto.NotificationDTO;
import com.demo.community.dto.PaginationDTO;
import com.demo.community.dto.QuestionDTO;
import com.demo.community.dto.UserStarsDTO;
import com.demo.community.entity.LikeStar;
import com.demo.community.entity.Question;
import com.demo.community.entity.User;
import com.demo.community.enums.LikeOrStarTypeEnum;
import com.demo.community.mapper.LikeStarMapper;
import com.demo.community.mapper.QuestionMapper;
import com.demo.community.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: foofoo3
 */
@Service
public class StarService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private LikeStarMapper likeStarMapper;
    @Autowired
    private UserMapper userMapper;

//    收藏问题
    public int questionStar(Long questionId, int uid) {
        Question question = questionMapper.getQuestionById(Math.toIntExact(questionId));
        question.setStar_count(question.getStar_count());
//        问题收藏数加一
        int i = questionMapper.StarPlus(question);
        int success = 0;
        LikeStar likeStar = new LikeStar();
        likeStar.setUid(uid);
        likeStar.setTarget_id((long) question.getId());
        likeStar.setType(LikeOrStarTypeEnum.QUESTION_STAR.getType());
        likeStar.setGmt_create(System.currentTimeMillis());
        if (i !=0){
//            记录点赞信息
            int insert = likeStarMapper.insert(likeStar);
            if (insert != 0){
                success = 1;
            }
        }
        return success;
    }

    //  问题取消收藏
    public int questionStarCancel(Long questionId, int uid) {
        Question question = questionMapper.getQuestionById(Math.toIntExact(questionId));
        question.setLike_count(question.getLike_count());
        //        问题点赞数减一
        int i = questionMapper.starCancel(question);
        int success = 0;
        if (i != 0){
            int delete = likeStarMapper.deleteLikeOrStar(questionId,uid, LikeOrStarTypeEnum.QUESTION_STAR.getType());
            if (delete != 0){
                success = 1;
            }
        }
        return success;
    }

    public List<Integer> selectQuestionStar(User user) {
        return likeStarMapper.selectQuestionLikeOrStarByUid(user.getUid(),LikeOrStarTypeEnum.QUESTION_STAR.getType());
    }


    public PaginationDTO userStarList(int uid, Integer page, Integer size) {
        PaginationDTO<UserStarsDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPage;
        //        搜索总数
        Integer totalCount = likeStarMapper.starCountByUid(uid,LikeOrStarTypeEnum.QUESTION_STAR.getType());
        //        计算页码总大小
        if (totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }
        //        超出页数范围判断,防止越界
        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        Integer offset =size *(page - 1);
        int starCount = likeStarMapper.selectLikeOrStarCountByUid(uid, LikeOrStarTypeEnum.QUESTION_STAR.getType());
        if (starCount != 0) {
            List<Question> starquestions = likeStarMapper.starQuestionByUid(uid, LikeOrStarTypeEnum.QUESTION_STAR.getType(), offset, size);
            List<UserStarsDTO> userStarsDTOs = new ArrayList<>();

            for (Question question : starquestions) {
                User creator = userMapper.SelectByUid(question.getCreator());
                UserStarsDTO userStarsDTO = new UserStarsDTO();
                BeanUtils.copyProperties(question, userStarsDTO);
                userStarsDTO.setUser(creator);
                Long starTime = likeStarMapper.selectStarTime((long) question.getId(), uid, LikeOrStarTypeEnum.QUESTION_STAR.getType());
                userStarsDTO.setStar_time(starTime);

                userStarsDTOs.add(userStarsDTO);
            }

            paginationDTO.setData(userStarsDTOs);

            return paginationDTO;

        }else {
            paginationDTO.setData(new ArrayList<>());

            return paginationDTO;
        }
    }
}
