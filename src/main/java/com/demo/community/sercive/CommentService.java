package com.demo.community.sercive;

import com.demo.community.dto.CommentDTO;
import com.demo.community.entity.Comment;
import com.demo.community.entity.Question;
import com.demo.community.entity.User;
import com.demo.community.enums.CommentTypeEnum;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.mapper.CommentMapper;
import com.demo.community.mapper.QuestionMapper;
import com.demo.community.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void creat(Comment comment) {
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(comment.getGmt_create());
//        如果回复的问题不存在则抛异常
        if (comment.getParent_id() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == 0 || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()){
//            回复评论
            Comment dbcomment = commentMapper.selectByParentId(comment.getParent_id());
            if (dbcomment == null){
                throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
            }
            commentMapper.creat(comment);
        }else {
//            回复问题
            Question question = questionMapper.getQuestionById(comment.getParent_id());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
            }
            commentMapper.creat(comment);
            //    回复数加一
            question.setComment_count(question.getComment_count());
            int i = questionMapper.updateCommentCount(question);
            if (i != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
            }
        }

    }

    public List<CommentDTO> listByQuestionId(int id){
        Integer type = CommentTypeEnum.QUESTION.getType();
        List<Comment> comments = commentMapper.selectByPidAndType(id, type);
        if (comments.size() == 0){
            return new ArrayList<>();
        }
//        获取去重评论人id
        Set<Integer> commentators = comments.stream().map(Comment::getCommentator).collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList<>(commentators);
//        获取评论人并转换为Map
        List<User> users = userMapper.SelectByUidInList(userIds);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getUid(), user -> user));

//        转换comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    };
}
