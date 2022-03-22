package com.tl.school.controller;


import com.tl.common.entity.Orders;
import com.tl.common.entity.User;
import com.tl.common.entityView.UserMessage;
import com.tl.school.Util.RedisUtil;
import com.tl.school.service.OrderService;
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
    @Autowired
    private OrderService orderService;
    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("/validateUser")
    public String validateUser(@RequestParam("userid") String userid, @RequestParam("password") String password) {
        //这里通过登陆账号验证
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

    @RequestMapping("/registerUser")
    public String registerUser(@RequestParam("username") String username, @RequestParam("userid") String userid, @RequestParam("password") String password) {
        //这里通过注册账号验证，看是否已经存在该用户
        List<User> userList = userService.findByUserId(userid);
        if (userList.size() != 0) {
            return "此账户已存在！";
        } else {
            User user = new User();
            user.setName(username);
            user.setUserId(userid);
            user.setPassword(password);
            user.setHeadpicture("https://ts1.cn.mm.bing.net/th?id=OIP-C.ZeQ5h5qmFJdYmGKtrR-I9gAAAA&w=204&h=204&c=8&rs=1&qlt=90&o=6&dpr=1.25&pid=3.1&rm=2");
            userService.save(user);
            return "注册成功";
        }

    }

    @RequestMapping("/getUser")
    public User getuser(HttpServletRequest request, @RequestParam("userid") String userid) {
        //这里通过登陆账号验证
        List<User> userList = userService.findByUserId(userid);
        if (userList.size() != 0) {
            redisUtil.set(userid, userList.get(0).getName());
            return userList.get(0);
        } else {
            return null;
        }

    }

    @RequestMapping("/getAllUser")
    public List<User> getAllUser() {
        return userService.findAllUser();

    }

    //获取所有与用户相关订单信息
    @RequestMapping("/getAllUserMessage")
    public List<UserMessage> getAllUserMessage(@RequestParam("userid") String userId) {
        return userService.findAllUserMessage(userId);
    }

    //获取所有与用户相关订单信息
    @RequestMapping("/getMyOrder")
    public List<Orders> getMyOrder(@RequestParam("userid") String userId) {
        return orderService.selectOrderByUserId(Integer.parseInt(userId));
    }
}

