package com.tl.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tl.common.entity.Goods;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tl
 * @since 2021-10-15
 */
public interface GoodsService extends IService<Goods> {

    List<Goods> findGoodsList();

    Goods findGoodById(Integer id);

}
