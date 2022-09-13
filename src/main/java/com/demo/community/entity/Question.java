package com.demo.community.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private int id;
    private String title;
    private String description;
    private Long gmt_create;
    private Long gmt_modified;
    private String creator;
    private int comment_count;
    private int view_count;
    private int like_count;
    private String tag;
}
