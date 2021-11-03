package com.tl.school.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tl.common.entity.Orders;
import com.tl.school.mapper.OrderMapper;
import com.tl.school.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tl
 * @since 2021-10-21
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public void addOrdering(Orders order) {
        orderMapper.insert(order);
    }

    @Override
    public List<Orders> selectOrderBystatusAndUserId(Integer status, Integer userId) {
        return orderMapper.selectList(
                Wrappers.<Orders>lambdaQuery()
                        .eq(Orders::getState, status)
                        .eq(Orders::getBuyerId, userId));
    }

    @Override
    public void deleteOrdersByOrdNum(List<String> list, Integer userId) {
        for (int i = 0; i < list.size(); i++) {
            orderMapper.delete(
                    Wrappers.<Orders>lambdaQuery()
                            .eq(Orders::getOrdNumber, list.get(i))
                            .eq(Orders::getBuyerId, userId));
        }
    }
}
