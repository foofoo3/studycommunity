package com.demo.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: foofoo3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeStar {
    private int type;
    private int uid;
    private Long target_id;
    private int id;
    private Long gmt_create;
    private int parent_id;
}
