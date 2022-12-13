package com.demo.community.sercive.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.community.dto.PaginationDTO;
import com.demo.community.dto.UserStarsDTO;
import com.demo.community.entity.LikeStar;
import com.demo.community.entity.Question;
import com.demo.community.entity.User;
import com.demo.community.enums.LikeOrStarTypeEnum;
import com.demo.community.mapper.LikeStarMapper;
import com.demo.community.mapper.QuestionMapper;
import com.demo.community.mapper.UserMapper;
import com.demo.community.sercive.StarService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: foofoo3
 */
@Service
public class StarServiceImpl implements StarService {

    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private LikeStarMapper likeStarMapper;
    @Resource
    private UserMapper userMapper;

//    收藏问题
    @Override
    public int questionStar(Long questionId, int uid) {
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("id",Math.toIntExact(questionId));
        Question question = questionMapper.selectOne(questionQueryWrapper);
        question.setStar_count(question.getStar_count());
//        问题收藏数加一
        UpdateWrapper<Question> questionUpdateWrapper = new UpdateWrapper<>();
        questionUpdateWrapper.eq("id",question.getId());
        questionUpdateWrapper.setSql("star_count = star_count + 1");
        int i = questionMapper.update(question,questionUpdateWrapper);
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
    @Override
    public int questionStarCancel(Long questionId, int uid) {
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("id",Math.toIntExact(questionId));
        Question question = questionMapper.selectOne(questionQueryWrapper);
        question.setStar_count(question.getLike_count());
        //        问题收藏数减一
        UpdateWrapper<Question> questionUpdateWrapper = new UpdateWrapper<>();
        questionUpdateWrapper.eq("id",question.getId());
        questionUpdateWrapper.setSql("star_count = star_count - 1");
        int i = questionMapper.update(question,questionUpdateWrapper);
        int success = 0;
        if (i != 0){
            QueryWrapper<LikeStar> deleteQueryWrapper = new QueryWrapper<>();
            deleteQueryWrapper.eq("target_id",questionId)
                    .eq("uid",uid)
                    .eq("type",LikeOrStarTypeEnum.QUESTION_STAR.getType());
            int delete = likeStarMapper.delete(deleteQueryWrapper);
            if (delete != 0){
                success = 1;
            }
        }
        return success;
    }

    @Override
    public List<Integer> selectQuestionStar(User user) {
        QueryWrapper<LikeStar> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",user.getUid())
                .eq("type",LikeOrStarTypeEnum.QUESTION_STAR.getType());
        List<LikeStar> likeStars = likeStarMapper.selectList(wrapper);
        List<Long> collect = likeStars.stream().map(LikeStar::getTarget_id).collect(Collectors.toList());
//        list<Long>转list<Integer>
        return JSONArray.parseArray(collect.toString(), Integer.class);
    }

    @Override
    public PaginationDTO userStarList(int uid, Integer page, Integer size) {
        PaginationDTO<UserStarsDTO> paginationDTO = new PaginationDTO<>();
        int totalPage;
        List<Question> questions = null;
        List<Integer> questionIds = null;
        //        搜索总数
        Integer totalCount;
        QueryWrapper<LikeStar> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",uid)
                .eq("type",LikeOrStarTypeEnum.QUESTION_STAR.getType())
                .orderByDesc("gmt_create");
        List<LikeStar> likeStars = likeStarMapper.selectList(wrapper);
        if (likeStars.size() == 0){
            totalCount = 0;
        }else {
            List<Long> collect = likeStars.stream().map(LikeStar::getTarget_id).collect(Collectors.toList());
            questionIds = JSONArray.parseArray(collect.toString(),Integer.class);
            questions = questionMapper.selectBatchIds(questionIds);
            totalCount = questions.size();
        }


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
        if (totalCount != 0) {
//            QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
//            questionQueryWrapper.eq("id",questionIds);
//            List<Question> starQuestions = questionMapper.selectPage(new Page<>(page,size),questionQueryWrapper).getRecords();
//           分页
            List<Question> starQuestions;
            if (offset + size <= questions.size()){
                starQuestions = questions.subList(offset, offset + size);
            }else {
                starQuestions = questions.subList(offset, questions.size());
            }

            List<UserStarsDTO> userStarsDTOs = new ArrayList<>();

            for (Question question : starQuestions) {
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("uid",question.getCreator());
                User creator = userMapper.selectOne(userQueryWrapper);
                UserStarsDTO userStarsDTO = new UserStarsDTO();
                BeanUtils.copyProperties(question, userStarsDTO);
                userStarsDTO.setUser(creator);
                QueryWrapper<LikeStar> starQueryWrapper = new QueryWrapper<>();
                starQueryWrapper.eq("target_id",(long) question.getId())
                        .eq("uid",uid)
                        .eq("type",LikeOrStarTypeEnum.QUESTION_STAR.getType());
                Long starTime = likeStarMapper.selectOne(starQueryWrapper).getGmt_create();
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
