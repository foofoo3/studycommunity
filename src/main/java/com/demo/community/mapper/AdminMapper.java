package com.demo.community.mapper;

import com.demo.community.entity.Admin;
import com.demo.community.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author: foofoo3
 */
@Repository
public interface AdminMapper {
    @Select("SELECT * from admin where name=#{name}")
    Admin selectAdminByName(@Param("name")String name);
}
