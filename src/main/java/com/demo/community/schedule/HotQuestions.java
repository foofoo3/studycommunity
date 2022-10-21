package com.demo.community.schedule;

import com.demo.community.cache.HotQuestionCache;
import com.demo.community.dto.QuestionDTO;
import com.demo.community.entity.Question;
import com.demo.community.mapper.QuestionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: foofoo3
 */
@Component
@Slf4j
@EnableScheduling
public class HotQuestions {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HotQuestionCache hotQuestionCache;

//    当天每一小时更新
    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void hotQuestionsScheduledByDay() {

        List<Question> questions= questionMapper.selectHotQuestionByDay();
        Map<Question, Integer> priorities = new HashMap<>();

        for (Question question : questions){
            Integer hot = question.getComment_count() + (question.getStar_count() * 2) + (question.getLike_count() * 3);
            priorities.put(question,hot);
        }

        hotQuestionCache.updateDayQuestions(priorities);
    }

//    上一周查询 每周一更新
    @Scheduled(cron = "0 0 0 ? * MON")
    public void hotQuestionsScheduledByWeek() {

    }

//    上个月查询 每月1号更新
    @Scheduled(cron = "0 0 0 1 * ?")
    public void hotQuestionsScheduledByMonth() {

    }

}
