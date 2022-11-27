package com.example.consumer.rest;

import com.example.protocol.annotation.RemoteReference;
import com.example.rpc.ISayService;
import com.example.rpc.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yanzx
 * @Date 2022/11/25 22:28
 */
@RestController
public class HelloController {

    @RemoteReference
    private IUserService userService;

    @RemoteReference
    private ISayService sayService;

    @GetMapping("/save")
    public String save() {
        return userService.save("Yanzx");
    }

    @GetMapping("/hello")
    public String hello() {
        return sayService.say();
    }
}
