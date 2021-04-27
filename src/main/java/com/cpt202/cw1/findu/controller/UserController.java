package com.cpt202.cw1.findu.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.cpt202.cw1.findu.annotation.UserLoginToken;
import com.cpt202.cw1.findu.mapper.EmailMapper;
import com.cpt202.cw1.findu.mapper.UserMapper;
import com.cpt202.cw1.findu.pojo.User;
import com.cpt202.cw1.findu.pojo.User_Cp;
import com.cpt202.cw1.findu.pojo.User_Fo;
import com.cpt202.cw1.findu.pojo.User_Re;
import com.cpt202.cw1.findu.service.TokenService;
import com.cpt202.cw1.findu.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Random;

@RestController
public class UserController {
    @Resource
    UserService userService;
    @Resource
    TokenService tokenService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private EmailMapper EmailMapper;

    Boolean checkCode(String email,Integer code){
        try{
            int data_code = EmailMapper.findByEmail(email).getCode();
            String str = EmailMapper.findByEmail(email).getdateStr();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
            LocalDateTime dateStr = LocalDateTime.parse(str, fmt);
            LocalDateTime currentTime = LocalDateTime.now();
            Duration duration = Duration.between(dateStr,currentTime);
            long millis = duration.toMillis();
            if(millis > 600000){
                EmailMapper.del(email);
                return false;
            }
            else
            {
                if(data_code == code){
                    EmailMapper.del(email);
                    return true;
                }
                else
                    return false;
            }
        }catch(java.lang.NullPointerException e)
        {
            return false;
        }
    }

    @GetMapping("/user_findByEmail")
    public Object findByEmail(@RequestParam(value = "email") String email) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("message", "200");
            jsonObject.put("user", userMapper.findByEmail(email));

        } catch (java.lang.NullPointerException e) {
            jsonObject.put("message", "1");

        }
        return jsonObject;
    }


    @RequestMapping(path="/register",method=RequestMethod.POST)
    public Object registerUser(@RequestBody User_Re user) {
        JSONObject jsonObject=new JSONObject();
        String email = user.getEmail();
        String name = user.getName();
        String password = user.getPassword();
        int code = Integer.parseInt(user.getCode());
        boolean s = checkCode(email,code);

        if(!s){
            jsonObject.put("message", "0");
            return jsonObject;
        }
        else {
            Random r = new Random();
            Calendar now = Calendar.getInstance();
            String name_suffix = "";
            String suffix;
            while (true) {
                try {
                    r.setSeed(now.getTimeInMillis());
                    suffix = String.valueOf((int) ((r.nextDouble() * 9 + 1) * 1000));
                    name_suffix = name + "#" + suffix;
                    userMapper.findByName(name_suffix).getName();
                } catch (java.lang.NullPointerException e) {
                    userMapper.registerUser(email, name_suffix, password);
                    jsonObject.put("name", name_suffix);
                    return jsonObject;
                }
            }
        }

    }

    @RequestMapping(path="/logByEmail",method=RequestMethod.POST)
    public Object logUserByEmail(@RequestBody User user) {
        JSONObject jsonObject=new JSONObject();
        String email = user.getEmail();
        String password = user.getPassword();
        System.out.println("Email: "+email);
        System.out.println(password);
        try {
            String true_password = userMapper.findByEmail(email).getPassword();

            if (true_password.equals(password)) {
                String name = userMapper.findByEmail(email).getName();
                user.setName(name);
                String token = tokenService.getToken(user);
                jsonObject.put("message", "200");
                jsonObject.put("token", token);
                jsonObject.put("user", user);
            }
            else
            {
                jsonObject.put("message", "0");
            }
            return jsonObject;
        } catch (java.lang.NullPointerException e) {
            jsonObject.put("message", "1");
            return jsonObject;
        }
    }

    @RequestMapping(path="/logByName",method=RequestMethod.POST)
    public Object logUserByName(@RequestBody User user) {
        JSONObject jsonObject=new JSONObject();
        String name = user.getName();
        String password = user.getPassword();

        try {
            String true_password = userMapper.findByName(name).getPassword();

            if (true_password.equals(password)) {
                String email = userMapper.findByName(name).getEmail();
                user.setEmail(email);
                String token = tokenService.getToken(user);
                jsonObject.put("message", "200");
                jsonObject.put("token", token);
                jsonObject.put("user", user);
            }
            else
            {
                jsonObject.put("message", "0");
            }
            return jsonObject;
        } catch (java.lang.NullPointerException e) {
            jsonObject.put("message", "1");
            return jsonObject;
        }
    }

    @UserLoginToken
    @GetMapping("/getUserByToken")
    public User getUserByToken(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String userEmail;
        try {
            userEmail = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("Bad Token");
        }
        User user = userService.findByEmail(userEmail);
        return user;
    }

    @UserLoginToken
    @RequestMapping(path="/changePassword",method=RequestMethod.POST)
    public Object ChangePassword(@RequestBody User_Cp cp) {
        JSONObject jsonObject=new JSONObject();
        String email = cp.getEmail();
        String oldP = cp.getOldP();
        String newP = cp.getNewP();
        try {
            String trueP = userMapper.findByEmail(email).getPassword();

            if (trueP.equals(oldP)) {
                userMapper.changePassword(email, newP);
                jsonObject.put("message", "200");
                jsonObject.put("password", newP);
            } else
                jsonObject.put("message", "0");
        } catch (java.lang.NullPointerException e) {
            jsonObject.put("message", "1");
        }
        return jsonObject;
    }

    @RequestMapping(path="/forgetPassword",method=RequestMethod.POST)
    public Object forgetPassword(@RequestBody User_Fo user) {
        JSONObject jsonObject=new JSONObject();
        String email = user.getEmail();
        String password = user.getPassword();
        int code = Integer.parseInt(user.getCode());
        boolean s = checkCode(email,code);

        if(!s){
            jsonObject.put("message", "0");
        }
        else {
            try {
                String oldP = userMapper.findByEmail(email).getPassword();
                userMapper.changePassword(email, password);
                jsonObject.put("message", "200");
                jsonObject.put("password", password);

            } catch (java.lang.NullPointerException e) {
                jsonObject.put("message", "1");
            }
        }
        return jsonObject;
    }

    @GetMapping("/user_changeName")
    public Object ChangeName(@RequestParam(value = "email") String email, @RequestParam(value = "name") String newName) {
        JSONObject jsonObject=new JSONObject();
        Random r = new Random();
        Calendar now = Calendar.getInstance();
        String name_suffix = "";
        String suffix;
        while (true) {
            try {
                r.setSeed(now.getTimeInMillis());
                suffix = String.valueOf((int) ((r.nextDouble() * 9 + 1) * 1000));
                name_suffix = newName + "#" + suffix;
                userMapper.findByName(name_suffix).getName();
            } catch (java.lang.NullPointerException e) {
                try {
                    userMapper.changeName(email, name_suffix);
                    jsonObject.put("message", "200");
                    jsonObject.put("name", name_suffix);

                    return jsonObject;
                } catch (java.lang.NullPointerException e2) {
                    jsonObject.put("message", "1");

                    return jsonObject;
                }
            }
        }
    }
}
