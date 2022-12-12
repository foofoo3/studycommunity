package com.demo.community.sercive.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
import java.util.stream.Collectors;

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
        UpdateWrapper<Comment> likeUpdateWrapper = new UpdateWrapper<>();
        likeUpdateWrapper.setSql("'likeCount' = 'likeCount' + 1");
        int i = commentMapper.update(comment,likeUpdateWrapper);
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
        UpdateWrapper<Comment> likeUpdateWrapper = new UpdateWrapper<>();
        likeUpdateWrapper.setSql("'likeCount' = 'likeCount' - 1");
        int i = commentMapper.update(comment,likeUpdateWrapper);
        int success = 0;
        if (i != 0){
            QueryWrapper<LikeStar> wrapper = new QueryWrapper<>();
            wrapper.eq("targetId",commentId)
                    .eq("uid",uid)
                    .eq("type",LikeOrStarTypeEnum.COMMENT_LIKE.getType());
            int delete = likeStarMapper.delete(wrapper);
            if (delete != 0){
                success = 1;
            }
        }
        return success;
    }

    @Override
    public List<Long> selectCommentLike(User user,Integer questionId) {
        QueryWrapper<LikeStar> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",user.getUid())
                .eq("type",LikeOrStarTypeEnum.COMMENT_LIKE.getType())
                .eq("parentId",questionId);
        List<LikeStar> likeStars = likeStarMapper.selectList(wrapper);
        return likeStars.stream().map(LikeStar::getTarget_id).collect(Collectors.toList());
    }

    //    问题增加点赞数
    @Override
    public int questionLikePlus(Long questionId, int uid) {
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("id",Math.toIntExact(questionId));
        Question question = questionMapper.selectOne(questionQueryWrapper);
        question.setLike_count(question.getLike_count());
//        问题点赞数加一
        UpdateWrapper<Question> questionUpdateWrapper = new UpdateWrapper<>();
        questionUpdateWrapper.setSql("'likeCount' = 'likeCount' + 1");
        int i = questionMapper.update(question,questionUpdateWrapper);
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
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("id",Math.toIntExact(questionId));
        Question question = questionMapper.selectOne(questionQueryWrapper);
        question.setLike_count(question.getLike_count());
        //        问题点赞数减一
        UpdateWrapper<Question> questionUpdateWrapper = new UpdateWrapper<>();
        questionUpdateWrapper.setSql("'likeCount' = 'likeCount' - 1");
        int i = questionMapper.update(question,questionUpdateWrapper);
        int success = 0;
        if (i != 0){
            QueryWrapper<LikeStar> deleteQueryWrapper = new QueryWrapper<>();
            deleteQueryWrapper.eq("targetId",questionId)
                    .eq("uid",uid)
                    .eq("type",LikeOrStarTypeEnum.QUESTION_LIKE.getType());
            int delete = likeStarMapper.delete(deleteQueryWrapper);
            if (delete != 0){
                success = 1;
            }
        }
        return success;
    }

    @Override
    public List<Integer> selectQuestionLike(User user) {
        QueryWrapper<LikeStar> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",user.getUid())
                .eq("type",LikeOrStarTypeEnum.QUESTION_LIKE.getType());
        List<LikeStar> likeStars = likeStarMapper.selectList(wrapper);
        List<Long> collect = likeStars.stream().map(LikeStar::getTarget_id).collect(Collectors.toList());
//        list<Long>转list<Integer>
        return JSONArray.parseArray(collect.toString(), Integer.class);
    }


}
