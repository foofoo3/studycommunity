package com.demo.community.dto;

import lombok.Data;
/**
 * @author foofoo3
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
