package com.demo.community.dto;

import lombok.Data;
/**
 * @author foofoo3
 */
@Data
public class CommentCreatDTO {
    private Long id;
    private int parentId;
    private int type;
    private String content;
}
