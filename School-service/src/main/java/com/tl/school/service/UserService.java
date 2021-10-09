package com.tl.school.service;

import com.tl.school.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tl
 * @since 2021-10-08
 */
public interface UserService extends IService<User> {

    public List<User> findByUserId(String userId);

}
