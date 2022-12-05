package com.example.im.protocol.response;

import com.example.im.protocol.Packet;
import com.example.im.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author yanzx
 * @Date 2022/12/5 21:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuitGroupResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_RESPONSE;
    }

    private String groupId;

    private boolean success;

    private String reason;
}
