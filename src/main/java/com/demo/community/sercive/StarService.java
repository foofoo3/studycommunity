package com.demo.community.sercive;

import com.demo.community.entity.LikeStar;
import com.demo.community.entity.Question;
import com.demo.community.entity.User;
import com.demo.community.enums.LikeOrStarTypeEnum;
import com.demo.community.mapper.LikeStarMapper;
import com.demo.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
