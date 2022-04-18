package com.tl.consumer.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tl.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tl
 * @since 2021-10-08
 */
@RestController
@RequestMapping("/consumer/user")
class UserController {

    @Autowired
    private FeginClient feginClient;


    //验证登录信息
    @RequestMapping("/validateUser")
    @ResponseBody
    public String getUser(@RequestParam("userid") String userid, @RequestParam("password") String password) {
        //调用远程服务
//		ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://sbc-order/getOrder", String.class);
//        logger.info("res="+JSON.toJSONString(responseEntity));
        String result = feginClient.validateUser(userid, password);
        return result;
    }

    //用户注册+验证
    @RequestMapping("/registerUser")
    @ResponseBody
    public String registerUser(@RequestParam("username") String username, @RequestParam("userid") String userid, @RequestParam("password") String password) {

        String result = feginClient.registerUser(username, userid, password);
        return result;
    }

    //修改用户
    @RequestMapping("/modifyUser")
    @ResponseBody
    public String modifyUser(@RequestBody User user) {

        String result = feginClient.modifyUser(user);
        return result;
    }

    //修改用户
    @RequestMapping("/getIpAddress")
    public String getIpAddress() {

        String result = feginClient.getIpAddress();
        return result;
    }

    //获取用户数据
    @RequestMapping("/getUser")
    @ResponseBody
    public String getUser(@RequestParam("userid") String userid) {
        //调用远程服务
        String result = feginClient.getUser(userid);
        return result;
    }

    //获取所有用户数据
    @RequestMapping("/getAllUser")
    @ResponseBody
    public String getAllUser() {
        //调用远程服务
        String result = feginClient.getAllUser();
        return result;
    }

    //获取所有用户数据
    @RequestMapping("/getAllUserMessage")
    @ResponseBody
    public String getAllUserMessage(@RequestParam("userid") String userId) {
        //调用远程服务
        String result = feginClient.getAllUserMessage(userId);
        return result;
    }

    //获取所有用户数据
    @RequestMapping("/getMyOrder")
    @ResponseBody
    public String getMyOrder(@RequestParam("userid") String userId) {
        //调用远程服务
        String result = feginClient.getMyOrder(userId);
        return result;
    }

    //获取所有视图数据
    @RequestMapping("/getAllViews")
    @ResponseBody
    public String getAllViews() {
        //调用远程服务
        String result = feginClient.getAllViews();

        return result;
    }


}

