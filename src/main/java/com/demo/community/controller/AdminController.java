package com.demo.community.controller;

import com.demo.community.dto.ResultDTO;
import com.demo.community.entity.Admin;
import com.demo.community.entity.User;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.sercive.AdminService;
import com.demo.community.sercive.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: foofoo3
 */
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @PostMapping("/loginadmin")
    public void loginadmin(@RequestParam("name") String name,
                             @RequestParam("password") String password,
                             HttpServletRequest request,
                             HttpServletResponse response){

        int res = adminService.loginAdmin(name, password);

        if (res == 1){
            Admin admin = adminService.selectadminByname(name);
            request.getSession().setAttribute("admin",admin);
//            如果有用户账户在登录状态，则退出登录
            if (request.getSession().getAttribute("user") != null){
                request.getSession().removeAttribute("user");
            }
            try {
                response.sendRedirect("/resultLogin?="+res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                response.sendRedirect("/resultLogin?="+res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @GetMapping("/logOutAdmin")
    public String logOutAdmin(HttpServletRequest request){
        request.getSession().removeAttribute("admin");
        return "logOut";
    }

    @GetMapping("/userManagement")
    public String userManagement(HttpServletRequest request, Model model){
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin == null){
            throw new CustomizeException(CustomizeErrorCode.ADMIN_NOT_FOUND);
        }
        List<User> blockingUsers = userService.getUserByType();
        model.addAttribute("blockingUsers",blockingUsers);
        return "userManagement";
    }

    @ResponseBody
    @PostMapping("/ban/{uid}")
    public Object banUser(@PathVariable("uid")int uid){
        int res = userService.banUser(uid);
        if (res != 0){
            return ResultDTO.okOf();
        }else {
            return ResultDTO.errorOf(9000,"封禁用户失败");
        }
    }

    @ResponseBody
    @PostMapping("/unban/{uid}")
    public Object unbanUser(@PathVariable("uid")int uid){
        int res = userService.unbanUser(uid);
        if (res != 0){
            return ResultDTO.okOf();
        }else {
            return ResultDTO.errorOf(9001,"解封用户失败");
        }
    }

    @ResponseBody
    @PostMapping("/cancellation/{uid}")
    public Object cancellationUser(@PathVariable("uid")int uid){
        int res = userService.cancellationUser(uid);
        if (res != 0){
            return ResultDTO.okOf();
        }else {
            return ResultDTO.errorOf(9002,"删除用户失败");
        }
    }

    @GetMapping("/publicAnnouncement")
    public String publicAnnouncement(HttpServletRequest request){
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin == null){
            throw new CustomizeException(CustomizeErrorCode.ADMIN_NOT_FOUND);
        }
        return "publicAnnouncement";
    }

    @PostMapping("/publishAnnouncement")
    public void publishAnnouncement(@RequestParam("id") int id,
                                    @RequestParam("announcement") String announcement,
                                    HttpServletResponse response){
        adminService.updateAnnouncement(id,announcement);
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
