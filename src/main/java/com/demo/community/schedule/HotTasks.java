package com.demo.community.schedule;

import com.demo.community.cache.HotTagCache;
import com.demo.community.entity.Question;
import com.demo.community.mapper.QuestionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author: foofoo3
 */
@Component
@Slf4j
@EnableScheduling
public class HotTasks {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HotTagCache hotTagCache;
//    定时获取标签热度 （每7小时更新一次）
    @Scheduled(fixedRate = 1000 * 60 * 60 * 7)
    public void hotTagScheduled() {
        int offset = 0;
        int limit = 5;
        log.info("{}",new Date());

        List<Question> list = new ArrayList<>();
        Map<String, Integer> priorities = new HashMap<>();
//        获得问题
        while (offset == 0 || list.size() == limit){
            list = questionMapper.selectByRowbounds(offset,limit);
//            循环问题标签并放入map集合
            for (Question question : list){
                String tagString = question.getTag();
                String[] tags;

                if (tagString.contains("，")){
                    tags = StringUtils.split(tagString, "，");
                    for (String tag : tags){
                        Integer priority = priorities.get(tag);
                        if (priority != null){
                            priorities.put(tag, priority +(5 * question.getComment_count()));
                        }else {
                            priorities.put(tag, 5 * question.getComment_count());
                        }
                    }
                }else {
                    Integer priority = priorities.get(tagString);
                    if (priority != null){
                        priorities.put(tagString, priority +(5 * question.getComment_count()));
                    }else {
                        priorities.put(tagString, 5 * question.getComment_count());
                    }
                }
                log.info("list question : {}",question.getId());
            }
            offset += limit;
        }

        hotTagCache.updateTags(priorities);

    }
}
