package com.tl.school.service;

import com.tl.common.entity.Chatmessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tl.common.entity.User;
import com.tl.school.mapper.ChatmessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tl
 * @since 2021-11-26
 */
public interface ChatmessageService extends IService<Chatmessage> {

    public void sendMessage(User user, Integer toUserId, String content);

    public List<Chatmessage> getMessage(Integer toUserId, Integer fromUserId);

    public List<Chatmessage> getMyMessage(Integer myId);


}
