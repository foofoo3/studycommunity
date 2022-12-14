package com.demo.community.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.community.cache.HotQuestionCache;
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
        QueryWrapper<Question> queryDayWrapper = new QueryWrapper<>();
        queryDayWrapper.ge("gmt_create",System.currentTimeMillis() - (1000*60*24));
        queryDayWrapper.lt("gmt_create",System.currentTimeMillis());
//        queryDayWrapper.apply("DATE_SUB(CURDATE(), INTERVAL 1 DAY) <= date(from_unixtime(gmt_create/1000,'%Y-%m-%d')) ORDER BY like_count desc limit 0,10");
        List<Question> questions= questionMapper.selectList(queryDayWrapper);
        Map<Question, Integer> priorities = new HashMap<>();

        for (Question question : questions){
            Integer hot = question.getComment_count() + (question.getStar_count() * 2) + (question.getLike_count() * 3);
            priorities.put(question,hot);
        }

        hotQuestionCache.updateDayQuestions(priorities);
    }

//    上一周查询 每周一更新
//    @Scheduled(cron = "0 0 0 ? * MON")



    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void hotQuestionsScheduledByWeek() {
        QueryWrapper<Question> queryWeekWrapper = new QueryWrapper<>();
        queryWeekWrapper.ge("gmt_create",System.currentTimeMillis() - (1000*60*60*24*7));
        queryWeekWrapper.lt("gmt_create",System.currentTimeMillis());
//        queryWeekWrapper.apply("DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(from_unixtime(gmt_create/1000,'%Y-%m-%d')) ORDER BY like_count desc limit 0,10");
        List<Question> questions= questionMapper.selectList(queryWeekWrapper);
        Map<Question, Integer> priorities = new HashMap<>();

        for (Question question : questions){
            Integer hot = question.getComment_count() + (question.getStar_count() * 2) + (question.getLike_count() * 3);
            priorities.put(question,hot);
        }

        hotQuestionCache.updateWeekQuestions(priorities);
    }

//    上个月查询 每月1号更新
//    @Scheduled(cron = "0 0 0 1 * ?")
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void hotQuestionsScheduledByMonth() {
        QueryWrapper<Question> queryMonthWrapper = new QueryWrapper<>();
        queryMonthWrapper.ge("gmt_create",System.currentTimeMillis() - (1000L *60*60*24*31));
        queryMonthWrapper.lt("gmt_create",System.currentTimeMillis());
//        queryMonthWrapper.apply("DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(from_unixtime(gmt_create/1000,'%Y-%m-%d')) ORDER BY like_count desc limit 0,10");
        List<Question> questions= questionMapper.selectList(queryMonthWrapper);
        Map<Question, Integer> priorities = new HashMap<>();

        for (Question question : questions){
            Integer hot = question.getComment_count() + (question.getStar_count() * 2) + (question.getLike_count() * 3);
            priorities.put(question,hot);
        }

        hotQuestionCache.updateMonthQuestions(priorities);
    }

}
