package com.example.im.protocol.response;

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
public class MessageResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }

    /**
     * 消息来源方用户id
     */
    private String fromUserId;

    private String fromUserName;

    private String message;

}
