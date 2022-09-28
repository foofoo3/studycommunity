package com.demo.community.dto;

import lombok.Data;

@Data
public class CommentCreatDTO {
    private Long id;
    private int parentId;
    private int type;
    private String content;
}
