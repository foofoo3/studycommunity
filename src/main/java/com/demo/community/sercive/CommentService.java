package com.demo.community.sercive;

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

    List<CommentDTO> listByParentId(int id, CommentTypeEnum comment, int i);

    Comment getCommentById(Long id);

    int deleteCommentById(Long id, int type);
}
