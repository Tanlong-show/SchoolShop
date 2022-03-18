package com.tl.school.Util;

import com.tl.common.entity.User;
import com.tl.common.entity.Workflow;
import com.tl.common.entityView.AuditShow;
import com.tl.school.service.UserService;
import com.tl.school.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//操作人，操作商品，操作
@Component
public class WorkflowUtil {
    @Autowired
    WorkflowService workflowService;
    @Autowired
    RedisUtil redisUtil;

    public static String Mydate() {
        Date ok = new Date();
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd&hh:mm:ss");
        return format1.format(ok);
    }

    //userId操作人账号(操作人实时变动), goodsId商品id
    // state操作行为  0-上架 1-下架 2-创建待审核 3-创建通过 4-创建被驳回(reject存储驳回信息) 5-修改待审核 6-修改通过 7-修改驳回 8-删除商品
    public void add(Workflow workflow) {

        String name = (String) redisUtil.get(workflow.getUserId());
        //以前的工作流内容交给service层解决
        String workflowContent = "";
        if (workflow.getState() == 0) {
            workflow.setContent(workflowContent + "操作人:" + name + " 操作:上架商品" + " 操作时间:" + WorkflowUtil.Mydate() + ",");
        } else if (workflow.getState() == 1) {
            workflow.setContent(workflowContent + "操作人:" + name + " 操作:下架商品" + " 操作时间:" + WorkflowUtil.Mydate() + ",");
        } else if (workflow.getState() == 2) {
            workflow.setContent(workflowContent + "操作人:" + name + " 操作:创建商品，待审核" + " 操作时间:" + WorkflowUtil.Mydate() + ",");
        } else if (workflow.getState() == 3) {
            workflow.setContent(workflowContent + "操作人:" + name + " 操作:允许创建" + " 操作时间:" + WorkflowUtil.Mydate() + ",");
        } else if (workflow.getState() == 4) {
            workflow.setContent(workflowContent + "操作人:" + name + " 操作:驳回创建请求&&原因：" + workflow.getReject() + " 操作时间:" + WorkflowUtil.Mydate() + ",");
        } else if (workflow.getState() == 5) {
            workflow.setContent(workflowContent + "操作人:" + name + " 操作:修改商品信息，待审核" + " 操作时间:" + WorkflowUtil.Mydate() + ",");
        } else if (workflow.getState() == 6) {
            workflow.setContent(workflowContent + "操作人:" + name + " 操作:允许修改" + " 操作时间：" + WorkflowUtil.Mydate() + ",");
        } else if (workflow.getState() == 7) {
            workflow.setContent(workflowContent + "操作人:" + name + " 操作:驳回修改请求&&原因：" + workflow.getReject() + " 操作时间:" + WorkflowUtil.Mydate() + ",");
        } else if (workflow.getState() == 8) {
            workflow.setContent(workflowContent + "操作人:" + name + " 操作:删除商品" + " 操作时间:" + WorkflowUtil.Mydate() + ",");
        }
        workflowService.updateWorkFlow(workflow);
    }

    //分成list单个消息块给
    public List<AuditShow> getAuditShow(Integer id){
        Workflow workflow = new Workflow();
        //根据商品id查找对应审计内容
        List<Workflow> list2 = workflowService.findByGoodsId(id);
        if(list2.size()!=0){
            workflow = list2.get(0);
        }else{
            return null;
        }
        String auditContent = workflow.getContent();

        //第一步先拆分逗号
        List<String> stringList = Arrays.asList(auditContent.split(","));

        //第二步拆分空格,分别是操作人，操作内容&驳回原因，操作时间
        List<AuditShow>auditShowList = new ArrayList<>();
        for (int i = 0; i < stringList.size(); i++) {
            List<String>list = Arrays.asList(stringList.get(i).split(" "));
            AuditShow auditShow = new AuditShow();
            auditShow.setPeople(list.get(0));
            auditShow.setContent(list.get(1));
            auditShow.setTime(list.get(2));
            System.out.println(auditShow.toString());
            auditShowList.add(auditShow);
        }

        return auditShowList;

    }

}
