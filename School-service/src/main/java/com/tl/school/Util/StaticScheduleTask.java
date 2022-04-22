package com.tl.school.Util;

import com.tl.common.entity.Chatmessage;
import com.tl.common.entity.Orders;
import com.tl.school.mapper.ChatmessageMapper;
import com.tl.school.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class StaticScheduleTask {
    @Autowired
    ChatmessageMapper chatmessageMapper;
    @Autowired
    OrderService orderService;
    @Autowired
    RedisUtil redisUtil;

    //3.添加定时任务
    @Scheduled(cron = "*/15 * * * * ?")
    //或直接指定时间间隔，例如：15秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        List<Chatmessage> chatmessageList = new ArrayList<>();

        if(redisUtil.get("Message") != null){
            chatmessageList = (List<Chatmessage>) redisUtil.get("Message");
            for (int i = 0; i < chatmessageList.size(); i++) {
                chatmessageMapper.insert(chatmessageList.get(i));
            }
            //一旦存进数据库则清空redis中信息
            redisUtil.del("Message");
        }
    }

    //3.添加定时任务
    @Scheduled(cron = "*/10 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void flashgoodsOrderTasks() {
        List<Orders> flashorderList = (List<Orders>) redisUtil.get("flashorderList");

        if(flashorderList != null){
            for (int i = 0; i < flashorderList.size(); i++) {
                orderService.addOrdering(flashorderList.get(i));
            }
            //一旦存进数据库则清空redis中信息
            redisUtil.del("flashorderList");
        }
    }


}
