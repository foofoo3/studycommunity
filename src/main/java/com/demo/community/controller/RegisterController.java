package com.demo.community.controller;

import com.demo.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author foofoo3
 */
@Slf4j
@Controller
public class RegisterController {

    @Resource
    private UserService userService;

    @GetMapping("/resultRegister")
    public String success(){ return "resultRegister"; }


    @PostMapping("/insertUser")
    public void insertUser(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        int number = Integer.parseInt(request.getParameter("number"));
        String password = request.getParameter("password");

        int resultRegister = userService.insertUser(name, number, password);

        try {
            response.sendRedirect("/resultRegister?="+resultRegister);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

