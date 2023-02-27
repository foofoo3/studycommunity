package com.demo.community.service;

import com.demo.community.entity.Admin;

/**
 * @Author: foofoo3
 */
public interface AdminService {

    int loginAdmin(String name, String password);

    Admin selectadminByname(String name);

    void updateAnnouncement(int id, String announcement);

    Admin selectadminById(int i);
}
