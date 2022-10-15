package com.demo.community.controller;

import com.demo.community.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author foofoo3
 */

@Controller
public class MenuController {
//    登录
    @GetMapping("/login")
    public String login(){ return "login"; }

//    退出登录
    @GetMapping("/LogOut")
    public String logOut(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "LogOut";
    }
//个人资料
    @GetMapping("/personal")
    public String personal(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        return "personal";
    }
//收藏
    @GetMapping("/stars")
    public String stars(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        return "stars";
    }
}
