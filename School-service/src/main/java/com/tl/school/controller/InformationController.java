package com.tl.school.controller;


import com.tl.common.entity.Information;
import com.tl.school.Configure.RabbitmqConfig;
import com.tl.school.Util.RedisUtil;
import com.tl.school.service.InformationService;
import io.lettuce.core.RedisURI;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
 * @since 2021-12-09
 */
//公告发布，rabbitMQ消息队列

@RestController
@RequestMapping("/information")
public class InformationController {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    InformationService informationService;

    //发布公告
    @RequestMapping("/publishAnnounce")
    public void announcement(@RequestParam("token") String token, @RequestBody Information information) {
        //发布后要传进数据库存储，同时用消息队列通知所有用户,消费方负责监听，这里消息直接被vue前端的socket监听获取
        String userName = (String) redisUtil.get(token);
        information.setPerson(userName);
        informationService.insertInformation(information);
        //使用rabbitTemplate发送消息
        /**
         * 参数：
         * 1、交换机名称
         * 2、routingKey
         * 3、消息内容
         */
        rabbitTemplate.convertAndSend(RabbitmqConfig.msg_exchang, RabbitmqConfig.msg_routing_key, information);
    }


    //获取所有公告信息
    @RequestMapping("/getAllAnnounce")
    public List<Information> getAllAnnounce() {
        return informationService.getAllAnnounce();

    }
}


