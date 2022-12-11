package com.demo.community.sercive.impl;

import com.demo.community.entity.Comment;
import com.demo.community.entity.LikeStar;
import com.demo.community.entity.Question;
import com.demo.community.entity.User;
import com.demo.community.enums.LikeOrStarTypeEnum;
import com.demo.community.mapper.CommentMapper;
import com.demo.community.mapper.LikeStarMapper;
import com.demo.community.mapper.QuestionMapper;
import com.demo.community.sercive.LikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: foofoo3
 */
@Service
public class LikeServiceImpl implements LikeService {

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private LikeStarMapper likeStarMapper;
    @Resource
    private QuestionMapper questionMapper;

//    评论增加点赞数
    @Override
    public int commentLikePlus(Long commentId,int uid){
        Comment comment = commentMapper.selectById(commentId);
        comment.setLike_count(comment.getLike_count());
//        评论点赞数加一
        int i = commentMapper.likePlus(comment);
        int success = 0;
        LikeStar likeStar = new LikeStar();
        likeStar.setUid(uid);
        likeStar.setTarget_id(comment.getId());
        likeStar.setType(LikeOrStarTypeEnum.COMMENT_LIKE.getType());
        likeStar.setGmt_create(System.currentTimeMillis());
        likeStar.setParent_id(comment.getParent_id());
        if (i !=0){
//            记录点赞信息
            int insert = likeStarMapper.insert(likeStar);
            if (insert != 0){
                success = 1;
            }
        }
        return success;
    }

    //    评论减少点赞数
    @Override
    public int commentLikeReduce(Long commentId,int uid){
        Comment comment = commentMapper.selectById(commentId);
        comment.setLike_count(comment.getLike_count());
        //        评论点赞数减一
        int i = commentMapper.likeReduce(comment);
        int success = 0;
        if (i != 0){
            int delete = likeStarMapper.deleteLikeOrStar(commentId,uid, LikeOrStarTypeEnum.COMMENT_LIKE.getType());
            if (delete != 0){
                success = 1;
            }
        }
        return success;
    }

    @Override
    public List<Long> selectCommentLike(User user,Integer questionId) {
        return likeStarMapper.selectCommentLikeByUid(user.getUid(),LikeOrStarTypeEnum.COMMENT_LIKE.getType(),questionId);
    }

    //    问题增加点赞数
    @Override
    public int questionLikePlus(Long questionId, int uid) {
        Question question = questionMapper.getQuestionById(Math.toIntExact(questionId));
        question.setLike_count(question.getLike_count());
//        问题点赞数加一
        int i = questionMapper.likePlus(question);
        int success = 0;
        LikeStar likeStar = new LikeStar();
        likeStar.setUid(uid);
        likeStar.setTarget_id((long) question.getId());
        likeStar.setType(LikeOrStarTypeEnum.QUESTION_LIKE.getType());
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

    //    问题减少点赞数
    @Override
    public int questionLikeReduce(Long questionId, int uid) {
        Question question = questionMapper.getQuestionById(Math.toIntExact(questionId));
        question.setLike_count(question.getLike_count());
        //        问题点赞数减一
        int i = questionMapper.likeReduce(question);
        int success = 0;
        if (i != 0){
            int delete = likeStarMapper.deleteLikeOrStar(questionId,uid, LikeOrStarTypeEnum.QUESTION_LIKE.getType());
            if (delete != 0){
                success = 1;
            }
        }
        return success;
    }

    @Override
    public List<Integer> selectQuestionLike(User user) {
        return likeStarMapper.selectQuestionLikeOrStarByUid(user.getUid(),LikeOrStarTypeEnum.QUESTION_LIKE.getType());
    }


}
