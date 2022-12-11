package com.demo.community.entity;

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
    private int id;
    private String name;
    private String password;
    private String announcement;
}
