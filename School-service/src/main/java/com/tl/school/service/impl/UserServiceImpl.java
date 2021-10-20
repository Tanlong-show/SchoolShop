package com.tl.school.service.impl;

import com.tl.school.entity.User;
import com.tl.school.mapper.UserMapper;
import com.tl.school.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tl
 * @since 2021-10-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    public UserServiceImpl() {
    }

    public List<User> findByUserId(String userId) {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", userId);
        return this.userMapper.selectByMap(hashMap);
    }
}

