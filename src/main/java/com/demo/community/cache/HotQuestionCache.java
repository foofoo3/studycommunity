package com.demo.community.cache;

import com.demo.community.dto.HotQuestionDTO;
import com.demo.community.dto.HotTagDTO;
import com.demo.community.dto.QuestionDTO;
import com.demo.community.entity.Question;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @Author: foofoo3
 */
@Component
@Data
public class HotQuestionCache {
    private List<Question> dayHotQuestions = new ArrayList<>();
    private List<Question> weekHotQuestions = new ArrayList<>();
    private List<Question> monthHotQuestions = new ArrayList<>();

    public void updateDayQuestions(Map<Question,Integer> questionMap){
        PriorityQueue<HotQuestionDTO> priorityQueue = new PriorityQueue<>(10);

        questionMap.forEach((question,hot)->{
            HotQuestionDTO hotQuestionDTO = new HotQuestionDTO();
            hotQuestionDTO.setQuestion(question);
            hotQuestionDTO.setPriority(hot);
            if (priorityQueue.size() < 10){
                priorityQueue.add(hotQuestionDTO);
            }else {
                HotQuestionDTO minHotQuestion = priorityQueue.peek();
                if (hotQuestionDTO.compareTo(minHotQuestion) > 0){
                    priorityQueue.poll();
                    priorityQueue.add(hotQuestionDTO);
                }
            }
        });

        List<Question>  sortedQuestions = new ArrayList<>();
        HotQuestionDTO poll = priorityQueue.poll();

        while (poll != null){
            sortedQuestions.add(0,poll.getQuestion());
            poll = priorityQueue.poll();
        }
        dayHotQuestions = sortedQuestions;
        System.out.println(dayHotQuestions);
    }

    public void updateWeekQuestions(Map<Question, Integer> questionMap) {
        PriorityQueue<HotQuestionDTO> priorityQueue = new PriorityQueue<>(10);

        questionMap.forEach((question,hot)->{
            HotQuestionDTO hotQuestionDTO = new HotQuestionDTO();
            hotQuestionDTO.setQuestion(question);
            hotQuestionDTO.setPriority(hot);
            if (priorityQueue.size() < 10){
                priorityQueue.add(hotQuestionDTO);
            }else {
                HotQuestionDTO minHotQuestion = priorityQueue.peek();
                if (hotQuestionDTO.compareTo(minHotQuestion) > 0){
                    priorityQueue.poll();
                    priorityQueue.add(hotQuestionDTO);
                }
            }
        });

        List<Question>  sortedQuestions = new ArrayList<>();
        HotQuestionDTO poll = priorityQueue.poll();

        while (poll != null){
            sortedQuestions.add(0,poll.getQuestion());
            poll = priorityQueue.poll();
        }
        weekHotQuestions = sortedQuestions;
        System.out.println(weekHotQuestions);
    }

    public void updateMonthQuestions(Map<Question, Integer> questionMap) {
        PriorityQueue<HotQuestionDTO> priorityQueue = new PriorityQueue<>(10);

        questionMap.forEach((question,hot)->{
            HotQuestionDTO hotQuestionDTO = new HotQuestionDTO();
            hotQuestionDTO.setQuestion(question);
            hotQuestionDTO.setPriority(hot);
            if (priorityQueue.size() < 10){
                priorityQueue.add(hotQuestionDTO);
            }else {
                HotQuestionDTO minHotQuestion = priorityQueue.peek();
                if (hotQuestionDTO.compareTo(minHotQuestion) > 0){
                    priorityQueue.poll();
                    priorityQueue.add(hotQuestionDTO);
                }
            }
        });

        List<Question>  sortedQuestions = new ArrayList<>();
        HotQuestionDTO poll = priorityQueue.poll();

        while (poll != null){
            sortedQuestions.add(0,poll.getQuestion());
            poll = priorityQueue.poll();
        }

        monthHotQuestions = sortedQuestions;
        System.out.println(monthHotQuestions);
    }
}
