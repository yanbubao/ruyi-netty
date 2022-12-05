package com.example.im.protocol.request;

import com.example.im.protocol.Packet;
import com.example.im.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author yanzx
 * @Date 2022/12/5 21:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuitGroupRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_REQUEST;
    }

    private String groupId;
}
