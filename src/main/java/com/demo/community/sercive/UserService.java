package com.demo.community.sercive;

import com.demo.community.entity.User;
import com.demo.community.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public int insertUser(String name,int number, String password) {
        log.info("name:{}", name);
        log.info("number:{}", number);
        log.info("password:{}", password);
//        查询账号,用户名是否重复
        if (userMapper.SelectByNumber(number)!=null){
            return 0;
        }else if (userMapper.SelectByName(name)!=null){
            return 2;
        }else {
            int result = userMapper.InsertUser(name, number, password);
            if (result == 1) {
                return 1;
            }
        }
        return 1;
    }

//    登录核对
    public int loginUser(int number, String password){
        log.info("number:{}", number);
        log.info("password:{}", password);

        User user = userMapper.SelectByNumber(number);
        log.info("user:{}", user);

        if (user==null) {
            return 0;
        }else if(!user.getPassword().equals(password)){
            return 2;
        }else if (user.getPassword().equals(password)){
            return 1;
        }
        return 1;
    }

//    查询用户
    public User getUserByNumber(int number){
        User CurrentUser = userMapper.SelectByNumber(number);
        log.info("CurrentUser:{}",CurrentUser);
        return CurrentUser;
    }


}
