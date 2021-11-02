package com.tl.consumer.controller;

import com.tl.common.entity.Goods;
import com.tl.common.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/consumer/ordering")
public class OrderController {

    @Autowired
    private FeginClient feginClient;


    //添加订单
    @RequestMapping("/addOrdering")
    @ResponseBody
    public void addOrdering(@RequestBody List<Goods> s) {
        //调用远程服务
       feginClient.addOrdering(s);
    }


    //获取对应状态订单
    @RequestMapping("/orderingStatus")
    @ResponseBody
    public String orderingStatus(@RequestParam("status") Integer status) {
        //调用远程服务
        return feginClient.orderingStatus(status);
    }

    //获取对应状态订单
    @RequestMapping("/deleteOrdering")
    @ResponseBody
    public void deleteOrdering(@RequestParam("list") String list) {
        //调用远程服务,传字符过去进行分段处理获取每个编号
        feginClient.deleteOrdeing(list);
    }
}
