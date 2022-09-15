package com.demo.community.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Controller
public class MenuController {

    @GetMapping("/LogOut")
    public String logOut(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "LogOut";
    }

}
