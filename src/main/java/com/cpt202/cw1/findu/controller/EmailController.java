package com.cpt202.cw1.findu.controller;

import com.alibaba.fastjson.JSONObject;
import com.cpt202.cw1.findu.EmailUtils;
import com.cpt202.cw1.findu.mapper.EmailMapper;
import com.cpt202.cw1.findu.pojo.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@RestController

public class EmailController {
    @Resource
    private EmailMapper EmailMapper;

    @GetMapping("/checkCode")
    public @ResponseBody
    Object checkCode(@RequestParam(value = "email") String email, @RequestParam(value = "code") Integer code){
        JSONObject jsonObject=new JSONObject();
        try {
            int data_code = EmailMapper.findByEmail(email).getCode();
            String str = EmailMapper.findByEmail(email).getdateStr();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
            LocalDateTime dateStr = LocalDateTime.parse(str, fmt);
            LocalDateTime currentTime = LocalDateTime.now();
            Duration duration = Duration.between(dateStr,currentTime);
            long millis = duration.toMillis();
            if(millis > 600000){
                EmailMapper.del(email);
                jsonObject.put("message", "1");
                return false;
            }
            else
            {
                if(data_code == code){
                    EmailMapper.del(email);
                    jsonObject.put("message", "200");
                    return true;
                }
                else{
                    jsonObject.put("message", "0");
                    return false;
                }

            }
        }catch(java.lang.NullPointerException e)
        {
            jsonObject.put("message", "1");
            return false;
        }
    }

    @GetMapping("/sendCodeForR")
    public Object saveCode(@RequestParam(value = "email") String email)throws UnsupportedEncodingException, MessagingException {
        JSONObject jsonObject=new JSONObject();
        try {
            EmailMapper.check_email(email).getEmail();
            jsonObject.put("message", "2");
        } catch (java.lang.NullPointerException e1) {
            try{
                EmailMapper.check_time(email).getEmail();
                int code = (int) ((Math.random() * 9 + 1) * 100000);
                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
                String dateStr = currentTime.format(fmt);

                EmailMapper.updateInfo(email, code, dateStr);
                EmailUtils.sendEmail(email, String.valueOf(code));
                jsonObject.put("message", "200");
                jsonObject.put("code", String.valueOf(code));

            }catch(java.lang.NullPointerException e2) {
                int code = (int) ((Math.random() * 9 + 1) * 100000);
                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
                String dateStr = currentTime.format(fmt);

                EmailMapper.saveInfo(email, code, dateStr);
                EmailUtils.sendEmail(email, String.valueOf(code));
                jsonObject.put("message", "200");
                jsonObject.put("code", String.valueOf(code));

                }
            catch(javax.mail.SendFailedException e3) {
                jsonObject.put("message", "0");
            }
        }
        return jsonObject;
    }

    @GetMapping("/sendCodeForC")
    public Object saveCodeForC(@RequestParam(value = "email") String email)throws UnsupportedEncodingException, MessagingException {
        JSONObject jsonObject=new JSONObject();
        try{
            EmailMapper.check_time(email).getEmail();
            int code = (int) ((Math.random() * 9 + 1) * 100000);
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
            String dateStr = currentTime.format(fmt);

            EmailMapper.updateInfo(email, code, dateStr);
            EmailUtils.sendEmail(email, String.valueOf(code));

            jsonObject.put("message", "200");
            jsonObject.put("code", String.valueOf(code));

        }catch(java.lang.NullPointerException e2) {
            int code = (int) ((Math.random() * 9 + 1) * 100000);
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
            String dateStr = currentTime.format(fmt);

            EmailMapper.saveInfo(email, code, dateStr);
            EmailUtils.sendEmail(email, String.valueOf(code));
            jsonObject.put("message", "200");
            jsonObject.put("code", String.valueOf(code));
        }
        catch(javax.mail.SendFailedException e3) {
            jsonObject.put("message", "0");
        }

        return jsonObject;

    }
}
