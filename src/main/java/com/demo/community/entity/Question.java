package com.demo.community.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author foofoo3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
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
    @TableLogic //逻辑删除
    private Integer deleted;
}
