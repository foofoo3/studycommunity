package com.demo.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private int number;
    private String password;
    private int uid ;

}
