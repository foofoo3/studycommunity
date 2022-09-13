package com.demo.community.controller;

import com.demo.community.entity.User;
import com.demo.community.sercive.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/Registration")
    public String Registration(){ return "Registration"; }

    @GetMapping("/resultLogin")
    public String resultLogin(){ return "resultLogin"; }

    @GetMapping("/loginuser")
    public void loginuser(HttpServletRequest request, HttpServletResponse response){
        int number = Integer.parseInt(request.getParameter("number"));
        String password = request.getParameter("password");

        int resultLogin = userService.loginUser(number,password);

        if (resultLogin==1){
            User user = userService.getUserByNumber(number);
//            Cookie userCookie = new Cookie("userCookie", user.getName() + "loginCookie");
//            response.addCookie(userCookie);
            request.getSession().setAttribute("user",user);
        }
        try {
            response.sendRedirect("/resultLogin?="+resultLogin);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
