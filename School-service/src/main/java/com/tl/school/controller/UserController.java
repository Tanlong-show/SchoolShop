package com.tl.school.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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
@RequestMapping("/school/user")
class UserController {
    @Autowired
    private LoadBalancerClient loadBalancerClient;//@LoadBalanced，表示需要做负载匀衡。


    @RequestMapping("/provide")
    @ResponseBody
    public String helloWorld(@RequestParam("name") String name) {
        System.out.println("传入的值为："+name);
        return "1111";
        //第一种调用方式
        // String forObject = new RestTemplate().getForObject("http://localhost:8071/Hello/World?s=" + s, String.class);
        // 第二种调用方式
        // 根据服务名 获取服务列表 根据算法选取某个服务 并访问某个服务的网络位置。
        // ServiceInstance serviceInstance = loadBalancerClient.choose("EUREKA-SERVICE");
        // String forObject = new RestTemplate().getForObject("http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/Hello/World?s="+s,String.class);
        // 第三种调用方式 需要restTemplate注入的方式 String forObject = restTemplate.getForObject("http://EUREKA-SERVICE/Hello/World?s=" + s,String.class); return forObject; }}

    }
}

