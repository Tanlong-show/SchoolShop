package com.tl.consumer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tl
 * @since 2021-10-08
 */
@RestController
@RequestMapping("/consumer")
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
        String result = feginClient.validateUser(userid,password);
        return result;
    }

    //获取用户数据
    @RequestMapping("/getUser")
    @ResponseBody
    public String getUser(@RequestParam("userid") String userid) {
        //调用远程服务
        String result = feginClient.getUser(userid);
        System.out.println(result);
        return result;
    }

}

