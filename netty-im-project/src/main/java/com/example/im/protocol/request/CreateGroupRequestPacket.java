package com.example.im.protocol.request;

import com.example.im.protocol.Packet;
import com.example.im.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author yanzx
 * @Date 2022/12/5 20:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateGroupRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }

    private List<String> userIdList;
}
