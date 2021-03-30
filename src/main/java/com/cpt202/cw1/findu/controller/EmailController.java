package com.cpt202.cw1.findu.controller;

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
    Boolean findByEmail(@RequestParam(value = "email") String email, @RequestParam(value = "code") Integer code){
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

    @GetMapping("/sendCode")
    public String saveCode(@RequestParam(value = "email") String email)throws UnsupportedEncodingException, MessagingException {
        try {
            EmailMapper.check_email(email).getEmail();
            return "The mailbox is registered";
        } catch (java.lang.NullPointerException e1) {
            try{
                EmailMapper.check_time(email).getEmail();
                int code = (int) ((Math.random() * 9 + 1) * 100000);
                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
                String dateStr = currentTime.format(fmt);

                EmailMapper.updateInfo(email, code, dateStr);
                EmailUtils.sendEmail(email, String.valueOf(code));

                return String.valueOf(code);

            }catch(java.lang.NullPointerException e2) {
                int code = (int) ((Math.random() * 9 + 1) * 100000);
                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
                String dateStr = currentTime.format(fmt);

                EmailMapper.saveInfo(email, code, dateStr);
                EmailUtils.sendEmail(email, String.valueOf(code));
                return String.valueOf(code);
                }
            catch(javax.mail.SendFailedException e3) {
                return "Invalid Addresses";
            }
        }
    }
}
