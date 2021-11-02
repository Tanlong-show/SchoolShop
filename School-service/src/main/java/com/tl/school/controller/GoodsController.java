package com.tl.school.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tl.common.entity.Goods;
import com.tl.school.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

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

}

