package com.demo.community.controller;

import com.demo.community.dto.PaginationDTO;
import com.demo.community.dto.UserStarsDTO;
import com.demo.community.entity.LikeStar;
import com.demo.community.entity.Question;
import com.demo.community.entity.User;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.sercive.QuestionService;
import com.demo.community.sercive.StarService;
import com.demo.community.sercive.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author foofoo3
 */

@Controller
public class MenuController {
    @Autowired
    private StarService starService;

//  登录
    @GetMapping("/login")
    public String login(){ return "login"; }

//  退出登录
    @GetMapping("/logOut")
    public String logOut(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "logOut";
    }
//  个人资料
    @GetMapping("/personal")
    public String personal(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("section","personal");
        return "myProfile";
    }
//  个人收藏
    @GetMapping("/star")
    public String stars(HttpServletRequest request,Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if (user == null){
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        PaginationDTO paginationDTO = starService.userStarList(user.getUid(),page, size);

        model.addAttribute("pagination",paginationDTO);
        model.addAttribute("section","star");
        return "myStars";
    }

    @GetMapping("/hotQuestion/{byTime}")
    public String hotQuestion(@PathVariable(name = "byTime",required = false) Integer byTime, Model model){



        model.addAttribute("byTime",byTime);
        return "hotQuestion";
    }
}
