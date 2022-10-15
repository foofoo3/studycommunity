package com.demo.community.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
/**
 * @author foofoo3
 */

@Controller
public class MenuController {
    //    登录
    @GetMapping("/login")
    public String login(){ return "login"; }

}
