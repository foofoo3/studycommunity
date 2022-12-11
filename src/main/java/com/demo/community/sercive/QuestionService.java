package com.demo.community.sercive;

import com.demo.community.dto.PaginationDTO;
import com.demo.community.dto.QuestionDTO;
import com.demo.community.entity.Question;

import java.util.List;

/**
 * @Author: foofoo3
 */
public interface QuestionService {

    PaginationDTO list(String tag, String search, Integer page, Integer size, int i);

    PaginationDTO list(int uid, Integer page, Integer size);

    void create(Question question);

    Question getQuestionById(Integer id);

    void update(Question questionById, String title, String description, String tag);

    QuestionDTO getById(Integer id);

    List<QuestionDTO> selectSimilar(QuestionDTO questionDTO);

    void incView(Integer id);

    int deleteQuestionById(Integer id);
}
