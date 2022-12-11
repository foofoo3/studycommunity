package com.demo.community.sercive.impl;

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
        return questionMapper.countByUid(uid);
    }

    @Override
    public int getQuestionLikeCountByUser(int uid) {
        int likes = 0;
        List<Integer> integers = questionMapper.selectListLikeCountByUid(uid);
        for (Integer like : integers){
            likes += like;
        }
        return likes;
    }

    @Override
    public int getQuestionStarCountByUser(int uid) {
        int stars = 0;
        List<Integer> integers = questionMapper.selectListStarCountByUid(uid);
        for (Integer star : integers){
            stars += star;
        }
        return stars;
    }

    @Override
    public int getCommentLikeCountByUser(int uid) {
        int likes = 0;
        List<Integer> integers = commentMapper.selectListLikeCountByUid(uid);
        for (Integer like : integers){
            likes += like;
        }
        return likes;
    }
}
