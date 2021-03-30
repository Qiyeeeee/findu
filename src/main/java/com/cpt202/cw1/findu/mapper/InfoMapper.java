package com.cpt202.cw1.findu.mapper;

import com.cpt202.cw1.findu.pojo.Info;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface InfoMapper {
    Info findByName(String name);
    List<Info> findAll();
}
