package com.demo.community.controller;


import com.demo.community.cache.HotTagCache;
import com.demo.community.dto.HotTagDTO;
import com.demo.community.dto.PaginationDTO;
import com.demo.community.sercive.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

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
        List<String> tags = hotTagCache.getHots();
        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        model.addAttribute("tags",tags);
        model.addAttribute("tag",tag);
        model.addAttribute("type",type);
        return "index";
    }


}

