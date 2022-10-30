package com.demo.community.controller;

import com.demo.community.entity.Admin;
import com.demo.community.entity.User;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.sercive.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author foofoo3
 */
@Slf4j
@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(){ return "registration"; }

    @GetMapping("/resultLogin")
    public String resultLogin(){ return "resultLogin"; }

    @PostMapping("/loginuser")
    public void loginuser(HttpServletRequest request, HttpServletResponse response){
        int number = Integer.parseInt(request.getParameter("number"));
        String password = request.getParameter("password");

        int resultLogin = userService.loginUser(number,password);

        if (resultLogin==1){
            User user = userService.getUserByNumber(number);
//            Cookie userCookie = new Cookie("userCookie", user.getName() + "loginCookie");
//            response.addCookie(userCookie);
            if (user.getType() == 1){
//                如果管理员用户存在则退出
                Admin admin = (Admin) request.getSession().getAttribute("admin");
                if (admin != null){
                    request.getSession().removeAttribute("admin");
                }
                request.getSession().setAttribute("user",user);
            }else {
                resultLogin = 5;
            }
        }

        try {
            response.sendRedirect("/resultLogin?="+resultLogin);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
