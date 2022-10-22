package com.demo.community.dto;

import lombok.Data;

/**
 * @Author: foofoo3
 */
@Data
public class UserCountDTO {
    private Integer questionCount;
    private Integer questionLikedCount;
    private Integer questionStaredCount;
}
