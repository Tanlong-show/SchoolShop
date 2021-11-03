package com.tl.school.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tl.common.entity.Goods;
import com.tl.school.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tl
 * @since 2021-10-15
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/getGoodsList")
    public List<Goods> goodsList(){
        return goodsService.findGoodsList();
    }

    @RequestMapping(value = "/getGoodsSortOne")
    public List<Goods> getGoodsSortOne(@RequestParam("sortOneName")String sortOneName){
        List<Goods> goodsList = goodsService.findGoodsSortOne(sortOneName);

        return goodsList;
    }

    @RequestMapping(value = "/getGoodsSortTwo")
    public List<Goods> getGoodsSortTwo(@RequestParam("sortOneName")String SortOneName, @RequestParam("sortTwoName")String sortTwoName){
        List<Goods> goodsList = goodsService.findGoodsSortTwo(SortOneName, sortTwoName);

        return goodsList;
    }

    @RequestMapping(value = "/getGoodsAdvanced")
    public List<Goods> getGoodsAdvanced(@RequestParam("name")String name, @RequestParam("sortOneName")String SortOneName, @RequestParam("sortTwoName")String sortTwoName){
        List<Goods> goodsList = goodsService.findGoodAdvanced(name, SortOneName, sortTwoName);

        return goodsList;
    }

    @RequestMapping(value = "/getGoodsListByUserId")
    public List<Goods> getGoodsListByUserId(@RequestParam("token")String token,@RequestParam("name")String name, @RequestParam("sortOneName")String SortOneName, @RequestParam("sortTwoName")String sortTwoName){
        List<Goods> goodsList = goodsService.findGoodAdvancedByUserId(token, name, SortOneName, sortTwoName);

        return goodsList;
    }

}

