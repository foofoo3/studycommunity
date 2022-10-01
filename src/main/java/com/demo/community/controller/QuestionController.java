package com.demo.community.controller;

import com.demo.community.dto.CommentDTO;
import com.demo.community.dto.QuestionDTO;
import com.demo.community.enums.CommentTypeEnum;
import com.demo.community.sercive.CommentService;
import com.demo.community.sercive.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id, Model model){
//        问题内容
        QuestionDTO questionDTO = questionService.getById(id);
//        相似问题列表
        List<QuestionDTO> similarQuestions = questionService.selectSimilar(questionDTO);
//        回复列表
        List<CommentDTO> comments = commentService.listByParentId(id, CommentTypeEnum.QUESTION);
//        累加阅读数
        questionService.incView(id);

        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("similarQuestions",similarQuestions);
        return "question";
    }
}
