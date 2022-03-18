package com.tl.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tl.common.entity.Flashsale;
import com.tl.common.entityView.FlashGoods;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tl
 * @since 2021-12-29
 */
public interface FlashsaleService extends IService<Flashsale> {
    //插入秒杀商品信息
    public void insertFlashSale(Flashsale flashsale);
    //获取所有秒杀商品信息
    public List<FlashGoods> getAllFlashSale();
}
