package com.example.im.protocol.response;

import com.example.im.protocol.Packet;
import com.example.im.protocol.command.Command;
import com.example.im.session.Session;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author yanzx
 * @Date 2022/12/5 21:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ListGroupMembersResponsePacket extends Packet {

    private boolean success;

    private String groupId;

    private List<Session> sessionList;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
