package com.tl.school.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tl.common.entity.Chatmessage;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tl.common.entity.Orders;
import com.tl.common.entity.User;
import com.tl.school.Util.RedisUtil;
import com.tl.school.mapper.ChatmessageMapper;
import com.tl.school.service.ChatmessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Wrapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tl
 * @since 2021-11-26
 */
@Service
public class ChatmessageServiceImpl extends ServiceImpl<ChatmessageMapper, Chatmessage> implements ChatmessageService {

    @Autowired
    ChatmessageMapper chatmessageMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public void sendMessage(User user, Integer toUserId, String content) {
        Chatmessage chatmessage = new Chatmessage();
        chatmessage.setContent(content);
        chatmessage.setFromId(user.getId());
        chatmessage.setToId(toUserId);
        chatmessage.setUserHeadpic(user.getHeadpicture());
        chatmessage.setUserName(user.getName());
        //放入redis中，通过定时任务存进数据库，减少数据库读写压力
        List<Chatmessage> chatmessageList = new ArrayList<>();
        //redis中不为空才能这样先赋值过来,否则下面添加新对象一直报null错
        if (redisUtil.get("Message") != null) {
            chatmessageList = (List<Chatmessage>) redisUtil.get("Message");
        }
        chatmessageList.add(chatmessage);
        redisUtil.set("Message", chatmessageList);
    }

    @Override
    public List<Chatmessage> getMessage(Integer toUserId, Integer fromUserId) {

        List<Chatmessage> chatmessageList = new ArrayList<>();//存放所有的消息，包括数据库+redis中的
        List<Chatmessage> chatmessageList1 = new ArrayList<>();//存放最终筛选好的
        //先把数据库中所有记录放进来
        chatmessageList = chatmessageMapper.selectList(Wrappers.<Chatmessage>lambdaQuery().isNotNull(Chatmessage::getId));


        if (redisUtil.get("Message") != null) {
            List<Chatmessage> chatmessageList2 = new ArrayList<>();
            chatmessageList2 = (List<Chatmessage>) redisUtil.get("Message");
            for (int i = 0; i < chatmessageList2.size(); i++) {
                //再把redis中数据放进来
                chatmessageList.add(chatmessageList2.get(i));
            }
        }
        //判断如果是大厅的消息，则直接返回toUserId带有0的消息
        if (toUserId == 0) {
            for (int i = 0; i < chatmessageList.size(); i++) {
                if (chatmessageList.get(i).getToId() == 0) {
                    chatmessageList1.add(chatmessageList.get(i));
                }
            }
            return chatmessageList1;
//            return chatmessageMapper.selectList(Wrappers.<Chatmessage>lambdaQuery().eq(Chatmessage::getToId, 0));
        }

        for (int i = 0; i < chatmessageList.size(); i++) {
            if ((chatmessageList.get(i).getToId() == toUserId && chatmessageList.get(i).getFromId() == fromUserId)
                    || (chatmessageList.get(i).getFromId() == toUserId && chatmessageList.get(i).getToId() == fromUserId)) {
                chatmessageList1.add(chatmessageList.get(i));
            }
        }
        return chatmessageList1;
//        return chatmessageMapper.selectList(Wrappers.<Chatmessage>lambdaQuery()
//                .or(i -> i.eq(Chatmessage::getFromId, fromUserId).eq(Chatmessage::getToId, toUserId))
//                .or(i -> i.eq(Chatmessage::getToId, fromUserId).eq(Chatmessage::getFromId, toUserId)));

    }

    @Override
    public List<Chatmessage> getMyMessage(Integer myId) {
        return chatmessageMapper.selectList(Wrappers.<Chatmessage>lambdaQuery()
                .or(i -> i.eq(Chatmessage::getFromId, myId))
                .or(i -> i.eq(Chatmessage::getToId, myId)));
    }


}
