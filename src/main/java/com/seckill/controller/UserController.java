package com.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("user")
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/get")
    public void getUser(@RequestParam(name="id") Integer id){

    }

}
