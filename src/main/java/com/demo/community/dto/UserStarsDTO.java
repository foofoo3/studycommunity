package com.demo.community.dto;

import com.demo.community.entity.User;
import lombok.Data;

/**
 * @Author: foofoo3
 */
@Data
public class UserStarsDTO {
    private int id;
    private String title;
    private String description;
    private Long gmt_create;
    private Long gmt_modified;
    private int creator;
    private int comment_count;
    private int view_count;
    private int like_count;
    private String tag;
    private int star_count;
    private User user;
    private Long star_time;
}
