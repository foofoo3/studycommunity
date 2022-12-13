package com.demo.community.sercive.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.community.dto.PaginationDTO;
import com.demo.community.dto.QuestionDTO;
import com.demo.community.entity.Question;
import com.demo.community.entity.User;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.mapper.QuestionMapper;
import com.demo.community.mapper.UserMapper;
import com.demo.community.sercive.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author foofoo3
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserMapper userMapper;

//  查所有问题
    @Override
    public PaginationDTO list(String tag, String search, Integer page, Integer size,int type) {
        if (StringUtils.isNoneBlank(search)){
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags)
                    .filter(StringUtils::isNotBlank)
//                    .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                    .collect(Collectors.joining(""));
        }

        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPage;


//        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
//        questionQueryDTO.setSearch(search);
//        questionQueryDTO.setTag(tag);
        //        总页数
        int totalCount;
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        if (search == null && tag == null){
            totalCount = Math.toIntExact(questionMapper.selectCount(questionQueryWrapper));
        }else if (search != null && tag == null){
            questionQueryWrapper.like("search",search);
            totalCount = Math.toIntExact(questionMapper.selectCount(questionQueryWrapper));
        }else if (search == null && tag != null){
            questionQueryWrapper.like("tag",tag);
            totalCount = Math.toIntExact(questionMapper.selectCount(questionQueryWrapper));
        }else {
            questionQueryWrapper.like("search",search)
                    .like("tag",tag);
            totalCount = Math.toIntExact(questionMapper.selectCount(questionQueryWrapper));
        }


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

//        Integer offset = 1;
//        if (page != 0 ) {
//            offset = size * (page - 1);
//        }
//        questionQueryDTO.setSize(size);
//        questionQueryDTO.setPage(offset);

        List<Question> questions;
        Page<Question> questionPage = new Page<>(page,size);
        if (type == 2){
            questionQueryWrapper.orderByDesc("likeCount");
            questions = questionMapper.selectPage(questionPage,questionQueryWrapper).getRecords();
        }else if (type == 3){
            questionQueryWrapper.orderByDesc("starCount");
            questions = questionMapper.selectPage(questionPage,questionQueryWrapper).getRecords();
        }else if (type == 1){
            questionQueryWrapper.orderByDesc("gmt_create");
            questions = questionMapper.selectPage(questionPage, questionQueryWrapper).getRecords();
        }else {
            questionQueryWrapper.orderByDesc("gmtCreate");
            questions = questionMapper.selectPage(questionPage,questionQueryWrapper).getRecords();
        }

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("uid",question.getCreator());
            User user = userMapper.selectOne(userQueryWrapper);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    //    查用户所有问题
    @Override
    public PaginationDTO list(int uid, Integer page, Integer size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        int totalPage;
        //        搜索总数
        QueryWrapper<Question> questionQueryWrapper1 = new QueryWrapper<>();
        questionQueryWrapper1.eq("creator",uid);
        Integer totalCount = Math.toIntExact(questionMapper.selectCount(questionQueryWrapper1));
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

//        Integer offset =size *(page - 1);
        QueryWrapper<Question> questionQueryWrapper2 = new QueryWrapper<>();
        questionQueryWrapper2.eq("creator",uid);
        int count = Math.toIntExact(questionMapper.selectCount(questionQueryWrapper2));
        if (count != 0){
            QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
            questionQueryWrapper.eq("creator",uid)
                    .orderByDesc("gmtCreate");
            List<Question> questions = questionMapper.selectPage(new Page<>(page,size),questionQueryWrapper).getRecords();
            List<QuestionDTO> questionDTOList = new ArrayList<>();

            for (Question question : questions) {
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("uid",question.getCreator());
                User user = userMapper.selectOne(userQueryWrapper);
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
    @Override
    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectById(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

//      创建问题
    @Override
    public void create(Question question) {
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        questionMapper.insert(question);
    }

    //    修改问题
    @Override
    public void update(Question questionById, String title, String description, String tag) {
        questionById.setGmt_modified(System.currentTimeMillis());
        questionById.setTitle(title);
        questionById.setDescription(description);
        questionById.setTag(tag);
        int update = questionMapper.updateById(questionById);
        if (update != 1){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
        }
    }
//    通过id查找问题
    @Override
    public Question getQuestionById(Integer id) {
        return questionMapper.selectById(id);
    }

//    浏览数加一
    @Override
    public void incView(Integer id) {
        Question question = questionMapper.selectById(id);
        question.setView_count(question.getView_count());
        UpdateWrapper<Question> questionUpdateWrapper = new UpdateWrapper<>();
        questionUpdateWrapper.setSql("'viewCount' = 'viewCount' + 1");
        int update = questionMapper.update(question,questionUpdateWrapper);
        if (update != 1){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
        }
    }
//    搜索相似问题
    @Override
    public List<QuestionDTO> selectSimilar(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), "，");
        String regexpTag = String.join("|", tags);
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.ne("id",question.getId())
                .like("tag",question.getTag());
        List<Question> questions = questionMapper.selectList(questionQueryWrapper);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }

    @Override
    public int deleteQuestionById(Integer id) {
        return questionMapper.deleteById(id);
    }
}
