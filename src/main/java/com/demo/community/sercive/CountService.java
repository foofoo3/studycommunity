package com.demo.community.sercive;

import com.demo.community.mapper.CommentMapper;
import com.demo.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: foofoo3
 */
@Service
public class CountService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    public CommentMapper commentMapper;

    public int getQuestionCountByUser(int uid) {
        return questionMapper.countByUid(uid);
    }

    public int getQuestionLikeCountByUser(int uid) {
        int likes = 0;
        List<Integer> integers = questionMapper.selectListLikeCountByUid(uid);
        for (Integer like : integers){
            likes += like;
        }
        return likes;
    }

    public int getQuestionStarCountByUser(int uid) {
        int stars = 0;
        List<Integer> integers = questionMapper.selectListStarCountByUid(uid);
        for (Integer star : integers){
            stars += star;
        }
        return stars;
    }

    public int getCommentLikeCountByUser(int uid) {
        int likes = 0;
        List<Integer> integers = commentMapper.selectListLikeCountByUid(uid);
        for (Integer like : integers){
            likes += like;
        }
        return likes;
    }
}
