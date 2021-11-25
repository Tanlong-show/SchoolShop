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

    List<Goods>findGoodsSortOne(String sortOneName);

    List<Goods>findGoodsSortTwo(String sortOneName, String sortTwoName);

    List<Goods>findGoodAdvanced(String name, String sortOneName, String sortTwoName);

    List<Goods>findGoodAdvancedByUserId(String userId, String name, String sortOneName, String sortTwoName);

    List<Goods>findGoodAdvancedByState(String name, String sortOneName, String sortTwoName);

    void lowerShelfById(Integer id);

    Goods getGoodsById(Integer id);

    //需要进入工作流的更新，创建
    void updateGoods(String token, Goods goods);

    //无需进入工作流
    public void updateGoodsOk(Goods goods);

}
