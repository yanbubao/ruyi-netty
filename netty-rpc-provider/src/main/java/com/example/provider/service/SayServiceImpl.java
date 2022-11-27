package com.example.provider.service;

import com.example.protocol.annotation.RemoteService;
import com.example.rpc.ISayService;

/**
 * @Author yanzx
 * @Date 2022/11/25 23:04
 */
@RemoteService
public class SayServiceImpl implements ISayService {
    @Override
    public String say() {
        return "Hello World.";
    }
}
