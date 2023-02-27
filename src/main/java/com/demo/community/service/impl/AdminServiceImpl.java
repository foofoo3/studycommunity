package com.demo.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.community.entity.Admin;
import com.demo.community.mapper.AdminMapper;
import com.demo.community.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author: foofoo3
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public int loginAdmin(String name, String password) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        Admin admin = adminMapper.selectOne(wrapper);

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

    @Override
    public Admin selectadminByname(String name){
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        return adminMapper.selectOne(wrapper);
    }

    @Override
    public void updateAnnouncement(int id, String announcement) {
        Admin admin = adminMapper.selectById(id);
        admin.setAnnouncement(announcement);
        adminMapper.updateById(admin);
    }

    @Override
    public Admin selectadminById(int adminId) {
        return adminMapper.selectById(adminId);
    }
}
