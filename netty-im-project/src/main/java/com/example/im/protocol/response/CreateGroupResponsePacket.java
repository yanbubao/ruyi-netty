package com.example.im.protocol.response;

import com.example.im.protocol.Packet;
import com.example.im.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author yanzx
 * @Date 2022/12/5 20:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateGroupResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }

    private boolean success;
    private List<String> userNameList;
    private String groupId;
}
