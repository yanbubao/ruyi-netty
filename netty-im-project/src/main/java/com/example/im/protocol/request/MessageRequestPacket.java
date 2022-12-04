package com.example.im.protocol.request;

import com.example.im.protocol.Packet;
import com.example.im.protocol.command.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author yanzx
 * @Date 2022/12/4 16:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MessageRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }

    /**
     * 消息接收方用户id
     */
    private String toUserId;

    private String message;
}
