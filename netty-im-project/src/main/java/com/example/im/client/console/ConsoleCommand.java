package com.example.im.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Author yanzx
 * @Date 2022/12/3 14:31
 */
public interface ConsoleCommand {

    /**
     * 执行控制台命令
     *
     * @param scanner console input
     * @param channel nio channel
     */
    void exec(Scanner scanner, Channel channel);

    String SEND_TO_USER = "sendToUser";
    String LOGOUT = "logout";
    String CREATE_GROUP = "createGroup";
    String JOIN_GROUP = "joinGroup";
    String QUIT_GROUP = "quitGroup";
    String LIST_GROUP_MEMBERS = "listGroupMembers";
    String SEND_TO_GROUP = "sendToGroup";
}
