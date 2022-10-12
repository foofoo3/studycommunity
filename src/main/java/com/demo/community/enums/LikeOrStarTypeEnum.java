package com.demo.community.enums;

/**
 * @Author: foofoo3
 */
public enum LikeOrStarTypeEnum {
    QUESTION_LIKE(1),
    QUESTION_STAR(2),
    COMMENT_LIKE(3);

    private int type;

    public int getType() {
        return type;
    }

    LikeOrStarTypeEnum(int type) {
        this.type = type;
    }
}
