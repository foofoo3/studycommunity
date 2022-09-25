package com.demo.community.sercive;

import com.demo.community.dto.CommentDTO;
import com.demo.community.entity.Comment;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    public void creat(Comment comment) {
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(comment.getGmt_create());
//        如果回复的问题不存在则抛异常
        if (comment.getParent_id() == null || comment.getParent_id() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        commentMapper.creat(comment);
    }
}
