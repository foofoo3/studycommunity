package com.demo.community.sercive.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.demo.community.dto.CommentDTO;
import com.demo.community.entity.Comment;
import com.demo.community.entity.Notification;
import com.demo.community.entity.Question;
import com.demo.community.entity.User;
import com.demo.community.enums.CommentTypeEnum;
import com.demo.community.enums.NotificationStatusEnum;
import com.demo.community.enums.NotificationTypeEnum;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.mapper.CommentMapper;
import com.demo.community.mapper.NotificationMapper;
import com.demo.community.mapper.QuestionMapper;
import com.demo.community.mapper.UserMapper;
import com.demo.community.sercive.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * @author foofoo3
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private NotificationMapper notificationMapper;
    @Resource
    private UserMapper userMapper;

    @Transactional
    @Override
    public void creat(Comment comment,User commentator) {
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
            Long commentId = (long) comment.getParent_id();
            Comment dbcomment = commentMapper.selectById(commentId);
            if (dbcomment == null){
                throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
            }
            commentMapper.insert(comment);
//            评论数加一
            dbcomment.setComment_count(dbcomment.getComment_count());
            UpdateWrapper<Comment> commentUpdateWrapper = new UpdateWrapper<>();
            commentUpdateWrapper.eq("id",dbcomment.getId());
            commentUpdateWrapper.setSql("comment_count = comment_count + 1");
            int i = commentMapper.update(dbcomment,commentUpdateWrapper);
            if (i != 1){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUNT);
            }
            //    创建通知
            Question questionById = questionMapper.selectById(dbcomment.getParent_id());
            createNotify(comment,questionById.getId(),dbcomment.getCommentator(),commentator.getName(),dbcomment.getContent(),NotificationTypeEnum.REPLY_COMMENT);
        }else {
//            回复问题
            Question question = questionMapper.selectById(comment.getParent_id());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
            }
            commentMapper.insert(comment);
            //    回复数加一
            question.setComment_count(question.getComment_count());
            UpdateWrapper<Question> questionUpdateWrapper = new UpdateWrapper<>();
            questionUpdateWrapper.eq("id",question.getId());
            questionUpdateWrapper.setSql("comment_count = comment_count + 1");
            int i = questionMapper.update(question,questionUpdateWrapper);
            if (i != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
            }
            //    创建通知
            createNotify(comment,question.getId(),question.getCreator(),commentator.getName(),question.getTitle(),NotificationTypeEnum.REPLY_QUESTION);
        }

    }

    //    创建通知方法
    private void createNotify(Comment comment,int outerId,int receiver,String notifierName, String outerTitle,NotificationTypeEnum notificationType) {
        if (receiver == comment.getCommentator()){
            return;
        }
        Notification notification = new Notification();
        notification.setGmt_create(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterId(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setReceiver(receiver);
        notification.setNotifier_name(notifierName);
        notification.setOuter_title(outerTitle);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notificationMapper.insert(notification);
    }

//    返回评论列表
    @Override
    public List<CommentDTO> listByParentId(int id, CommentTypeEnum type,int like) {
        List<Comment> comments;
        if (like == 1) {
//            按点赞排序
            QueryWrapper<Comment> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id",id)
                    .eq("type",type.getType())
                    .orderByDesc("like_count");
            comments = commentMapper.selectList(wrapper);
        } else{
//            默认时间排序
            QueryWrapper<Comment> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id",id)
                    .eq("type",type.getType())
                    .orderByDesc("gmt_create");
            comments = commentMapper.selectList(wrapper);
        }
        if (comments.size() == 0){
            return new ArrayList<>();
        }
//        获取去重评论人id
        Set<Integer> commentators = comments.stream().map(Comment::getCommentator).collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList<>(commentators);

//        获取评论人并转换为Map
        List<User> users = userMapper.selectBatchIds(userIds);
        Map<Integer,User> userMap = users.stream().collect(Collectors.toMap(User::getUid, user -> user));

//        转换comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    };

    @Transactional
    @Override
    public int deleteCommentById(Long id,int type) {
//        判断删除评论类型
        if (type == 1){
//            如果为评论则删除评论及它的二级评论
            QueryWrapper<Comment> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("parent_id",id)
                    .eq("type",CommentTypeEnum.COMMENT.getType())
                    .orderByDesc("gmt_create");
            List<Comment> comments = commentMapper.selectList(wrapper1);
            if (comments.size() != 0){
                QueryWrapper<Comment> wrapper2 = new QueryWrapper<>();
                wrapper2.eq("parent_id",id)
                        .eq("type",CommentTypeEnum.COMMENT.getType());
                int res = commentMapper.delete(wrapper2);
                if (res != 0){
//                    当前问题评论数减一
                    int questionId = commentMapper.selectById(id).getParent_id();
                    Question parentQuestion = questionMapper.selectById(questionId);
                    parentQuestion.setComment_count(parentQuestion.getComment_count());
                    UpdateWrapper<Question> questionUpdateWrapper = new UpdateWrapper<>();
                    questionUpdateWrapper.eq("id",parentQuestion.getId());
                    questionUpdateWrapper.setSql("comment_count = comment_count - 1");
                    int qr = questionMapper.update(parentQuestion,questionUpdateWrapper);
//                    删除评论
                    int resC = commentMapper.deleteById(id);
                    if (resC != 0 && qr != 0){
                        return 1;
                    }
                }
            }else {
//                    当前问题评论数减一
                int questionId = commentMapper.selectById(id).getParent_id();
                Question parentQuestion = questionMapper.selectById(questionId);
                parentQuestion.setComment_count(parentQuestion.getComment_count());
                UpdateWrapper<Question> questionUpdateWrapper = new UpdateWrapper<>();
                questionUpdateWrapper.eq("id",parentQuestion.getId());
                questionUpdateWrapper.setSql("comment_count = comment_count - 1");
                int qr = questionMapper.update(parentQuestion,questionUpdateWrapper);
//                    删除评论
                int resC = commentMapper.deleteById(id);
                if (resC != 0 && qr != 0){
                    return 1;
                }
            }
        }if (type == 2){
//            当前评论评论数减一
            int commentId = commentMapper.selectById(id).getParent_id();
            Comment parentComment = commentMapper.selectById(commentId);
            parentComment.setComment_count(parentComment.getComment_count());
            UpdateWrapper<Comment> commentUpdateWrapper = new UpdateWrapper<>();
            commentUpdateWrapper.eq("id",parentComment.getId());
            commentUpdateWrapper.setSql("comment_count = comment_count - 1");
            int cr = commentMapper.update(parentComment,commentUpdateWrapper);

//            如果为二级评论则直接删除
            int resS = commentMapper.deleteById(id);
            if (resS != 0 && cr != 0){
                return 1;
            }
        }
        return 0;
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentMapper.selectById(id);
    }
}
