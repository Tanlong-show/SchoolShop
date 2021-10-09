package com.tl.consumer.controller;

import org.springframework.stereotype.Component;


//当服务方的服务断掉时的熔断机制，
@Component
public class ConsumerFallback implements FeginClient {


    @Override
    public String getUser(String name) {
        return "Feign客户端访问失败!";
    }
}
