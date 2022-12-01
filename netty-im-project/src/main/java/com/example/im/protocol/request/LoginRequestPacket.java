package com.example.im.protocol.request;

import com.example.im.protocol.Packet;
import com.example.im.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author yanzx
 * @Date 2022/12/1 22:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

    private Integer userId;
    private String username;
    private String password;
}
