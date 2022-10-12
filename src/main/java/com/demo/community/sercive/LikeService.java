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
    public int commentLikePlus(int commentId,int uid){
        User user = userMapper.SelectByUid(uid);
        Comment comment = commentMapper.selectById((long) commentId);
        comment.setLike_count(comment.getLike_count());
        int i = commentMapper.likePlus(comment);
        int success = 0;
        LikeStar likeStar = new LikeStar();
        likeStar.setUid(user.getUid());
        likeStar.setTarget_id(Math.toIntExact(comment.getId()));
        likeStar.setType(LikeOrStarTypeEnum.COMMENT_LIKE.getType());
        likeStar.setGmt_create(System.currentTimeMillis());
        if (i !=0){
            int insert = likeStarMapper.insert(likeStar);
            if (insert != 0){
                success = 1;
            }
        }
        return success;
    }

    public void commentLikeReduce(Comment comment){

    }
}
