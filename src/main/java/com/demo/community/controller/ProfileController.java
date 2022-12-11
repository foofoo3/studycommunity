package com.demo.community.controller;

import com.demo.community.dto.PaginationDTO;
import com.demo.community.entity.User;
import com.demo.community.sercive.NotificationService;
import com.demo.community.sercive.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * @author foofoo3
 */
@Controller
public class ProfileController {

    @Resource
    private QuestionService questionService;
    @Resource
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action, Model model,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size,
                          HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user==null) {
            try {
                response.sendRedirect("/resultLogin?=3");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if ("questions".equals(action)){
//            我的提问列表
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            if (user!=null){
                PaginationDTO paginationDTO = questionService.list(user.getUid(),page,size);
                model.addAttribute("pagination",paginationDTO);
            }

        }else if ("replies".equals(action)){
//            我的回复列表
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            if (user!=null) {
                PaginationDTO paginationDTO = notificationService.list(user.getUid(), page, size);
                model.addAttribute("pagination", paginationDTO);
            }
        }
        return "profile";
    }
}
