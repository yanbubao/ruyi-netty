package com.example.im.protocol.request;

import com.example.im.protocol.Packet;
import com.example.im.protocol.command.Command;

/**
 * @Author yanzx
 * @Date 2022/12/4 15:15
 */
public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}
