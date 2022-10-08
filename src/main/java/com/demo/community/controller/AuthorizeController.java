package com.demo.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author foofoo3
 */
@Controller
public class AuthorizeController {
    @GetMapping("/LogOut")
    public String logOut(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "LogOut";
    }
}
