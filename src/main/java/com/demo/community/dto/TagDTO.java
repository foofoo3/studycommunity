package com.demo.community.dto;

import lombok.Data;

import java.util.List;
/**
 * @author foofoo3
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
