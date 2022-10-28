package com.demo.community.sercive;

import com.demo.community.entity.Admin;
import com.demo.community.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author: foofoo3
 */
@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public int loginAdmin(String name,String password) {
        Admin admin = adminMapper.selectAdminByName(name);

        if (admin == null){
            return 3;
        }else if (!Objects.equals(admin.getPassword(), password)){
            return 4;
        }else if (Objects.equals(admin.getPassword(), password)){
            return 1;
        }else {
            return 0;
        }
    }

    public Admin selectadminByname(String name){
        return adminMapper.selectAdminByName(name);
    }
}
