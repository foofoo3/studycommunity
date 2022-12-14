package com.demo.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: foofoo3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Admin {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private String password;
    private String announcement;
}
