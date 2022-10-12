package com.demo.community.controller;

import com.demo.community.dto.CommentCreatDTO;
import com.demo.community.dto.ResultDTO;
import com.demo.community.entity.Comment;
import com.demo.community.entity.LikeStar;
import com.demo.community.entity.User;
import com.demo.community.sercive.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: foofoo3
 */
@Controller
public class LikeStarController {
    @Autowired
    private LikeService likeService;

    @ResponseBody
    @RequestMapping(value = "/commentLike",method = RequestMethod.POST)
    public Object like(@RequestBody LikeStar likeStar){
        int i = likeService.commentLikePlus(likeStar.getTarget_id(),likeStar.getUid());
        if (i == 1){
            return ResultDTO.okOf();
        }else {
            return ResultDTO.errorOf(5200,"点赞失败");
        }
    }
}
