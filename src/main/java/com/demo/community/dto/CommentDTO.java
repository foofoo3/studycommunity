package com.demo.community.dto;

import com.demo.community.entity.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private int parent_id;
    private int type;
    private int commentator;
    private Long gmt_create;
    private Long gmt_modified;
    private Long like_count;
    private String content;
    private User user;
}
