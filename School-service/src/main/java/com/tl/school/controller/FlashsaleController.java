package com.tl.school.controller;


import com.tl.common.entity.Flashsale;
import com.tl.common.entity.User;
import com.tl.common.entityView.FlashGoods;
import com.tl.school.service.FlashsaleService;
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
@RequestMapping("/flashsale")
public class FlashsaleController {

    @Autowired
    FlashsaleService flashsaleService;

    //插入秒杀商品
    @RequestMapping("/insertFlashSale")
    @ResponseBody
    public void insertFlashSale(@RequestBody Flashsale flashsale) {
        System.out.println(flashsale.toString());
        flashsaleService.insertFlashSale(flashsale);
    }

    //获取所有秒杀商品信息
    @RequestMapping("/getAllFlashSale")
    @ResponseBody
    public List<FlashGoods> getAllFlashSale() {
        return flashsaleService.getAllFlashSale();
    }
}

