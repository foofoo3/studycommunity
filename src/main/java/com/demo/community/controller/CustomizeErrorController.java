package com.demo.community.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CustomizeErrorController implements ErrorController {
    private static final String ERROR_PATH = "/error";
//    判断400,500 错误
    @RequestMapping(value=ERROR_PATH)
    public String handleError(HttpServletResponse response, Model model){
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        if (status.is4xxClientError()){
            model.addAttribute("message","请求出错，请换个请求重试");
        }if (status.is5xxServerError()){
            model.addAttribute("message","服务器出错，请稍后重试");
        }
        return "/error";
    }

}
