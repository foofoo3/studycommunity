package com.demo.community.controller;

import com.demo.community.dto.CommentCreatDTO;
import com.demo.community.dto.CommentDTO;
import com.demo.community.dto.ResultDTO;
import com.demo.community.entity.Admin;
import com.demo.community.entity.Comment;
import com.demo.community.entity.User;
import com.demo.community.enums.CommentTypeEnum;
import com.demo.community.enums.NotificationTypeEnum;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.sercive.CommentService;
import com.demo.community.sercive.NotificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
/**
 * @author foofoo3
 */
@Controller
public class CommentController {

    @Resource
    private CommentService commentService;
    @Resource
    private NotificationService notificationService;

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
        commentService.creat(comment,user);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTOS = commentService.listByParentId(id, CommentTypeEnum.COMMENT,0);
        return ResultDTO.okOf(commentDTOS);
    }

    @ResponseBody
    @PostMapping("/deleteComment/{id}")
    public Object deleteComment(@PathVariable(name = "id")Long id,HttpServletRequest request){
        Comment comment = commentService.getCommentById(id);
//        ????????????
        int type = comment.getType();
        Admin admin = (Admin) request.getSession().getAttribute("admin");

//        ????????????
        int res = commentService.deleteCommentById(id,type);

//        ???????????????????????????????????????????????? ???????????????
        if (admin != null && type == 1){
            notificationService.createAdminNotify(admin.getId(),comment.getParent_id(),comment.getCommentator(),admin.getName(),comment.getContent(), NotificationTypeEnum.DELETE_COMMENT);
        }
//        ??????????????????
        Comment dbcomment = commentService.getCommentById((long) comment.getParent_id());
        if (admin != null && type == 2){
            notificationService.createAdminNotify(admin.getId(),dbcomment.getParent_id(),comment.getCommentator(),admin.getName(),comment.getContent(), NotificationTypeEnum.DELETE_SECONDCOMMENT);
        }

        if (res != 0){
            return ResultDTO.okOf();
        }else {
            return ResultDTO.errorOf(6000,"??????????????????");
        }
    }
}
