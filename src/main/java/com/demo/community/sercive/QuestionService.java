package com.demo.community.sercive;

import com.demo.community.dto.PaginationDTO;
import com.demo.community.dto.QuestionDTO;
import com.demo.community.dto.QuestionQueryDTO;
import com.demo.community.entity.Question;
import com.demo.community.entity.User;
import com.demo.community.enums.LikeOrStarTypeEnum;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.mapper.QuestionMapper;
import com.demo.community.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author foofoo3
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;
//  查所有问题
    public PaginationDTO list(String tag, String search, Integer page, Integer size,Integer type) {
        if (StringUtils.isNoneBlank(search)){
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags)
                    .filter(StringUtils::isNotBlank)
//                    .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                    .collect(Collectors.joining(""));
        }

        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPage;

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        questionQueryDTO.setTag(tag);
        //        总页数
        Integer totalCount = questionMapper.countBySearch(questionQueryDTO);

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

        Integer offset = 0;
        if (page != 0 ) {
            offset = size * (page - 1);
        }
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);

        List<Question> questions;
        if (type == 2){
            questions = questionMapper.listLike(questionQueryDTO);
        }else if (type == 3){
            questions = questionMapper.listStar(questionQueryDTO);
        }else if (type == 1){
            questions = questionMapper.listTime(questionQueryDTO);
        }else {
            questions = questionMapper.listTime(questionQueryDTO);
        }

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.SelectByUid(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }
//    查用户所有问题
    public PaginationDTO list(int uid, Integer page, Integer size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPage;
        //        搜索总数
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
        int count = questionMapper.countByUid(uid);
        if (count != 0){
            List<Question> questions = questionMapper.listByUid(uid,offset,size);
            List<QuestionDTO> questionDTOList = new ArrayList<>();

            for (Question question : questions) {
                User user = userMapper.SelectByUid(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question,questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }

            paginationDTO.setData(questionDTOList);

            return paginationDTO;

        }else {
            paginationDTO.setData(new ArrayList<>());

            return paginationDTO;
        }
    }

//    通过id查问题详情
    public QuestionDTO getById(int id) {
        Question question = questionMapper.getQuestionById(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
        }
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
        int update = questionMapper.update(questionById);
        if (update != 1){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
        }
    }
//    通过id查找问题
    public Question getQuestionById(int id) {
        return questionMapper.getQuestionById(id);
    }
//    浏览数加一
    public void incView(Integer id) {
        Question question = questionMapper.getQuestionById(id);
        question.setView_count(question.getView_count());
        int update = questionMapper.updateViewCount(question);
        if (update != 1){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
        }
    }
//    搜索相似问题
    public List<QuestionDTO> selectSimilar(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), "，");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionMapper.selectSimilarQuestion(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }

    public void deleteQuestionById(Integer id) {
        questionMapper.deleteById(id);
    }
}
