package com.demo.community.controller;

import com.demo.community.dto.CommentDTO;
import com.demo.community.dto.QuestionDTO;
import com.demo.community.dto.ResultDTO;
import com.demo.community.entity.Admin;
import com.demo.community.entity.Question;
import com.demo.community.entity.User;
import com.demo.community.enums.CommentTypeEnum;
import com.demo.community.enums.NotificationTypeEnum;
import com.demo.community.sercive.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author foofoo3
 */

@Controller
public class QuestionController {

    @Resource
    private QuestionService questionService;
    @Resource
    private CommentService commentService;
    @Resource
    private LikeService likeService;
    @Resource
    private StarService starService;
    @Resource
    private NotificationService notificationService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id, Model model, HttpServletRequest request,
                           @RequestParam(name = "like",required = false)Integer like){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

//        问题内容
        QuestionDTO questionDTO = questionService.getById(id);

//        相似问题列表
        List<QuestionDTO> similarQuestions = new ArrayList<>();
        List<QuestionDTO> questions = questionService.selectSimilar(questionDTO);
        if (questions.size() <= 10){
            similarQuestions = questions;
        }else {
//            如果相似问题大于10个则随机抽取10个
            for(int i = 0; i < 10; i++ ) {
                Random random = new Random();
                int index = random.nextInt(questions.size());
                similarQuestions.add(questions.get(index));
                questions.remove(index);
            }
        }

//        回复列表
        List<CommentDTO> comments;

//        判断是否按喜欢排序
        if (like != null) {
            comments = commentService.listByParentId(Long.valueOf(id), CommentTypeEnum.QUESTION, like);
        }else {
            comments = commentService.listByParentId(Long.valueOf(id), CommentTypeEnum.QUESTION,0);
        }

//        查询用户喜欢评论id
        if (user != null){
            List<Long> likesId = likeService.selectCommentLike(user,id);
            model.addAttribute("likesId",likesId);
            List<Integer> questionLikesId = likeService.selectQuestionLike(user);
            model.addAttribute("questionLikesId",questionLikesId);
            List<Integer> questionStarsId = starService.selectQuestionStar(user);
            model.addAttribute("questionStarsId",questionStarsId);
        }

//        累加阅读数
        questionService.incView(id);

        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("similarQuestions",similarQuestions);
        model.addAttribute("like",like);
        return "question";
    }

    @ResponseBody
    @PostMapping("/deleteQuestion/{id}")
    public Object deleteQuestion(@PathVariable(name = "id")Integer id,HttpServletRequest request){
        Question question = questionService.getQuestionById(id);
        Admin admin = (Admin) request.getSession().getAttribute("admin");

//        删除问题
        int res = questionService.deleteQuestionById(id);

//        如果为管理员删除则创建通知
        if (admin != null){
            notificationService.createAdminNotify(admin.getId(),id,question.getCreator(),admin.getName(),question.getTitle(), NotificationTypeEnum.DELETE_QUESTION);
        }
        if (res != 0){
            return ResultDTO.okOf();
        }else {
            return ResultDTO.errorOf(6000,"删除问题失败");
        }
    }
}
