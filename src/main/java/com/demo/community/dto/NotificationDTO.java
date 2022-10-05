package com.demo.community.dto;

import com.demo.community.entity.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmt_create;
    private Integer status;
    private int notifier;
    private String notifierName;
    private String outerTitle;
    private String type;
}
