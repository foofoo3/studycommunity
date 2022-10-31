package com.demo.community.controller;

import com.demo.community.dto.NotificationDTO;
import com.demo.community.dto.ResultDTO;
import com.demo.community.entity.User;
import com.demo.community.enums.NotificationTypeEnum;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.sercive.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * @author foofoo3
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id")Long id,HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user==null) {
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }

        NotificationDTO notificationDTO = notificationService.read(id,user);

        if (   NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
            || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()
            || NotificationTypeEnum.DELETE_COMMENT.getType() == notificationDTO.getType()
            || NotificationTypeEnum.DELETE_QUESTION.getType()== notificationDTO.getType()
            || NotificationTypeEnum.DELETE_SECONDCOMMENT.getType()== notificationDTO.getType()
        ){
            return "redirect:/question/" + notificationDTO.getOuterId();
        }else {
            return "redirect:/";
        }
    }

    @ResponseBody
    @PostMapping("/deleteNotification/{id}")
    public Object deleteNotification(@PathVariable(name = "id")Integer id){
        int res = notificationService.deleteNotificationById(id);
        if (res != 0){
            return ResultDTO.okOf();
        }else {
            return ResultDTO.errorOf(6000,"删除通知失败");
        }
    }
}
