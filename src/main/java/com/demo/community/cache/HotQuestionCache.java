package com.demo.community.cache;

import com.demo.community.entity.Question;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: foofoo3
 */
@Component
@Data
public class HotQuestionCache {
    private List<Question> dayHotQuestions = new ArrayList<>();
    private List<Question> weekHotQuestions = new ArrayList<>();
    private List<Question> monthHotQuestions = new ArrayList<>();
}
