package com.demo.community.sercive;

import com.demo.community.entity.User;

import java.util.List;

/**
 * @Author: foofoo3
 */
public interface LikeService {

    int commentLikePlus(Long target_id, int uid);

    int commentLikeReduce(Long target_id, int uid);

    int questionLikePlus(Long target_id, int uid);

    int questionLikeReduce(Long target_id, int uid);

    List<Long> selectCommentLike(User user, Integer id);

    List<Integer> selectQuestionLike(User user);
}
