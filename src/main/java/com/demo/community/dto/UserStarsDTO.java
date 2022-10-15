package com.demo.community.dto;

import com.demo.community.entity.LikeStar;
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
    private int comment_count;
    private int view_count;
    private User user;
    private Long star_time;
}
