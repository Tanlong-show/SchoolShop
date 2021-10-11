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


    @RequestMapping("/login")
    @ResponseBody
    public String getUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        //调用远程服务
//		ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://sbc-order/getOrder", String.class);
//        logger.info("res="+JSON.toJSONString(responseEntity));
        System.out.println("TL:  "+username);
        String result = feginClient.getUser(username,password);
        return result;
    }
}

