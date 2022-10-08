package com.demo.community.controller;

import com.demo.community.dto.NotificationDTO;
import com.demo.community.dto.PaginationDTO;
import com.demo.community.entity.User;
import com.demo.community.enums.NotificationTypeEnum;
import com.demo.community.sercive.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * @author foofoo3
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id")Long id,HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user==null) {
            try {
                response.sendRedirect("/resultLogin?=3");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        NotificationDTO notificationDTO = notificationService.read(id,user);

        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
            || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()){
            return "redirect:/question/" + notificationDTO.getOuterId();
        }else {
            return "redirect:/";
        }

    }
}
