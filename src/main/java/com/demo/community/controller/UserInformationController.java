package com.demo.community.controller;

import com.demo.community.dto.PaginationDTO;
import com.demo.community.dto.UserCountDTO;
import com.demo.community.entity.User;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.sercive.CountService;
import com.demo.community.sercive.QuestionService;
import com.demo.community.sercive.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: foofoo3
 */
@Slf4j
@Controller
public class UserInformationController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CountService countService;

    @GetMapping("/information/{uid}")
    public String information(@PathVariable(name = "uid")int uid, Model model,
                              @RequestParam(name = "page",defaultValue = "1") Integer page,
                              @RequestParam(name = "size",defaultValue = "5") Integer size){

        UserCountDTO userCountDTO = new UserCountDTO();
        User user = userService.getUserByUid(uid);
        if (user == null){
            throw new CustomizeException(CustomizeErrorCode.USER_NOT_FOUND);
        }
        PaginationDTO paginationDTO = questionService.list(user.getUid(),page,size);

//        查用户问题数 获赞数 被收藏数
        int questionCountByUser = countService.getQuestionCountByUser(user.getUid());
        int questionLikeCountByUser = countService.getQuestionLikeCountByUser(user.getUid());
        int questionStarCountByUser = countService.getQuestionStarCountByUser(user.getUid());
        userCountDTO.setQuestionCount(questionCountByUser);
        userCountDTO.setQuestionLikedCount(questionLikeCountByUser);
        userCountDTO.setQuestionStaredCount(questionStarCountByUser);

        model.addAttribute("pagination",paginationDTO);
        model.addAttribute("user",user);
        model.addAttribute("userCount",userCountDTO);

        return "userInformation";
    }
}
