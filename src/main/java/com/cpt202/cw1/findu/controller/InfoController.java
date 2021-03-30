package com.cpt202.cw1.findu.controller;

import com.cpt202.cw1.findu.mapper.InfoMapper;
import com.cpt202.cw1.findu.pojo.Info;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@RestController
public class InfoController {
    @Resource
    private InfoMapper InfoMapper;

    @GetMapping("/info_findByName")
    public Info findByName(@RequestParam(value = "name") String name) {
        return InfoMapper.findByName(name);
    }

    @GetMapping("/fillInfo")
    public Info fillInfo(@RequestParam(value = "name") String name, @RequestParam(value = "gender") String gender, @RequestParam(value = "department") String department, @RequestParam(value = "grade") String grade, @RequestParam(value = "routine") String routine, @RequestParam(value = "contact") String contact, @RequestParam(value = "description") String description) {
        return InfoMapper.findByName(name);
    }
}