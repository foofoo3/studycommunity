package com.demo.community.controller;

import com.demo.community.dto.PaginationDTO;
import com.demo.community.entity.User;
import com.demo.community.sercive.QuestionService;
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

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;

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

        PaginationDTO paginationDTO = null;
        if (user != null) {
            paginationDTO = questionService.list(user.getUid(),page,size);
        }
        model.addAttribute("pagination",paginationDTO);

        return "profile";
    }
}
