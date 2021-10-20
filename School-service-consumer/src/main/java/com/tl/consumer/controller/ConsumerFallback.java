package com.tl.consumer.controller;

import org.springframework.stereotype.Component;


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
}
