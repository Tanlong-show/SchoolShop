package com.tl.consumer.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tl.consumer.entity.User;
import com.tl.consumer.mapper.UserMapper;
import com.tl.consumer.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tl
 * @since 2021-10-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
