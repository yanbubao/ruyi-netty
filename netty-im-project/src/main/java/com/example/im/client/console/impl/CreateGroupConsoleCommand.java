package com.example.im.client.console.impl;

import com.example.im.client.console.ConsoleCommand;
import com.example.im.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author yanzx
 * @Date 2022/12/5 20:42
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SEPARATOR = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("【拉人群聊】输入userId列表，userId之间英文逗号隔开: ");
        String userIds = scanner.next();

        String[] split = userIds.split(USER_ID_SEPARATOR);
        if (split.length < 2) {
            System.err.print("【拉人群聊】至少输入两个用户ID，之间英文逗号隔开！");
        }

        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        createGroupRequestPacket.setUserIdList(Arrays.asList(split));
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
