package com.demo.community.schedule;

import com.demo.community.cache.HotQuestionCache;
import com.demo.community.entity.Question;
import com.demo.community.mapper.QuestionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

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

//    每一小时更新
    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void hotQuestionsScheduledByDay() {
//        List<Question> questions;
        List<Question> questionsByLike= questionMapper.selectHotQuestionByDay();

        hotQuestionCache.setDayHotQuestions(questionsByLike);
    }

//    上一周查询
    @Scheduled(cron = "0 0 0 ? * MON")
    public void hotQuestionsScheduledByWeek() {

    }

//    上个月查询
    @Scheduled(cron = "0 0 0 1 * ?")
    public void hotQuestionsScheduledByMonth() {

    }

}
