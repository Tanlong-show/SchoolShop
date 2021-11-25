package com.tl.school.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tl.common.entity.Goods;
import com.tl.common.entity.Workflow;
import com.tl.common.entityView.AuditShow;
import com.tl.school.Util.WorkflowUtil;
import com.tl.school.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
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
    @Autowired
    WorkflowUtil workflowUtil;

    @RequestMapping("/getGoodsList")
    public List<Goods> goodsList() {
        return goodsService.findGoodsList();
    }

    @RequestMapping(value = "/getGoodsSortOne")
    public List<Goods> getGoodsSortOne(@RequestParam("sortOneName") String sortOneName) {
        List<Goods> goodsList = goodsService.findGoodsSortOne(sortOneName);

        return goodsList;
    }

    @RequestMapping(value = "/getGoodsSortTwo")
    public List<Goods> getGoodsSortTwo(@RequestParam("sortOneName") String SortOneName, @RequestParam("sortTwoName") String sortTwoName) {
        List<Goods> goodsList = goodsService.findGoodsSortTwo(SortOneName, sortTwoName);

        return goodsList;
    }

    @RequestMapping(value = "/getGoodsAdvanced")
    public List<Goods> getGoodsAdvanced(@RequestParam("name") String name, @RequestParam("sortOneName") String SortOneName, @RequestParam("sortTwoName") String sortTwoName) {
        List<Goods> goodsList = goodsService.findGoodAdvanced(name, SortOneName, sortTwoName);

        return goodsList;
    }

    //一二级搜索，根据用户userId查的
    @RequestMapping(value = "/getGoodsListByUserId")
    public List<Goods> getGoodsListByUserId(@RequestParam("token") String token, @RequestParam("name") String name, @RequestParam("sortOneName") String SortOneName, @RequestParam("sortTwoName") String sortTwoName) {
        List<Goods> goodsList = goodsService.findGoodAdvancedByUserId(token, name, SortOneName, sortTwoName);

        return goodsList;
    }

    //一二级搜索，管理员根据state查询
    @RequestMapping(value = "/getGoodsListByState")
    public List<Goods> getGoodsListByState(@RequestParam("name") String name, @RequestParam("sortOneName") String SortOneName, @RequestParam("sortTwoName") String sortTwoName) {
        List<Goods> goodsList = goodsService.findGoodAdvancedByState(name, SortOneName, sortTwoName);

        return goodsList;
    }


    //上下架通用
    @RequestMapping(value = "/lowerShelfById")
    public void lowerShelfById(@RequestParam("token") String token, @RequestParam("id") Integer id) {
        Workflow workflow = new Workflow();
        Goods goods = goodsService.findGoodById(id);
        if (goods.getState() == 0) {
            workflow.setState(0);
        } else {
            workflow.setState(1);
        }
        workflow.setGoodsId(id);
        workflow.setUserId(token);
        workflowUtil.add(workflow);

        //上下架通用
        goodsService.lowerShelfById(id);

    }

    @RequestMapping(value = "/getGoodsById")
    public Goods getGoodsById(@RequestParam("id") Integer id) {
        //获取商品信息
        return goodsService.getGoodsById(id);

    }


    @RequestMapping(value = "/updateGoods")
    public void updateGoods(@RequestParam("token") String token, @RequestBody Goods goods) {
        //这里工作流得等商品创建后才能添加记录
        //新增、修改商品信息
        goodsService.updateGoods(token, goods);

    }

    @RequestMapping(value = "/getAuditShow")
    List<AuditShow> getAuditShow(@RequestParam("id") Integer id) {

        return workflowUtil.getAuditShow(id);
    }

    //同意/驳回商品操作，4-创建驳回 7-修改驳回 ,content为空则是审核同意， 3-创建通过 6-修改通过
    @RequestMapping(value = "/updateGoodsBystate")
    void updateGoodsBystate(@RequestParam("token") String token, @RequestParam("goodsId") Integer goodsId, @RequestParam("state") Integer state
            , @RequestParam(name = "content", value = "", required = false) String content) {

        Workflow workflow = new Workflow();
        workflow.setUserId(token);
        if (content != "") {
            workflow.setGoodsId(goodsId);
            workflow.setState(state);
            workflow.setReject(content);
            workflowUtil.add(workflow);
            //审核未通过，更新商品state
            Goods goods = goodsService.findGoodById(goodsId);
            goods.setState(4);//4-审核未通过
            //无需进入工作流的更改商品状态信息
            goodsService.updateGoodsOk(goods);

        } else {
            //先加入审核通过后的信息
            workflow.setGoodsId(goodsId);
            workflow.setState(state);
            workflowUtil.add(workflow);
            //审核通过后直接进入上架状态，并进入工作流中
            workflow.setState(0);
            workflowUtil.add(workflow);
            //更新商品上架状态
            Goods goods = goodsService.findGoodById(goodsId);
            goods.setState(1);//1已经上架
            //无需进入工作流的更改商品状态信息
            goodsService.updateGoodsOk(goods);
        }
    }

}

