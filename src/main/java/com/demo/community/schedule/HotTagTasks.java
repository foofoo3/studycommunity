package com.demo.community.schedule;

import com.demo.community.entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: foofoo3
 */
@Component
@Slf4j
public class HotTagTasks {
    @Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 0 1 * * *")
    public void hotTagScheduled() {
        int offset = 0;
        int limit = 5;
        List<Question> list = new ArrayList<>();

    }
}
