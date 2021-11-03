package com.tl.consumer.controller;

import com.tl.common.entity.Goods;
import com.tl.common.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/consumer/ordering")
public class OrderController {

    @Autowired
    private FeginClient feginClient;


    //添加订单
    @RequestMapping("/addOrdering")
    @ResponseBody
    public void addOrdering(HttpServletRequest request, @RequestBody List<Goods> s) {
        //token这里则是userid
        String token = request.getHeader("curUserid");
        //调用远程服务
       feginClient.addOrdering(s, token);
    }


    //获取对应状态订单
    @RequestMapping("/orderingStatus")
    @ResponseBody
    public String orderingStatus(HttpServletRequest request, @RequestParam("status") Integer status) {
        //token这里则是userid
        String token = request.getHeader("curUserid");
        //调用远程服务
        return feginClient.orderingStatus(status, token);
    }

    //删除订单
    @RequestMapping("/deleteOrdering")
    @ResponseBody
    public void deleteOrdering(HttpServletRequest request, @RequestParam("list") String list) {
        //token这里则是userid
        String token = request.getHeader("curUserid");
        //调用远程服务,传字符过去进行分段处理获取每个编号
        feginClient.deleteOrdeing(list, token);
    }
}
