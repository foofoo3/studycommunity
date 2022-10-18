package com.demo.community.controller;

import com.demo.community.dto.ResultDTO;
import com.demo.community.entity.LikeStar;
import com.demo.community.sercive.LikeService;
import com.demo.community.sercive.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Author: foofoo3
 */
@Controller
public class LikeStarController {
    @Autowired
    private LikeService likeService;
    @Autowired
    private StarService starService;
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

    @ResponseBody
    @RequestMapping(value = "/commentLikeReduce",method = RequestMethod.POST)
    public Object likeReduce(@RequestBody LikeStar likeStar){
        int i = likeService.commentLikeReduce(likeStar.getTarget_id(),likeStar.getUid());
        if (i == 1){
            return ResultDTO.okOf();
        }else {
            return ResultDTO.errorOf(5201,"取消点赞失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/questionLike",method = RequestMethod.POST)
    public Object questionLike(@RequestBody LikeStar likeStar){
        int i = likeService.questionLikePlus(likeStar.getTarget_id(),likeStar.getUid());
        if (i == 1){
            return ResultDTO.okOf();
        }else {
            return ResultDTO.errorOf(5200,"点赞失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/questionLikeReduce",method = RequestMethod.POST)
    public Object questionLikeReduce(@RequestBody LikeStar likeStar){
        int i = likeService.questionLikeReduce(likeStar.getTarget_id(),likeStar.getUid());
        if (i == 1){
            return ResultDTO.okOf();
        }else {
            return ResultDTO.errorOf(5201,"取消点赞失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/questionStar",method = RequestMethod.POST)
    public Object questionStar(@RequestBody LikeStar likeStar){
        int i = starService.questionStar(likeStar.getTarget_id(),likeStar.getUid());
        if (i == 1){
            return ResultDTO.okOf();
        }else {
            return ResultDTO.errorOf(5200,"点赞失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/questionStarCancel",method = RequestMethod.POST)
    public Object questionStarCancel(@RequestBody LikeStar likeStar){
        int i = starService.questionStarCancel(likeStar.getTarget_id(),likeStar.getUid());
        if (i == 1){
            return ResultDTO.okOf();
        }else {
            return ResultDTO.errorOf(5201,"取消点赞失败");
        }
    }
}
