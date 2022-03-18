package com.tl.consumer.controller;


import com.tl.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/consumer/chatmessage")
public class ChatmessageController {

    @Autowired
    private FeginClient feginClient;


    //发送消息
    @RequestMapping("/sendMessage")
    @ResponseBody
    public void sendMessage(@RequestParam("toUserId") Integer toUserId, @RequestParam("content") String content,
                            @RequestParam("fromUserId") Integer fromUserId) {
        feginClient.sendMessage(toUserId, content, fromUserId);
    }

    //获取对应消息
    @RequestMapping("/getMessage")
    @ResponseBody
    public String getMessage(@RequestParam("toUserId") Integer toUserId, @RequestParam("fromUserId") Integer fromUserId) {

        return feginClient.getMessage(toUserId, fromUserId);
    }

    //获取对应消息
    @RequestMapping("/getMyMessage")
    @ResponseBody
    public String getMyMessage(@RequestParam("myId") Integer myId) {

        return feginClient.getMyMessage(myId);
    }

}
