package com.demo.community.controller;

import com.demo.community.dto.UserStarsDTO;
import com.demo.community.entity.LikeStar;
import com.demo.community.entity.Question;
import com.demo.community.entity.User;
import com.demo.community.sercive.QuestionService;
import com.demo.community.sercive.StarService;
import com.demo.community.sercive.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    private QuestionService questionService;
    @Autowired
    private StarService starService;
    @Autowired
    private UserService userService;
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


        model.addAttribute("section","personal");
        return "myProfile";
    }
//  个人收藏
    @GetMapping("/stars")
    public String stars(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        List<LikeStar> stars = starService.selectStar(user);

        List<UserStarsDTO> list = new ArrayList<>();

        for (LikeStar star : stars){
            UserStarsDTO userStarsDTO = new UserStarsDTO();
            Question question = questionService.getQuestionById(Math.toIntExact(star.getTarget_id()));
            User creator = userService.getUserByUid(question.getCreator());

            userStarsDTO.setStar_time(star.getGmt_create());
            userStarsDTO.setUser(creator);
            userStarsDTO.setId(question.getId());
            userStarsDTO.setTitle(question.getTitle());
            userStarsDTO.setDescription(question.getDescription());
            userStarsDTO.setComment_count(question.getComment_count());
            userStarsDTO.setView_count(question.getView_count());
            userStarsDTO.setGmt_create(question.getGmt_create());

            list.add(userStarsDTO);
        }

        model.addAttribute("stars",list);
        model.addAttribute("section","star");
        return "myStars";
    }
}
