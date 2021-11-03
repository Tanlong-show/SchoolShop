package com.tl.consumer.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/consumer/goods")
public class GoodsController {

    @Autowired
    private FeginClient feginClient;


    //查询所有产品列表
    @RequestMapping("/getGoodsListByUserId")
    @ResponseBody
    public String getGoodsListByUserId(HttpServletRequest request,@RequestParam("name")String name, @RequestParam("sortOneName")String sortOneName, @RequestParam("sortTwoName")String sortTwoName) {
        //token这里则是userid
        String token = request.getHeader("curUserid");
        return feginClient.getGoodsListByUserId(token, name, sortOneName, sortTwoName);
    }

    //查询所有产品列表
    @RequestMapping("/getGoodsList")
    @ResponseBody
    public String getGoodsList() {
        String result = feginClient.getGoodsList();
        return result;
    }

    //查询产品一级分类
    @RequestMapping("/getGoodsSortOne")
    @ResponseBody
    public String getGoodsSortOne(@RequestParam("sortOneName") String sortOneName) {
        String result = feginClient.getGoodsSortOne(sortOneName);
        return result;
    }

    //查询产品一级对应的二级分类
    @RequestMapping("/getGoodsSortTwo")
    @ResponseBody
    public String getGoodsSortTwo(@RequestParam("sortOneName") String sortOneName, @RequestParam("sortTwoName") String SortTwoName) {
        String result = feginClient.getGoodsSortTwo(sortOneName, SortTwoName);
        return result;
    }

    //多级智能条件判断搜索
    @RequestMapping(value = "/getGoodsAdvanced")
    public String getGoodsAdvanced(@RequestParam(value = "name",required = false)String name, @RequestParam(value = "sortOneName",required = false)String SortOneName,
                            @RequestParam(value = "sortTwoName",required = false)String SortTwoName){
        String result = feginClient.getGoodsAdvanced(name, SortOneName, SortTwoName);
        return result;
    }


}
