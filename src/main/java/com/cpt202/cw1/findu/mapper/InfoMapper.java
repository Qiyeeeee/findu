package com.cpt202.cw1.findu.mapper;

import com.cpt202.cw1.findu.pojo.Info;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface InfoMapper {
    Info findByName(String name);
    Info findByEmail(String email);
    void fillInfo(String email,String name,  String gender, String department, String grade, String routine, String contact, String description);
    void changeName(String email, String name);
    List<Info> findAll();
}
