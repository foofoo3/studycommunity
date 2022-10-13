package com.demo.community.controller;

import com.demo.community.dto.CommentDTO;
import com.demo.community.dto.QuestionDTO;
import com.demo.community.entity.User;
import com.demo.community.enums.CommentTypeEnum;
import com.demo.community.sercive.CommentService;
import com.demo.community.sercive.LikeService;
import com.demo.community.sercive.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
/**
 * @author foofoo3
 */

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
//        问题内容
        QuestionDTO questionDTO = questionService.getById(id);
//        相似问题列表
        List<QuestionDTO> similarQuestions = questionService.selectSimilar(questionDTO);
//        回复列表
        List<CommentDTO> comments = commentService.listByParentId(id, CommentTypeEnum.QUESTION);
//        查询用户喜欢评论id
        if (user != null){
            List<Long> likesId = likeService.selectCommentLike(user,id);
            model.addAttribute("likesId",likesId);
            List<Integer> questionLikesId = likeService.selectQuestionLike(user);
            model.addAttribute("questionLikesId",questionLikesId);
        }
//        累加阅读数
        questionService.incView(id);

        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("similarQuestions",similarQuestions);
        return "question";
    }
}
