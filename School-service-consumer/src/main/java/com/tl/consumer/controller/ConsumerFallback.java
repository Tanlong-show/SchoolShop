package com.tl.consumer.controller;

import com.tl.common.entity.Goods;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getGoodsSortOne(String sortOneName) {
        return "Feign客户端访问失败!";
    }

    @Override
    public String getGoodsSortTwo(String sortOneName, String sortTwoName) {
        return "Feign客户端访问失败!";
    }

    @Override
    public String getGoodsAdvanced(String name, String sortOneName, String sortTwoName) {
        return "Feign客户端访问失败!";
    }


    @Override
    public String addOrdering(List<Goods> order, @RequestParam("token") String token) {
        return "Feign客户端访问失败!";
    }

    @Override
    public String orderingStatus(Integer status, @RequestParam("token") String token) {
        return "Feign客户端访问失败!";
    }

    @Override
    public String deleteOrdeing(String list, @RequestParam("token") String token) {
        return "Feign客户端访问失败!";
    }

    @Override
    public String getGoodsListByUserId(String token, String name, String sortOneName, String sortTwoName) {
        return "Feign客户端访问失败!";
    }

    @Override
    public String lowerShelfById(@RequestParam("token") String token,Integer id) {
        return "Feign客户端访问失败!";
    }

    @Override
    public String getGoodsById(Integer id) {
        return "Feign客户端访问失败!";
    }

    @Override
    public String updateGoods(@RequestParam("token") String token, Goods goods) {
        return "Feign客户端访问失败!";
    }

    @Override
    public String getAuditShow(@RequestParam("id")Integer id) {
        return "Feign客户端访问失败!";
    }


}
