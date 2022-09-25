package com.demo.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;
    private Long parent_id;
    private int type;
    private int commentator;
    private Long gmt_create;
    private Long gmt_modified;
    private Long like_count;
    private String content;
}
