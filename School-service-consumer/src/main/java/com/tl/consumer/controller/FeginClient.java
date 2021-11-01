package com.tl.consumer.controller;

import com.tl.common.entity.Goods;
import com.tl.consumer.Configure.FeignInterceptor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//fallback为熔断器,ConsumerFallback为自定义的熔断器，当服务提供方出问题时的熔断转移策略。
@Component
@FeignClient(name = "SCHOOLSHOP-SERVICE-PROVIDER" ,configuration ={FeignInterceptor.class},fallback=ConsumerFallback.class)
public interface FeginClient {

    @LoadBalanced //负载均衡,验证用户
    @RequestMapping(value = "/user/validateUser")
    String validateUser(@RequestParam("userid") String userid, @RequestParam("password") String password);

    @LoadBalanced //负载均衡，取得用户信息
    @RequestMapping(value = "/user/getUser")
    String getUser(@RequestParam("userid") String userid);

    @LoadBalanced //负载均衡，获取所有商品列表
    @RequestMapping(value = "/goods/getGoodsList")
    String getGoodsList();

    @LoadBalanced //负载均衡,添加订单
    @RequestMapping(value = "/ordering/addOrdering")
    String addOrdering(List<Goods> order);

    @LoadBalanced //负载均衡,获取特定状态订单
    @RequestMapping(value = "/ordering/orderingStatus")
    String orderingStatus(@RequestParam("status")Integer status);

    @LoadBalanced //负载均衡,批量删除订单
    @RequestMapping(value = "/ordering/deleteOrdeing")
    String deleteOrdeing(@RequestParam("list")String list);


}
