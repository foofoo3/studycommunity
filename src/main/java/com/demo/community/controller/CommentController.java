package com.demo.community.controller;

import com.demo.community.dto.CommentCreatDTO;
import com.demo.community.dto.ResultDTO;
import com.demo.community.entity.Comment;
import com.demo.community.entity.User;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.sercive.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreatDTO commentCreatDTO, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreatDTO == null || StringUtils.isBlank(commentCreatDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParent_id(commentCreatDTO.getParentId());
        comment.setContent(commentCreatDTO.getContent());
        comment.setType(commentCreatDTO.getType());
        comment.setCommentator(user.getUid());
        commentService.creat(comment);
        return ResultDTO.okOf();
    }
}
