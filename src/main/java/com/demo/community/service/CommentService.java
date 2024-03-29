package com.demo.community.service;

import com.demo.community.dto.CommentDTO;
import com.demo.community.entity.Comment;
import com.demo.community.entity.User;
import com.demo.community.enums.CommentTypeEnum;

import java.util.List;

/**
 * @Author: foofoo3
 */
public interface CommentService {

    void creat(Comment comment, User user);

    List<CommentDTO> listByParentId(Long id, CommentTypeEnum comment, int i);

    Comment getCommentById(Long id);

    int deleteCommentById(Long id, int type);
}
