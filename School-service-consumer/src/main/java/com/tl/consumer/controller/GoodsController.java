package com.tl.consumer.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.org.apache.xpath.internal.objects.XNull;
import com.tl.common.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/consumer/goods")
public class GoodsController {

    @Autowired
    private FeginClient feginClient;


    //查询所某个用户有产品列表，也可以进行三级搜索联动
    @RequestMapping("/getGoodsListByUserId")
    @ResponseBody
    public String getGoodsListByUserId(HttpServletRequest request,@RequestParam("name")String name, @RequestParam("sortOneName")String sortOneName, @RequestParam("sortTwoName")String sortTwoName) {
        //token这里则是userid
        String token = request.getHeader("curUserid");
        return feginClient.getGoodsListByUserId(token, name, sortOneName, sortTwoName);
    }

    //查询所有创建和修改审核的产品列表
    @RequestMapping("/getGoodsListByState")
    @ResponseBody
    public String getGoodsListByState(@RequestParam("name")String name, @RequestParam("sortOneName")String sortOneName, @RequestParam("sortTwoName")String sortTwoName) {

        return feginClient.getGoodsListByState(name, sortOneName, sortTwoName);
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

    //多级智能条件判断搜索
    @RequestMapping(value = "/lowerShelfById")
    public String lowerShelfById(HttpServletRequest request,@RequestParam("id")Integer id){
        String token = request.getHeader("curUserid");
        String result = feginClient.lowerShelfById(token, id);
        return result;
    }

    //根据产品id查信息
    @RequestMapping(value = "/getGoodsById")
    public String getGoodsById(@RequestParam("id")Integer id){
        String result = feginClient.getGoodsById(id);
        return result;
    }

    //更新、添加产品
    @RequestMapping(value = "/updateGoods")
    public void updateGoods(HttpServletRequest request,@RequestBody Goods goods){
        //token这里则是userid
        String token = request.getHeader("curUserid");
        feginClient.updateGoods(token, goods);
    }


    //驳回商品
    @RequestMapping(value = "/updateGoodsBystate")
    public void updateGoodsBystate(HttpServletRequest request,@RequestParam("goodsId")Integer goodsId, @RequestParam("state")Integer state
            ,@RequestParam(name = "content", value = "", required = false) String content){
        //token这里则是userid
        String token = request.getHeader("curUserid");
        System.out.println("state: " + state);
        feginClient.updateGoodsBystate(token, goodsId, state, content);
    }

    //审计商品
    @RequestMapping(value = "/getAuditShow")
    public String getAuditShow(@RequestParam("id")Integer id){

        String result = feginClient.getAuditShow(id);
        return result;
    }


}
