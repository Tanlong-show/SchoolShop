package com.tl.school.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tl.common.entity.Goods;
import com.tl.school.mapper.GoodsMapper;
import com.tl.school.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public List<Goods> findGoodsList() {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("id");
        return goodsMapper.selectList(queryWrapper);
    }

    @Override
    public Goods findGoodById(Integer id) {
        return goodsMapper.selectById(id);
    }
}
