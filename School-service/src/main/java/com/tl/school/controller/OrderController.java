package com.tl.school.controller;
import com.tl.common.entity.Goods;
import com.tl.common.entity.Orders;
import com.tl.common.entity.User;
import com.tl.common.entityView.OrdersShop;
import com.tl.school.service.GoodsService;
import com.tl.school.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tl
 * @since 2021-10-21
 */
@RestController
@RequestMapping("/ordering")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    GoodsService goodsService;


    //添加订单
    @RequestMapping("/addOrdering")
    public void addOrdering(@RequestParam("token")String token, @RequestBody List<Goods> goods) {

        for (int i = 0; i < goods.size(); i++) {
            Orders order = new Orders();
            order.setState(0);
            //暂时写死，后面实现springcloud的session一致性再改
            //已改，通过提供方headers提取转发的
            order.setBuyerId(Integer.parseInt(token));
            order.setGoodsId(goods.get(i).getId());
            String s = "TL"+ (int)(Math.random()*100000000);
            order.setOrdNumber(s);
            order.setSellerId(goods.get(i).getUserId());
            orderService.addOrdering(order);
        }

    }


    //根据不同状态获取订单
    @RequestMapping("/orderingStatus")
    public List<OrdersShop> orderingStatus(@RequestParam("token")String token, @RequestParam("status") Integer status) {
        int userId = Integer.parseInt(token);
        List<OrdersShop> ordersShopList = new ArrayList<>();
        //找出对应订单
        List<Orders> ordersList = orderService.selectOrderBystatusAndUserId(status, userId);
        List<Goods> goodsList = new ArrayList<>();
        //根据订单找出对应商品
        for (int i = 0; i < ordersList.size(); i++) {
            goodsList.add(goodsService.findGoodById(ordersList.get(i).getGoodsId()));
        }
        for (int i = 0; i < ordersList.size(); i++) {
            OrdersShop ordersShop = new OrdersShop();
            ordersShop.setBuyerId(ordersList.get(i).getBuyerId());
            ordersShop.setName(goodsList.get(i).getName());
            ordersShop.setOrdNumber(ordersList.get(i).getOrdNumber());
            ordersShop.setPictureUrl(goodsList.get(i).getPictureUrl());
            ordersShop.setPrice(goodsList.get(i).getPrice());
            ordersShop.setState(ordersList.get(i).getState());
            ordersShop.setUpdateTime(ordersList.get(i).getUpdateTime());
            ordersShop.setSellerId(ordersList.get(i).getSellerId());
            ordersShopList.add(ordersShop);
        }

        return ordersShopList;
    }

    //批量删除订单
    @RequestMapping("/deleteOrdeing")
    public void deleteOrdeing(@RequestParam("token")String token, @RequestParam("list")String list){

        List<String>stringList = Arrays.asList(list.split(","));
        orderService.deleteOrdersByOrdNum(stringList, Integer.parseInt(token));
    }

}

