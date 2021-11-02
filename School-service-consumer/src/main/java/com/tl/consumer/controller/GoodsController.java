package com.tl.consumer.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer/goods")
public class GoodsController {

    @Autowired
    private FeginClient feginClient;


    //验证登录信息
    @RequestMapping("/getGoodsList")
    @ResponseBody
    public String getGoodsList() {
        String result = feginClient.getGoodsList();
        return result;
    }
}
