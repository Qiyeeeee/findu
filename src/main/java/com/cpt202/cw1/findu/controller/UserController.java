package com.cpt202.cw1.findu.controller;

import com.cpt202.cw1.findu.mapper.UserMapper;
import com.cpt202.cw1.findu.pojo.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Random;

@RestController
public class UserController {
    @Resource
    private UserMapper userMapper;

    @GetMapping("/user_findByEmail")
    public User findByEmail(@RequestParam(value = "email") String email) {
        return userMapper.findByEmail(email);
    }


    @GetMapping("/register")
    public String registerUser(@RequestParam(value = "email") String email, @RequestParam(value = "name") String name, @RequestParam(value = "password") String password) {
        Random r = new Random();
        Calendar now = Calendar.getInstance();
        String name_suffix = "";
        String suffix;
        while (true) {
            try {
                r.setSeed(now.getTimeInMillis());
                suffix = String.valueOf((int) ((r.nextDouble() * 9 + 1) * 1000));
                name_suffix = name + "#" + suffix;
                userMapper.findByName(name).getName();
            } catch (java.lang.NullPointerException e) {
                userMapper.registerUser(email, name_suffix, password);
                return name_suffix;
            }
        }

    }

    @GetMapping("/log")
    public String logUser(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
        try {
            String true_password = userMapper.findByEmail(email).getPassword();

            if (true_password.equals(password)) {
                String name = userMapper.findByEmail(email).getName();
                return name + " Login successful";
            } else
                return "Wrong password";
        } catch (java.lang.NullPointerException e) {
            return "The user does not exist";
        }
    }
}
