package com.tl.consumer.controller;

import com.tl.common.entity.Goods;
import com.tl.common.entity.Information;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/consumer/information")
public class InformationController {
    @Autowired
    private FeginClient feginClient;

    //发布公告
    @RequestMapping("/publishAnnounce")
    @ResponseBody
    public void orderingStatus(HttpServletRequest request, @RequestBody Information information) {
        //token这里则是userid
        String token = request.getHeader("curUserid");
        //调用远程服务
        feginClient.publishAnnounce(token, information);
    }

    //获取所有公告
    @RequestMapping("/getAllAnnounce")
    @ResponseBody
    public String getAllAnnounce() {

        //调用远程服务
        return feginClient.getAllAnnounce();
    }
}
