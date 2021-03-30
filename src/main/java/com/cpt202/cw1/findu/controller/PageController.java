package com.cpt202.cw1.findu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
public class PageController {
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String Hi() {
        return "mainPage";
    }
}
