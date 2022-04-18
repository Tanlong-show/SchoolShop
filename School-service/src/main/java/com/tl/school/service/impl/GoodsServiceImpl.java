package com.tl.school.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tl.common.entity.Goods;
import com.tl.common.entity.Workflow;
import com.tl.school.Util.WorkflowUtil;
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
    @Autowired
    WorkflowUtil workflowUtil;

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

    @Override
    public List<Goods> findGoodAdvancedByState(String name, String sortOneName, String sortTwoName) {
        return goodsMapper.selectList(Wrappers.<Goods>lambdaQuery()
                .ge(Goods::getState, 2)
                .le(Goods::getState, 3)
                .like(name != null, Goods::getName, name)
                .like(sortOneName != null, Goods::getSortone, sortOneName)
                .like(sortTwoName != null, Goods::getSorttwo, sortTwoName));
    }

    @Override
    public void lowerShelfById(Integer id) {
        Goods goods = goodsMapper.selectById(id);
        goods.setState(goods.getState() == 0 ? 1 : 0);
        goodsMapper.updateById(goods);
    }

    @Override
    public Goods getGoodsById(Integer id) {
        return goodsMapper.selectById(id);
    }

    //修改、创建时候用的
    @Override
    public void updateGoods(String token, Goods goods) {
        if(goods.getId() != null){
            goodsMapper.updateById(goods);
        }else{
            goods.setPictureUrl("https://tse3-mm.cn.bing.net/th/id/OIP-C.wqA1x9I9fFEiBvOHA-RQtQHaHa?w=205&h=205&c=7&r=0&o=5&dpr=1.25&pid=1.7");
            goodsMapper.insert(goods);
        }
        Workflow workflow = new Workflow();
        if(goods.getState() == 2){
            //创建待审核
            workflow.setState(2);
        }else if(goods.getState() == 3){
            //修改待审核
            workflow.setState(5);
        }
        workflow.setUserId(token);
        workflow.setGoodsId(goods.getId());
        workflowUtil.add(workflow);

    }

    //直接改状态时使用
    @Override
    public void updateGoodsOk(Goods goods){
        goodsMapper.updateById(goods);
    }


}
