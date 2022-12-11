package com.demo.community.controller;

import com.demo.community.entity.User;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.sercive.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: foofoo3
 */
@Slf4j
@Controller
public class UserProfileController {

    @Resource
    private UserService userService;

    @PostMapping("/userProfile")
    public void userProfile(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword1 = request.getParameter("newPassword1");
        String newPassword2 = request.getParameter("newPassword2");
        if (user == null){
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        int i = userService.updateUser(user,name,description,oldPassword,newPassword1,newPassword2,response);

        if (i != 0){
            try {
                response.sendRedirect("resultRegister?=9");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                response.sendRedirect("resultRegister?=7");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
