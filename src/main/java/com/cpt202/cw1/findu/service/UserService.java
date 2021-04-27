package com.cpt202.cw1.findu.service;

import com.cpt202.cw1.findu.pojo.User;
import com.cpt202.cw1.findu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("UserService")
public class UserService {

    @Resource
    UserMapper userMapper;

    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

}
