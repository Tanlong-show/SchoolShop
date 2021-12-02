package com.tl.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tl.common.entity.User;
import com.tl.common.entityView.UserMessage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tl
 * @since 2021-10-15
 */
public interface UserService extends IService<User> {
    //更新/新增user
    public void updateOrInsertUser(User user);

    List<User> findByUserId(String userId);

    List<User> findById(Integer id);

    List<User> findAllUser();

    List<User> getMyMessagePerson(Integer myId);

    List<UserMessage> findAllUserMessage(String userId);

}
