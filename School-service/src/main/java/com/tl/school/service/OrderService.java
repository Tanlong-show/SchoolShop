package com.tl.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tl.common.entity.Orders;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tl
 * @since 2021-10-21
 */
public interface OrderService extends IService<Orders> {

    public void addOrdering(Orders oder);

    public List<Orders> selectOrderBystatusAndUserId(Integer status, Integer userId);

    public void deleteOrdersByOrdNum(List<String>list, Integer userId);

    public List<Orders> selectOrderByUserId(Integer userId);

}
