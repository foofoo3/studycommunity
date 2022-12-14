package com.demo.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Notification {
    @TableId(type = IdType.AUTO)
    private Long id;
    private int notifier;
    private int receiver;
    private int outerId;
    private int type;
    private Long gmt_create;
    private int status;
    private String notifier_name;
    private String outer_title;
    @TableLogic //逻辑删除
    private Integer deleted;
}
