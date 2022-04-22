package com.tl.school.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tl.common.entity.Flashsale;
import com.tl.common.entity.Goods;
import com.tl.common.entityView.FlashGoods;
import com.tl.school.Util.RedisUtil;
import com.tl.school.mapper.FlashsaleMapper;
import com.tl.school.mapper.GoodsMapper;
import com.tl.school.service.FlashsaleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tl
 * @since 2021-12-29
 */
@Service
public class FlashsaleServiceImpl extends ServiceImpl<FlashsaleMapper, Flashsale> implements FlashsaleService {

    @Autowired
    FlashsaleMapper flashsaleMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public void insertFlashSale(Flashsale flashsale) {
        flashsaleMapper.insert(flashsale);
    }

    @Override
    public List<FlashGoods> getAllFlashSale() {
        List<Flashsale> flashsales = flashsaleMapper.selectList(Wrappers.<Flashsale>lambdaQuery().isNotNull(Flashsale::getGoodsId));
        List<FlashGoods> flashGoods = new LinkedList<>();
        for (int i = 0; i < flashsales.size(); i++) {
            Goods goods = new Goods();
            goods = goodsMapper.selectById(flashsales.get(i).getGoodsId());
            FlashGoods flashGoods1 = new FlashGoods();
            flashGoods1.setId(flashsales.get(i).getId());
            flashGoods1.setContent(flashsales.get(i).getContent());
            //从redis中获取数量，如果有则为redis中，反之则为本身
            String pp = "flashsale" + flashsales.get(i).getId();
            Integer kk;

            if(redisUtil.get(pp) != null){
                kk = (Integer) redisUtil.get(pp);
            }else{
                kk = null;
            }

            if(kk != null){
                goods.setNumber(kk);
            }
            flashGoods1.setGoods(goods);
            flashGoods1.setStartTime(flashsales.get(i).getStartTime());
            flashGoods.add(flashGoods1);
        }
        return flashGoods;
    }
}
