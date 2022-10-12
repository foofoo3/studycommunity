package com.demo.community.entity;

import lombok.Data;

/**
 * @Author: foofoo3
 */
@Data
public class LikeStar {
    private int type;
    private int uid;
    private Long target_id;
    private int id;
    private Long gmt_create;
}
