package com.demo.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private Long id;
    private int notifier;
    private int receiver;
    private Long outerId;
    private int type;
    private Long gmt_create;
    private int status;
}
