package com.demo.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.community.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @Author: foofoo3
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {
//    @Select("SELECT * from admin where name=#{name}")
//    Admin selectAdminByName(@Param("name")String name);
//
//    @Select("SELECT * from admin where id=#{id}")
//    Admin selectAdminById(@Param("id")int id);
//
//    @Update("update admin set announcement = #{announcement} where id = #{id}")
//    void updateAnnouncement(Admin admin);
//
//    @Select("SELECT announcement from admin where id=#{id}")
//    String getAnnouncement(@Param("id")int id);
}
