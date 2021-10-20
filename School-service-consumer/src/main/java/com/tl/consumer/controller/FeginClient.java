package com.tl.consumer.controller;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


//fallback为熔断器,ConsumerFallback为自定义的熔断器，当服务提供方出问题时的熔断转移策略。
@Component
@FeignClient(name = "SCHOOLSHOP-SERVICE-PROVIDER" ,fallback=ConsumerFallback.class)
public interface FeginClient {

    @LoadBalanced //负载均衡
    @RequestMapping(value = "/user/validateUser")
    String validateUser(@RequestParam("userid") String userid, @RequestParam("password") String password);

    @LoadBalanced //负载均衡
    @RequestMapping(value = "/user/getUser")
    String getUser(@RequestParam("userid") String userid);

    @LoadBalanced //负载均衡
    @RequestMapping(value = "/goods/getGoodsList")
    String getGoodsList();

}
