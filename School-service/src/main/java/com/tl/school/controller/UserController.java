package com.tl.school.controller;


import com.tl.common.entity.User;
import com.tl.school.service.UserService;
import io.micrometer.core.instrument.util.JsonUtils;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tl
 * @since 2021-10-08
 * //第一种调用方式
 * String forObject = new RestTemplate().getForObject("http://localhost:8071/Hello/World?s=" + s, String.class);
 * 第二种调用方式
 * 根据服务名 获取服务列表 根据算法选取某个服务 并访问某个服务的网络位置。
 * ServiceInstance serviceInstance = loadBalancerClient.choose("EUREKA-SERVICE");
 * String forObject = new RestTemplate().getForObject("http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/Hello/World?s="+s,String.class);
 * 第三种调用方式 需要restTemplate注入的方式 String forObject = restTemplate.getForObject("http://EUREKA-SERVICE/Hello/World?s=" + s,String.class); return forObject; }}
 */
@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/validateUser")
    public String validateUser(HttpSession session, @RequestParam("userid") String userid, @RequestParam("password") String password) {
        List<User> userList = userService.findByUserId(userid);
        if (userList.size() != 0) {
            if (userList.get(0).getPassword().equals(password)) {

                return "登陆成功";
            } else {
                return "密码错误！";
            }
        } else {
            return "此账户不存在！";
        }

    }

    @RequestMapping("/getUser")
    public User getuser(HttpServletRequest request, @RequestParam("userid") String userid) {
        String s = request.getHeader("curUserid");
        System.out.println("AAAAA: "+s);
        List<User> userList = userService.findByUserId(userid);
        if (userList.size() != 0) {
            return userList.get(0);
        } else {
            return null;
        }

    }

}

