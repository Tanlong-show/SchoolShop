package com.tl.school.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tl.common.entity.Chatmessage;
import com.tl.common.entity.Goods;
import com.tl.common.entity.Orders;
import com.tl.common.entity.User;
import com.tl.common.entityView.UserMessage;
import com.tl.school.Util.RedisUtil;
import com.tl.school.mapper.GoodsMapper;
import com.tl.school.mapper.OrderMapper;
import com.tl.school.mapper.UserMapper;
import com.tl.school.service.ChatmessageService;
import com.tl.school.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tl
 * @since 2021-10-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ChatmessageService chatmessageService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    RedisUtil redisUtil;

    //更新/新增user
    @Override
    public void updateOrInsertUser(User user) {
        if (user.getId() != null) {
            userMapper.updateById(user);
        } else {
            userMapper.insert(user);
        }
    }

    @Override
    public List<User> findByUserId(String userId) {

        return userMapper.selectList(Wrappers.<User>lambdaQuery()
                .eq(User::getUserId, userId));
    }

    @Override
    public List<User> findById(Integer id) {
        HashMap hashMap = new HashMap();
        hashMap.put("id", id);
        return userMapper.selectByMap(hashMap);
    }

    @Override
    public List<User> findAllUser() {
        return userMapper.selectList(Wrappers.<User>lambdaQuery().isNotNull(User::getId));
    }

    @Override
    public List<User> getMyMessagePerson(Integer myId) {
        //先获取相关信息，再通过信息找用户id
        List<Chatmessage> chatmessageList = chatmessageService.getMyMessage(myId);
        Set set = new HashSet();
        for (int i = 0; i < chatmessageList.size(); i++) {
            set.add(chatmessageList.get(i).getToId());
            set.add(chatmessageList.get(i).getFromId());
        }
        Iterator iterator = set.iterator();
        List<User> userList = new ArrayList<>();
        while (iterator.hasNext()) {
            Integer z = (Integer) iterator.next();
            if (z != 0 && z != myId) {
                User user = userMapper.selectById(z);
                userList.add(user);
            }
        }

        return userList;
    }

    @Override
    public List<UserMessage> findAllUserMessage(String userId) {
        List<Orders> ordersList = orderMapper.selectList(Wrappers.<Orders>lambdaQuery()
                .eq(Orders::getSellerId, userId));
        List<UserMessage> userMessageList = new LinkedList<>();
        //构建信息流userMessage
        for (int i = 0; i < ordersList.size(); i++) {
            UserMessage userMessage = new UserMessage();
            userMessage.setBuyerId(ordersList.get(i).getBuyerId());
            userMessage.setBuyerName((String) redisUtil.get("" + ordersList.get(i).getBuyerId()));
            userMessage.setGoodId(ordersList.get(i).getGoodsId());
            userMessage.setGoodName(goodsMapper.selectById(ordersList.get(i).getGoodsId()).getName());
            userMessage.setTime(ordersList.get(i).getUpdateTime());
            userMessage.setOrdNumber(ordersList.get(i).getOrdNumber());
            List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery()
                    .eq(User::getUserId, ordersList.get(i).getBuyerId()));

            userMessage.setHeadpic(users.get(0).getHeadpicture());
            userMessage.setBuyerMainId(users.get(0).getId());
            int state = ordersList.get(i).getState();
            if (state == 0) {
                userMessage.setType("下单了");
            } else if (state == 1) {
                userMessage.setType("支付了订单产品");
            } else {
                userMessage.setType("取消了订单产品");
            }
            userMessageList.add(userMessage);
        }

        return userMessageList;
    }
}

