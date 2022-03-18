package com.tl.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tl.common.entity.Goods;
import com.tl.common.entity.Workflow;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tl
 * @since 2021-11-08
 */
public interface WorkflowService extends IService<Workflow> {

    public void updateWorkFlow(Workflow workflow);

    public List<Workflow> findByGoodsId(Integer id);

}
