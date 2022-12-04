package com.example.im.protocol.response;

import com.example.im.protocol.Packet;
import com.example.im.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author yanzx
 * @Date 2022/12/4 17:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LogoutResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }

    private boolean success;

    private String reason;
}
