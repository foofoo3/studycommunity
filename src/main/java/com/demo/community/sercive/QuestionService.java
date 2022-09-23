package com.demo.community.sercive;

import com.demo.community.dto.PaginationDTO;
import com.demo.community.dto.QuestionDTO;
import com.demo.community.entity.Question;
import com.demo.community.entity.User;
import com.demo.community.mapper.QuestionMapper;
import com.demo.community.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;
//  查所有问题
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        //        搜索总页数
        Integer totalCount = questionMapper.count();
        if (totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }

        paginationDTO.setPagination(totalPage,page);

        //        超出页数范围判断,防止越界
        if (page < 1){
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        Integer offset =size *(page - 1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.SelectByUid(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
//    查用户所有问题
    public PaginationDTO list(int uid, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        //        搜索总页数
        Integer totalCount = questionMapper.countByUid(uid);
        //        计算页码总大小
        if (totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }
        //        超出页数范围判断,防止越界
        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        Integer offset =size *(page - 1);
        List<Question> questions = questionMapper.listByUid(uid,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.SelectByUid(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

//    通过id查问题详情
    public QuestionDTO getById(int id) {
        Question question = questionMapper.getQuestionById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.SelectByUid(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

//      创建问题
    public void create(Question question) {
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        questionMapper.create(question);
    }
//    修改问题
    public void update(Question questionById, String title, String description, String tag) {
        questionById.setGmt_modified(System.currentTimeMillis());
        questionById.setTitle(title);
        questionById.setDescription(description);
        questionById.setTag(tag);
        questionMapper.update(questionById);
    }
//    通过id查找问题
    public Question getQuestionById(int id) {
        return questionMapper.getQuestionById(id);
    }
}
