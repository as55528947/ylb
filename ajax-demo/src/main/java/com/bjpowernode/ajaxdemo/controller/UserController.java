package com.bjpowernode.ajaxdemo.controller;

import com.bjpowernode.ajaxdemo.model.User;
import org.springframework.web.bind.annotation.*;

/**
 * 动力节点乌兹
 * 2022-4-21
 */
@CrossOrigin
@RestController
public class UserController {
    int b = 1;
    /*get请求*/
    @GetMapping("/user/query")
    public User queryUser(){
        b+=1;
        System.out.println("===/user/query 接收前端的请求===" + b);
        User user = new User(1001, "马丁", 24, "男");

        return user;
    }
    /*get请求*/
    @GetMapping("/user/get")
    public User queryUser(Integer id,String name){

        System.out.println("===/user/query 接收前端的请求===id=" + id + ",name=" + name);
        User user = new User(1002, "马劫", 24, "男");

        return user;
    }

    /*post请求*/
    @PostMapping("/user/add")
    public User addUser(Integer id,String name){

        System.out.println("===/user/query 接收前端的请求===id=" + id + ",name=" + name);
        User user = new User(1003, "马哈", 24, "男");

        return user;
    }

    /*post请求   前端是json格式   后端用java对象接收参数
    * @RequestBody:请求体中获取数据，转为形参的对象
    */
    @PostMapping("/user/json")
    public User addUserJson(@RequestBody User user){

        System.out.println("===/user/query 接收前端的请求===user-->" + user);
        User user2 = new User(1003, "马哈", 24, "男");

        return user;
    }
}
