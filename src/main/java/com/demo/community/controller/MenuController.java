package com.demo.community.controller;

import com.demo.community.cache.HotQuestionCache;
import com.demo.community.cache.HotTagCache;
import com.demo.community.dto.*;
import com.demo.community.entity.Admin;
import com.demo.community.entity.Question;
import com.demo.community.entity.User;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.service.AdminService;
import com.demo.community.service.CountService;
import com.demo.community.service.StarService;
import com.demo.community.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author foofoo3
 */

@Controller
public class MenuController {

    @Resource
    private StarService starService;
    @Resource
    private HotQuestionCache hotQuestionCache;
    @Resource
    private UserService userService;
    @Resource
    private HotTagCache hotTagCache;
    @Resource
    private CountService countService;
    @Resource
    private AdminService adminService;

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
        UserCountDTO userCountDTO = new UserCountDTO();

        //        查用户问题数 获赞数 被收藏数
        int questionCountByUser = countService.getQuestionCountByUser(user.getUid());
        int questionLikeCountByUser = countService.getQuestionLikeCountByUser(user.getUid());
        int commentLikeCountByUser = countService.getCommentLikeCountByUser(user.getUid());
        int questionStarCountByUser = countService.getQuestionStarCountByUser(user.getUid());
        userCountDTO.setQuestionCount(questionCountByUser);
        userCountDTO.setQuestionLikedCount(questionLikeCountByUser + commentLikeCountByUser);
        userCountDTO.setQuestionStaredCount(questionStarCountByUser);

        model.addAttribute("userCount",userCountDTO);
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
        List<QuestionDTO> hotQuestionDTOs = new ArrayList<>();
        List<Question> dayHotQuestions = hotQuestionCache.getDayHotQuestions();
        List<Question> weekHotQuestions = hotQuestionCache.getWeekHotQuestions();
        List<Question> monthHotQuestions = hotQuestionCache.getMonthHotQuestions();
        //        热门标签
        List<String> tags = hotTagCache.getHots();
        //        管理员公告
        Admin admin = adminService.selectadminById(1);
        AdminAnnouncementDTO adminAnnouncementDTO = new AdminAnnouncementDTO();
        adminAnnouncementDTO.setName(admin.getName());
        adminAnnouncementDTO.setAnnouncement(admin.getAnnouncement());

        if (byTime == 1){
//            当天
            for (Question question : dayHotQuestions) {
                User user = userService.getUserByUid(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question,questionDTO);
                questionDTO.setUser(user);
                hotQuestionDTOs.add(questionDTO);
            }
            model.addAttribute("hotQuestions",hotQuestionDTOs);
        }else if (byTime == 2){
//            上周
            for (Question question : weekHotQuestions) {
                User user = userService.getUserByUid(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question,questionDTO);
                questionDTO.setUser(user);
                hotQuestionDTOs.add(questionDTO);
            }
            model.addAttribute("hotQuestions",hotQuestionDTOs);
        }else if (byTime == 3){
//            上月
            for (Question question : monthHotQuestions) {
                User user = userService.getUserByUid(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question,questionDTO);
                questionDTO.setUser(user);
                hotQuestionDTOs.add(questionDTO);
            }
            model.addAttribute("hotQuestions",hotQuestionDTOs);
        }else {
            model.addAttribute("hotQuestions",hotQuestionDTOs);
        }

        model.addAttribute("tags",tags);
        model.addAttribute("announcement",adminAnnouncementDTO);
        model.addAttribute("byTime",byTime);

        return "hotQuestion";
    }
}
