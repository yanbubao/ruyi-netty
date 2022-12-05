package com.example.im.protocol.request;

import com.example.im.protocol.Packet;
import com.example.im.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author yanzx
 * @Date 2022/12/5 21:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class JoinGroupRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_REQUEST;
    }

    private String groupId;
}
