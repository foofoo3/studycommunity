package com.demo.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private int parent_id;
    private int type;
    private String content;
}
