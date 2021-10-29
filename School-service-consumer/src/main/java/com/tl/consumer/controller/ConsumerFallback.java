package com.tl.consumer.controller;

import com.tl.common.entity.Goods;
import org.springframework.stereotype.Component;

import java.util.List;


//当服务方的服务断掉时的熔断机制，
@Component
public class ConsumerFallback implements FeginClient {

    @Override
    public String validateUser(String username, String password) {
        return "Feign客户端访问失败!";
    }

    @Override
    public String getUser(String username) {
        return "Feign客户端访问失败!";
    }

    @Override
    public String getGoodsList() {
        return "Feign客户端访问失败!";
    }

    @Override
    public String addOrdering(List<Goods> order) {
        return "Feign客户端访问失败!";
    }

    @Override
    public String orderingStatus(Integer status) {
        return "Feign客户端访问失败!";
    }

    @Override
    public String deleteOrdeing(String list) {
        return "Feign客户端访问失败!";
    }

}
