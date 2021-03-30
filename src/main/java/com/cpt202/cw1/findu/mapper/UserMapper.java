package com.cpt202.cw1.findu.mapper;

import com.cpt202.cw1.findu.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface UserMapper {
    User findByEmail(String email);
    User findByName(String name);
    List<User> findAll();
    void registerUser(String email,String name,String password);
}
