package com.tl.school.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tl.common.entity.Goods;
import com.tl.school.mapper.GoodsMapper;
import com.tl.school.service.GoodsService;
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
        queryWrapper.eq("state", 1);
        return goodsMapper.selectList(queryWrapper);
    }

    @Override
    public Goods findGoodById(Integer id) {
        return goodsMapper.selectById(id);
    }

    @Override
    public List<Goods> findGoodsSortOne(String sortOneName) {
        return goodsMapper.selectList(Wrappers.<Goods>lambdaQuery()
                .eq(Goods::getSortone, sortOneName));
    }

    @Override
    public List<Goods> findGoodsSortTwo(String sortOneName, String sortTwoName) {
        return goodsMapper.selectList(Wrappers.<Goods>lambdaQuery()
                .eq(Goods::getSortone, sortOneName)
                .eq(Goods::getSorttwo, sortTwoName));
    }

    @Override
    public List<Goods> findGoodAdvanced(String name, String sortOneName, String sortTwoName) {
        return goodsMapper.selectList(Wrappers.<Goods>lambdaQuery()
                .eq(Goods::getState, 1)
                .like(name != null, Goods::getName, name)
                .like(sortOneName != null, Goods::getSortone, sortOneName)
                .like(sortTwoName != null, Goods::getSorttwo, sortTwoName));
    }

    @Override
    public List<Goods> findGoodAdvancedByUserId(String userId, String name, String sortOneName, String sortTwoName) {
        return goodsMapper.selectList(Wrappers.<Goods>lambdaQuery()
                .eq(userId != null, Goods::getUserId, userId)
                .like(name != null, Goods::getName, name)
                .like(sortOneName != null, Goods::getSortone, sortOneName)
                .like(sortTwoName != null, Goods::getSorttwo, sortTwoName));    }


}
