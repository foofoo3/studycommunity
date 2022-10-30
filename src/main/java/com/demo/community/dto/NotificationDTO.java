package com.demo.community.dto;

import lombok.Data;
/**
 * @author foofoo3
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmt_create;
    private Integer status;
    private int notifier;
    private String notifier_name;
    private String outer_title;
    private int outerId;
    private String typeName;
    private int type;

}
