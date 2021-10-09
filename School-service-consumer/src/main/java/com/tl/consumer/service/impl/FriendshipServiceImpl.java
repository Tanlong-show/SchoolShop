package com.tl.consumer.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tl.consumer.entity.Friendship;
import com.tl.consumer.mapper.FriendshipMapper;
import com.tl.consumer.service.FriendshipService;
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
public class FriendshipServiceImpl extends ServiceImpl<FriendshipMapper, Friendship> implements FriendshipService {

}
