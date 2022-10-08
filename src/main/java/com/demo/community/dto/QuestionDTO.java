package com.demo.community.dto;

import com.demo.community.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author foofoo3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
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
    private User user;
}
