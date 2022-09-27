package com.demo.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private int parentId;
    private int type;
    private String content;
}
