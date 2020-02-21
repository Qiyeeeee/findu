package com.cpt202.cw1.findu.controller;

import com.alibaba.fastjson.JSONObject;
import com.cpt202.cw1.findu.annotation.UserLoginToken;
import com.cpt202.cw1.findu.mapper.InfoMapper;
import com.cpt202.cw1.findu.pojo.Info;
import com.cpt202.cw1.findu.pojo.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@RestController
public class InfoController {
    @Resource
    private InfoMapper InfoMapper;

    @GetMapping("/info_findByName")
    public Info findByName(@RequestParam(value = "name") String name) {
        try {
            return InfoMapper.findByName(name);
        }
        catch(java.lang.NullPointerException e) {
            return null;
        }
    }

    @GetMapping("/info_findByEmail")
    public Info findByEmail(@RequestParam(value = "email") String email) {
        try {
            return InfoMapper.findByEmail(email);
        }
        catch(java.lang.NullPointerException e) {
            return null;
        }
    }

    @UserLoginToken
    @RequestMapping(path="/fillInfo",method= RequestMethod.POST)
    public Object fillInfo(@RequestBody Info info) {
        JSONObject jsonObject=new JSONObject();
        String email = info.getEmail();
        String name = info.getName();
        String gender = info.getGender();
        String department = info.getDepartment();
        String grade = info.getGrade();
        String routine = info.getRoutine();
        String contact = info.getContact();
        String description = info.getDescription();

        try{
            Info existInfo = InfoMapper.findByEmail(email);
            jsonObject.put("message", "2");

        }
        catch(java.lang.NullPointerException e) {
            InfoMapper.fillInfo(email,name,gender,department,grade,routine,contact,description);
            jsonObject.put("message", "200");
            jsonObject.put("info", InfoMapper.findByEmail(email));

        }
        return jsonObject;
    }

    @GetMapping("/info_changeName")
    public String changeName(@RequestParam(value = "email") String email,@RequestParam(value = "name") String name) {
        try {
            InfoMapper.changeName(email, name);
            return name;
        }
        catch(java.lang.NullPointerException e) {
            return "1";
        }
    }
}