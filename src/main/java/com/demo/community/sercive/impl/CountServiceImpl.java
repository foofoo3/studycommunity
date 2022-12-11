package com.demo.community.sercive.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.community.entity.Comment;
import com.demo.community.entity.Question;
import com.demo.community.mapper.CommentMapper;
import com.demo.community.mapper.QuestionMapper;
import com.demo.community.sercive.CountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: foofoo3
 */
@Service
public class CountServiceImpl implements CountService {

    @Resource
    private QuestionMapper questionMapper;
    @Resource
    public CommentMapper commentMapper;

    @Override
    public int getQuestionCountByUser(int uid) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("creator",uid);
        return Math.toIntExact(questionMapper.selectCount(wrapper));
    }

    @Override
    public int getQuestionLikeCountByUser(int uid) {
        int likes = 0;
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("creator",uid);
        List<Question> questions = questionMapper.selectList(wrapper);
        for (Question question : questions){
            likes += question.getLike_count();
        }
        return likes;
    }

    @Override
    public int getQuestionStarCountByUser(int uid) {
        int stars = 0;
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("creator",uid);
        List<Question> questions = questionMapper.selectList(wrapper);
        for (Question question : questions){
            stars += question.getStar_count();
        }
        return stars;
    }

    @Override
    public int getCommentLikeCountByUser(int uid) {
        int likes = 0;
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("commentator",uid);
        List<Comment> comments = commentMapper.selectList(wrapper);
        for (Comment comment: comments){
            likes += comment.getLike_count();
        }
        return likes;
    }
}
