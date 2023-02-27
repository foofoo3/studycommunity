package com.demo.community.controller;

import com.demo.community.cache.TagCache;
import com.demo.community.entity.Question;
import com.demo.community.entity.User;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * @author foofoo3
 */
@Slf4j
@Controller
public class PublishController {

    @Resource
    private QuestionService questionService;

    @GetMapping("/publish")
    public String Publish(Model model){
        model.addAttribute("id",0);
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public void doPublish(@RequestParam("title") String title,
                          @RequestParam("description") String description,
                          @RequestParam("tag") String tag,
                          @RequestParam("id") Integer id,
                          HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user==null){
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }else {
            if (title.length() > 40){
                try {
                    response.sendRedirect("/resultRegister?=false&"+id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(tag.contains(",")){
                try {
                    response.sendRedirect("/resultLogin?=false&"+id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
//                创建新问题
                if (id == 0){
                    Question question = new Question();
                    question.setTitle(title);
                    question.setDescription(description);
                    question.setTag(tag);
                    question.setCreator(user.getUid());

                    questionService.create(question);

                    try {
                        response.sendRedirect("/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    //     修改问题
                    Question questionById = questionService.getQuestionById(id);
                    questionService.update(questionById, title, description, tag);
                    try {
                        response.sendRedirect("/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id, Model model){
        Question question = questionService.getQuestionById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
}
