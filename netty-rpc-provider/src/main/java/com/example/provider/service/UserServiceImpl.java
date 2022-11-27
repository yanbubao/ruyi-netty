package com.example.provider.service;

import com.example.protocol.annotation.RemoteService;
import com.example.rpc.IUserService;

/**
 * @Author yanzx
 * @Date 2022/11/25 23:04
 */
@RemoteService
public class UserServiceImpl implements IUserService {
    @Override
    public String save(String userName) {
        return "save success.";
    }
}
