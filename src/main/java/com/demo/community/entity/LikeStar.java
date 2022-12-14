package com.demo.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: foofoo3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeStar {
    private int type;
    private int uid;
    private Long target_id;
    @TableId(type = IdType.AUTO)
    private int id;
    private Long gmt_create;
    private int parent_id;
    @TableLogic //逻辑删除
    private Integer deleted;
}
