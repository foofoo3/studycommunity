package com.demo.community.sercive;

/**
 * @Author: foofoo3
 */
public interface CountService {

    int getQuestionCountByUser(int uid);

    int getQuestionLikeCountByUser(int uid);

    int getCommentLikeCountByUser(int uid);

    int getQuestionStarCountByUser(int uid);
}
