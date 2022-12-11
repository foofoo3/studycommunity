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
public class User {
    private String name;
    private int number;
    private String password;
    private int uid ;
    private String description;
    private String face;
    private int type;
    @TableLogic //逻辑删除
    private Integer deleted;
}
