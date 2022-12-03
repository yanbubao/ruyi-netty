package com.example.im.protocol.response;

import com.example.im.protocol.Packet;
import com.example.im.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author yanzx
 * @Date 2022/12/3 15:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }

    private boolean success;
    private String reason;
    private String userId;
    private String userName;
}
