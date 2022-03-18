package com.tl.school.controller;


import com.tl.common.entity.Chatmessage;
import com.tl.common.entity.User;
import com.tl.school.Util.RedisUtil;
import com.tl.school.service.ChatmessageService;
import com.tl.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chatmessage")
public class ChatmessageController {
    @Autowired
    ChatmessageService chatmessageService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserService userService;

    //发送消息
    @RequestMapping("/sendMessage")
    @ResponseBody
    public void sendMessage(@RequestParam("fromUserId") Integer fromUserId, @RequestParam("toUserId")Integer toUserId, @RequestParam("content")String content) {
        List<User> userList = userService.findById(fromUserId);
        User user = userList.get(0);
        chatmessageService.sendMessage(user, toUserId, content);
    }

    //获取消息
    @RequestMapping("/getMessage")
    @ResponseBody
    public List<Chatmessage> getMessage(@RequestParam("fromUserId") Integer fromUserId, @RequestParam("toUserId")Integer toUserId) {
        //内部自动判断是否是大厅消息还是两人间的消息
        return chatmessageService.getMessage(toUserId, fromUserId);
        //        chatmessageService.sendMessage(user, toUserId);
    }

    //获取与我相关的消息的联系人！
    @RequestMapping("/getMyMessage")
    @ResponseBody
    public List<User> getMyMessage(@RequestParam("myId") Integer myId) {
        //获取所有,,,,
        return userService.getMyMessagePerson(myId);
        //        chatmessageService.sendMessage(user, toUserId);
    }
}
