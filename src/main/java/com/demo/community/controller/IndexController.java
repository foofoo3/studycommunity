package com.demo.community.controller;


import com.demo.community.cache.HotQuestionCache;
import com.demo.community.cache.HotTagCache;
import com.demo.community.dto.AdminAnnouncementDTO;
import com.demo.community.dto.PaginationDTO;
import com.demo.community.dto.QuestionDTO;
import com.demo.community.entity.Admin;
import com.demo.community.entity.Question;
import com.demo.community.entity.User;
import com.demo.community.sercive.AdminService;
import com.demo.community.sercive.QuestionService;
import com.demo.community.sercive.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author foofoo3
 */

@Slf4j
@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private HotTagCache hotTagCache;
    @Autowired
    private HotQuestionCache hotQuestionCache;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        @RequestParam(name = "search",required = false) String search,
                        @RequestParam(name = "tag",required = false) String tag,
                        @RequestParam(name = "type",required = false) Integer type) {
        if (search == ""){
            search = null;
        }
        if (tag == ""){
            tag = null;
        }

        PaginationDTO pagination;

        if (type == null || type == 1){
            pagination = questionService.list(tag,search,page,size,1);
        }else {
            pagination = questionService.list(tag,search,page,size,type);
        }
//        一周热门问题前五
        List<Question> hotQuestions;
        List<Question> weekHotQuestions = hotQuestionCache.getWeekHotQuestions();
        if (weekHotQuestions.size() <= 5){
            hotQuestions = weekHotQuestions;
        }else {
            hotQuestions = weekHotQuestions.subList(0, 5);
        }
//        热门标签
        List<String> tags = hotTagCache.getHots();
//        管理员公告
        Admin admin = adminService.selectadminById(1);
        AdminAnnouncementDTO adminAnnouncementDTO = new AdminAnnouncementDTO();
        adminAnnouncementDTO.setName(admin.getName());
        adminAnnouncementDTO.setAnnouncement(admin.getAnnouncement());

        model.addAttribute("hotQuestions",hotQuestions);
        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        model.addAttribute("tags",tags);
        model.addAttribute("tag",tag);
        model.addAttribute("type",type);
        model.addAttribute("announcement",adminAnnouncementDTO);
        return "index";
    }

    @GetMapping("/searchUser")
    public String searchUser(Model model, @RequestParam(name = "searchName") String searchName){

        List<User> searchUsers = userService.getUsersByName(searchName);

        //        一周热门问题前五
        List<Question> hotQuestions;
        List<Question> weekHotQuestions = hotQuestionCache.getWeekHotQuestions();
        if (weekHotQuestions.size() <= 5){
            hotQuestions = weekHotQuestions;
        }else {
            hotQuestions = weekHotQuestions.subList(0, 5);
        }
//        热门标签
        List<String> tags = hotTagCache.getHots();
//        管理员公告
        Admin admin = adminService.selectadminById(1);
        AdminAnnouncementDTO adminAnnouncementDTO = new AdminAnnouncementDTO();
        adminAnnouncementDTO.setName(admin.getName());
        adminAnnouncementDTO.setAnnouncement(admin.getAnnouncement());

        model.addAttribute("users",searchUsers);
        model.addAttribute("searchName",searchName);
        model.addAttribute("hotQuestions",hotQuestions);
        model.addAttribute("tags",tags);
        model.addAttribute("announcement",adminAnnouncementDTO);

        return "searchUsers";
    }

}

