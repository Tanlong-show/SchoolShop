package com.tl.school.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tl.common.entity.Goods;
import com.tl.common.entity.Workflow;
import com.tl.school.mapper.WorkflowMapper;
import com.tl.school.service.WorkflowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tl
 * @since 2021-11-08
 */
@Service
public class WorkflowServiceImpl extends ServiceImpl<WorkflowMapper, Workflow> implements WorkflowService {

    @Autowired
    WorkflowMapper workflowMapper;

    @Override
    public void updateWorkFlow(Workflow workflow) {
        Map map = new HashMap<>();
        map.put("goods_id", workflow.getGoodsId());
        List<Workflow> workflows = workflowMapper.selectByMap(map);
        if (workflows.size() != 0) {
            workflow.setId(workflows.get(0).getId());
            workflow.setContent(workflow.getContent() + workflows.get(0).getContent());
            workflowMapper.updateById(workflow);
        } else {
            workflowMapper.insert(workflow);
        }

    }

    @Override
    public List<Workflow> findByGoodsId(Integer id) {
        return workflowMapper.selectList(Wrappers.<Workflow>lambdaQuery().eq(Workflow::getGoodsId, id));

    }
}
