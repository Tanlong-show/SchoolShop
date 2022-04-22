package com.tl.school.controller;


import com.tl.common.entity.Flashsale;
import com.tl.common.entity.Goods;
import com.tl.common.entity.Orders;
import com.tl.common.entity.User;
import com.tl.common.entityView.FlashGoods;
import com.tl.school.Util.RedisUtil;
import com.tl.school.mapper.FlashsaleMapper;
import com.tl.school.service.FlashsaleService;
import com.tl.school.service.GoodsService;
import com.tl.school.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tl
 * @since 2021-12-29
 */
@RestController
@RequestMapping("/flashsale")
public class FlashsaleController {

    @Autowired
    FlashsaleService flashsaleService;
    @Autowired
    FlashsaleMapper flashsaleMapper;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;

    //插入秒杀商品
    @RequestMapping("/insertFlashSale")
    @ResponseBody
    public void insertFlashSale(@RequestBody Flashsale flashsale) {
        Goods goods = goodsService.findGoodById(flashsale.getGoodsId());
        flashsaleService.insertFlashSale(flashsale);
    }

    //获取所有秒杀商品信息
    @RequestMapping("/getAllFlashSale")
    @ResponseBody
    public List<FlashGoods> getAllFlashSale() {
        return flashsaleService.getAllFlashSale();
    }

    //秒抢，这里的id是flashsale的主键id
    @RequestMapping("/flashBuy")
    @ResponseBody
    public String flashBuy(@RequestParam("userId") String userId, @RequestParam("id") Integer id){
        String s = "flashsale"+id;
        Flashsale flashsale = flashsaleMapper.selectById(id);
        Goods goods = goodsService.findGoodById(flashsale.getGoodsId());
        Integer num = null;
        if(redisUtil.get(s) != null){
            num = (Integer)redisUtil.get(s);
        }else{
            num = null;
        }

        if(num != null){
            if(num > 0){
                redisUtil.set(s, num - 1);
                Orders order = new Orders();
                order.setState(0);
                //订单生成
                order.setBuyerId(Integer.parseInt(userId));
                order.setGoodsId(goods.getId());
                String s2 = "TL"+ (int)(Math.random()*100000000);
                order.setOrdNumber(s2);
                order.setSellerId(goods.getUserId());
                //这里先存进redis
                List<Orders> flashorderList = (List<Orders>) redisUtil.get("flashorderList");
                if(flashorderList != null){
                    flashorderList.add(order);
                }else{
                    flashorderList = new LinkedList<>();
                    flashorderList.add(order);
                }
                redisUtil.set("flashorderList",flashorderList);

                return "秒杀成功！";
            }else{
                return "已经抢空！秒杀失败";
            }
        }else{
            int num2 = goods.getNumber();
            if(num2 > 0){
                redisUtil.set(s, num2 - 1);
                Orders order = new Orders();
                order.setState(0);
                //订单生成
                order.setBuyerId(Integer.parseInt(userId));
                order.setGoodsId(goods.getId());
                String s2 = "TL"+ (int)(Math.random()*100000000);
                order.setOrdNumber(s2);
                order.setSellerId(goods.getUserId());
                //这里先存进redis
                List<Orders> flashorderList = (List<Orders>) redisUtil.get("flashorderList");
                if(flashorderList != null){
                    flashorderList.add(order);
                }else{
                    flashorderList = new LinkedList<>();
                    flashorderList.add(order);
                }
                redisUtil.set("flashorderList",flashorderList);

                return "秒杀成功！";
            }else{
                redisUtil.set(s, num2);
                return "已经抢空！秒杀失败";
            }
        }
    }
}

