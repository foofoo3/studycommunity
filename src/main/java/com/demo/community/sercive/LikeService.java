package com.demo.community.sercive;

import com.demo.community.entity.Comment;
import com.demo.community.entity.LikeStar;
import com.demo.community.entity.User;
import com.demo.community.enums.LikeOrStarTypeEnum;
import com.demo.community.mapper.CommentMapper;
import com.demo.community.mapper.LikeStarMapper;
import com.demo.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: foofoo3
 */
@Service
public class LikeService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private LikeStarMapper likeStarMapper;
    @Autowired
    private UserMapper userMapper;
//    评论增加点赞数
    public int commentLikePlus(Long commentId,int uid){
        User user = userMapper.SelectByUid(uid);
        Comment comment = commentMapper.selectById(commentId);
        comment.setLike_count(comment.getLike_count());
//        评论点赞数加一
        int i = commentMapper.likePlus(comment);
        int success = 0;
        LikeStar likeStar = new LikeStar();
        likeStar.setUid(user.getUid());
        likeStar.setTarget_id(comment.getId());
        likeStar.setType(LikeOrStarTypeEnum.COMMENT_LIKE.getType());
        likeStar.setGmt_create(System.currentTimeMillis());
        if (i !=0){
//            记录点赞信息
            int insert = likeStarMapper.insert(likeStar);
            if (insert != 0){
                success = 1;
            }
        }
        return success;
    }
    //    评论减少点赞数
    public int commentLikeReduce(Long commentId,int uid){
        Comment comment = commentMapper.selectById(commentId);
        comment.setLike_count(comment.getLike_count());
        int i = commentMapper.likeReduce(comment);
        int success = 0;
        if (i != 0){
            int delete = likeStarMapper.deleteByCommentId(commentId,uid, LikeOrStarTypeEnum.COMMENT_LIKE.getType());
            if (delete != 0){
                success = 1;
            }
        }
        return success;
    }


    public List<Long> selectCommentLike(User user) {
        return likeStarMapper.selectCommentLikeByUid(user.getUid(), LikeOrStarTypeEnum.COMMENT_LIKE.getType());
    }

}
