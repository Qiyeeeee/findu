package com.cpt202.cw1.findu.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.cpt202.cw1.findu.mapper.ApplicationMapper;
import com.cpt202.cw1.findu.mapper.TopicMapper;
import com.cpt202.cw1.findu.mapper.Topic_UserMapper;
import com.cpt202.cw1.findu.mapper.UserMapper;
import com.cpt202.cw1.findu.pojo.Application;
import com.cpt202.cw1.findu.pojo.Topic;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TopicController {
    @Resource
    private TopicMapper topicMapper;

    @Resource
    private Topic_UserMapper topic_userMapper;

    @Resource
    private ApplicationMapper application;

    @Resource
    private UserMapper userMapper;

    /*
    * 	@RequestMapping(path = "/addTopic", method = RequestMethod.POST)
	public View addTask(@RequestParam("category") String category, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam("code") String code,
			@RequestParam("id_user") String id_user, HttpServletRequest request)*/
    @RequestMapping(path = "/create_topic", method = RequestMethod.POST)
    public String createNewTopic(@RequestBody Topic topic, HttpServletRequest request){
        String token = request.getHeader("token");
        String user = JWT.decode(token).getAudience().get(0);
        String create_by = user;

        String title = topic.getTitle();
        String gender = topic.getGender();
        String routine = topic.getRoutine();
        String location = topic.getLocation();
        String body = topic.getBody();
        int user_number = topic.getUser_number();
        String post_time = topic.getPost_time();

        List<String> email=topicMapper.selectCreatorEmail();
        if(email.contains(create_by)){
            return "You've already created a topic";
        }
        List<String> apply = application.selectEmail();
        if(apply.contains(create_by)){
            return "You've already applied a topic";
        }
        topicMapper.creatNewTopic(title,post_time,create_by,gender,routine,location,user_number,body);
        return "Create successfully";
    }

    @GetMapping("/search")
    public List<Topic> searchTopicBy(@RequestParam(value = "routine") String routine, @RequestParam(value = "gender") String gender,@RequestParam(value = "location") String location, HttpServletRequest request){
        String email = JWT.decode(request.getHeader("token")).getAudience().get(0);
        List<Topic> l = new ArrayList<>();
        if(!routine.equals("")&&!location.equals("")){
            l = topicMapper.selectNoNull(routine, gender, location);
        }
        if(routine.equals("")&&location.equals("")){
            l = topicMapper.selectAllNull(gender);
        }
        if (routine.equals("")&&!location.equals("")){
            l = topicMapper.selectRoutineNull(gender, location);
        }
        if(!routine.equals("")&&location.equals("")){
            l = topicMapper.selectLocationNull(routine, gender);
        }

        for(int i = l.size()-1;i>=0;i--){
            int num = l.get(i).getUser_number();
            int topic_id = l.get(i).getId();
            List<String> allUser = topic_userMapper.selectByTopicId(topic_id);
            //满员时不显示
            if(allUser.size()==num){
                l.remove(i);
                continue;
            }
            // 申请过的不显示
            if(application.selecIdByEmail(email).contains(l.get(i).getId())){
                l.remove(i);
                continue;
            }

        }

        return l;
    }

    @GetMapping("/display")//可删
    public Topic display(@RequestParam(value = "topic_id") int topic_id){
        Topic t = topicMapper.topic_findById(topic_id);
        return t;
    }

    /*state=0: 请求未处理, state=1: 请求通过, state=2: 请求拒绝*/
    @RequestMapping(path = "/apply", method = RequestMethod.POST)
    public String applyTopic(@RequestBody Application a, HttpServletRequest request){
        String email = JWT.decode(request.getHeader("token")).getAudience().get(0);
        int topic_id = a.getTopic_id();
        String introduction = a.getIntroduction();
        String applytime = a.getApplytime();
        int state = 0;

        List<String> createTopic = topicMapper.selectCreatorEmail();
        if(createTopic.contains(email)){
            return "You've already create a topic";
        }
        List<Integer> hasapplied = application.selecIdByEmail(email);//检查该用户申请的所有帖子
        List<String> joined = topic_userMapper.selectAllUser();
        if(joined.contains(email)){
            return "You've joined a topic";
        }
        if(hasapplied.contains(topic_id)){
            return "You've already applied this topic";
        }
        application.apply(email,topic_id,introduction,applytime,state);
        return "Apply successfully";
    }

    @GetMapping("/notification")//显示别人和自己的申请
    public ArrayList<JSONObject> checkApplication(HttpServletRequest request) {
        //处理自己发的帖子的申请
        String create_by = JWT.decode(request.getHeader("token")).getAudience().get(0);
        ArrayList<JSONObject> all = new ArrayList();
        Topic topic = topicMapper.topic_findByCreator(create_by);
        if(topic!=null) {
            int topic_id = topic.getId();
            List<String> allApplication = application.selecByTopicId(topic_id);
            for (String a : allApplication) {
                if (application.selectState(topic_id, a) != 0) {
                    continue;
                }//如果请求已经被处理，则不显示
                JSONObject notification = new JSONObject();
                String returntitle = "New Application";
                String name = userMapper.findByEmail(a).getName();
                String topictitle = topicMapper.topic_findById(topic_id).getTitle();
                String time = application.selectApplytime(topic_id, a);
                notification.put("message_title", returntitle);
                notification.put("body", name + " applied to join " + topictitle);
                notification.put("time", time);
                all.add(notification);
            }
        }

        //显示自己的申请
        List<Application> alltopic = application.selecAllByEmail(create_by);
        for (Application a : alltopic) {
            JSONObject notification = new JSONObject();
            if (a.getState() == 1) {//被批准
                String returntitle = "Application Approved";
                String topictitle = topicMapper.topic_findById(a.getTopic_id()).getTitle();
                String time = a.getApplytime();
                notification.put("message_title", returntitle);
                notification.put("body", "You are approved to join " + topictitle);
                notification.put("time", time);
                all.add(notification);
            }
            if (a.getState() == 2) {//被拒绝
                String returntitle = "Application Approved";
                String topictitle = topicMapper.topic_findById(a.getTopic_id()).getTitle();
                String time = a.getApplytime();
                notification.put("message_title", returntitle);
                notification.put("body", "You are rejected to join " + topictitle);
                notification.put("time", time);
                all.add(notification);
            }
        }
        return all;
    }

    @GetMapping("/confirm")//control=1 同意，control=0 拒绝
    public String confirm(@RequestParam(value = "topic_id") int topic_id, @RequestParam(value = "email") String email, @RequestParam(value = "control") int control){
        if(control==1) {
            List<String> allUser = topic_userMapper.selectByTopicId(topic_id);
            if (allUser.size() == topicMapper.topic_findById(topic_id).getUser_number()) {
                return "Your dorm is full";
            }
            List<String> joined = topic_userMapper.selectAllUser();
            if (joined.contains(email)) {
                return "User is not available";
            }
            topic_userMapper.confirm(email, topic_id);
            application.approve(topic_id, email);
            return "He/She joins your dorm";
        }else{
            application.reject(topic_id, email);
            return "He/She has been reject to join your dorm";
        }
    }

    @GetMapping("/deleteTopic")
    public void deleteTopic(){

    }

}
