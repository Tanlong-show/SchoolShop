package com.tl.consumer.controller;

import com.tl.common.entity.Flashsale;
import com.tl.common.entity.Goods;
import com.tl.common.entity.Information;
import com.tl.common.entity.User;
import com.tl.common.entityView.FlashGoods;
import com.tl.consumer.Configure.FeignInterceptor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//fallback为熔断器,ConsumerFallback为自定义的熔断器，当服务提供方出问题时的熔断转移策略。
@Component
@FeignClient(name = "SCHOOLSHOP-SERVICE-PROVIDER", configuration = {FeignInterceptor.class}, fallback = ConsumerFallback.class)
public interface FeginClient {

    @LoadBalanced //负载均衡,验证用户
    @RequestMapping(value = "/user/validateUser")
    String validateUser(@RequestParam("userid") String userid, @RequestParam("password") String password);

    @LoadBalanced //负载均衡,验证用户
    @RequestMapping(value = "/user/getIpAddress")
    String getIpAddress();

    @LoadBalanced //负载均衡,注册用户
    @RequestMapping(value = "/user/registerUser")
    String registerUser(@RequestParam("username") String username, @RequestParam("userid") String userid, @RequestParam("password") String password);

    @LoadBalanced //负载均衡，取得用户信息
    @RequestMapping(value = "/user/modifyUser")
    String modifyUser(@RequestBody User user);

    @LoadBalanced //负载均衡，取得用户信息
    @RequestMapping(value = "/user/getUser")
    String getUser(@RequestParam("userid") String userid);

    @LoadBalanced //获取所有用户信息
    @RequestMapping(value = "/user/getAllUser")
    String getAllUser();

    @LoadBalanced //负载均衡，获取所有与用户相关订单信息
    @RequestMapping(value = "/user/getAllUserMessage")
    String getAllUserMessage(@RequestParam("userid") String userId);

    @LoadBalanced //负载均衡，获取所有与用户相关订单信息
    @RequestMapping(value = "/user/getMyOrder")
    String getMyOrder(@RequestParam("userid") String userId);

    @LoadBalanced //获取视图
    @RequestMapping(value = "/user/getAllViews")
    String getAllViews();

    @LoadBalanced //负载均衡，获取所有商品列表
    @RequestMapping(value = "/goods/getGoodsList")
    String getGoodsList();

    @LoadBalanced //负载均衡,获取一级分类
    @RequestMapping(value = "/goods/getGoodsSortOne")
    String getGoodsSortOne(@RequestParam("sortOneName") String sortOneName);

    @LoadBalanced //负载均衡,获取一级对应二级分类
    @RequestMapping(value = "/goods/getGoodsSortTwo")
    String getGoodsSortTwo(@RequestParam("sortOneName") String SortOneName, @RequestParam("sortTwoName") String sortTwoName);

    @LoadBalanced //负载均衡,智能多级搜索
    @RequestMapping(value = "/goods/getGoodsAdvanced")
    String getGoodsAdvanced(@RequestParam("name") String name, @RequestParam("sortOneName") String sortOneName, @RequestParam("sortTwoName") String sortTwoName);

    @LoadBalanced //负载均衡,添加订单
    @RequestMapping(value = "/ordering/addOrdering")
    String addOrdering(List<Goods> order, @RequestParam("token") String token);

    @LoadBalanced //负载均衡,获取特定状态订单
    @RequestMapping(value = "/ordering/orderingStatus")
    String orderingStatus(@RequestParam("status") Integer status, @RequestParam("token") String token);

    @LoadBalanced //负载均衡,批量删除订单
    @RequestMapping(value = "/ordering/deleteOrdeing")
    String deleteOrdeing(@RequestParam("list") String list, @RequestParam("token") String token);

    @LoadBalanced //负载均衡,查找当前用户商品信息
    @RequestMapping(value = "/goods/getGoodsListByUserId")
    String getGoodsListByUserId(@RequestParam("token") String token, @RequestParam("name") String name, @RequestParam("sortOneName") String sortOneName, @RequestParam("sortTwoName") String sortTwoName);

    @LoadBalanced //负载均衡,查找当前用户商品信息
    @RequestMapping(value = "/goods/getGoodsListByState")
    String getGoodsListByState(@RequestParam("name") String name, @RequestParam("sortOneName") String sortOneName, @RequestParam("sortTwoName") String sortTwoName);


    @LoadBalanced //负载均衡,批量删除订单
    @RequestMapping(value = "/goods/lowerShelfById")
    String lowerShelfById(@RequestParam("token") String token, @RequestParam("id") Integer id);


    @LoadBalanced //负载均衡,批量删除订单
    @RequestMapping(value = "/goods/getGoodsById")
    String getGoodsById(@RequestParam("id") Integer id);

    @LoadBalanced //负载均衡,更新商品
    @RequestMapping(value = "/goods/updateGoods")
    String updateGoods(@RequestParam("token") String token, @RequestBody Goods goods);

    @LoadBalanced //负载均衡,更新商品
    @RequestMapping(value = "/goods/getAuditShow")
    String getAuditShow(@RequestParam("id") Integer id);

    @LoadBalanced //负载均衡,更新商品状态
    @RequestMapping(value = "/goods/updateGoodsBystate")
    String updateGoodsBystate(@RequestParam("token") String token, @RequestParam("goodsId") Integer goodsId, @RequestParam("state") Integer state
            , @RequestParam(name = "content", value = "", required = false) String content);

    @LoadBalanced //发送消息,都是id，不是userId
    @RequestMapping(value = "/chatmessage/sendMessage")
    String sendMessage(@RequestParam("toUserId") Integer toUserId, @RequestParam("content") String content,
                       @RequestParam("fromUserId") Integer fromUserId);

    @LoadBalanced //获取消息,都是id，不是userId
    @RequestMapping(value = "/chatmessage/getMessage")
    String getMessage(@RequestParam("toUserId") Integer toUserId, @RequestParam("fromUserId") Integer fromUserId);

    @LoadBalanced //获取消息,都是id，不是userId
    @RequestMapping(value = "/chatmessage/getMyMessage")
    String getMyMessage(@RequestParam("myId") Integer myId);


    @LoadBalanced //公告发布
    @RequestMapping(value = "/information/publishAnnounce")
    String publishAnnounce(@RequestParam("token") String token, @RequestBody Information information);

    @LoadBalanced //获取所有公告
    @RequestMapping(value = "/information/getAllAnnounce")
    String getAllAnnounce();

    @LoadBalanced //插入秒杀商品
    @RequestMapping(value = "/flashsale/insertFlashSale")
    String insertFlashSale(@RequestBody Flashsale flashsale);


    @LoadBalanced //获取所有公告
    @RequestMapping(value = "/flashsale/getAllFlashSale")
    String getAllFlashSale();




}
