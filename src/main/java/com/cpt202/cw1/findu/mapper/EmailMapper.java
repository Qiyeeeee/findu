package com.cpt202.cw1.findu.mapper;

import com.cpt202.cw1.findu.pojo.Email;

import java.util.List;


public interface EmailMapper {
    Email findByEmail(String email);
    List<Email> findAll();
    void saveInfo(String email, Integer code, String dateStr);
    void updateInfo(String email, Integer code, String dateStr);
    Email check_email(String email);
    Email check_time(String email);
    void del(String email);
}
