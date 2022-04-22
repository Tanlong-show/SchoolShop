package com.tl.consumer.controller;


import com.tl.common.entity.Flashsale;
import com.tl.common.entityView.FlashGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
@RequestMapping("/consumer/flashsale")
public class FlashsaleController {

    @Autowired
    private FeginClient feginClient;


    //插入秒杀商品
    @RequestMapping("/insertFlashSale")
    @ResponseBody
    public void insertFlashSale(@RequestBody Flashsale flashsale) {
        System.out.println("SSS: "+flashsale);
        feginClient.insertFlashSale(flashsale);
    }

    //获取所有秒杀商品
    @RequestMapping("/getAllFlashSale")
    @ResponseBody
    public String getAllFlashSale() {
        return feginClient.getAllFlashSale();
    }

    //秒买
    @RequestMapping("/flashBuy")
    @ResponseBody
    public String flashBuy(@RequestParam("userId") String userId, @RequestParam("id")Integer id) {
        return feginClient.flashBuy(userId, id);
    }

}

