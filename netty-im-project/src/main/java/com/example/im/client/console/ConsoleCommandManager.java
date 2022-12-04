package com.example.im.client.console;

import com.example.im.util.SessionUtil;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Author yanzx
 * @Date 2022/12/3 14:31
 */
public class ConsoleCommandManager implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        // 获取第一个指令
        System.out.println("输入操作命令:");
        String command = scanner.next();

        if (!SessionUtil.hasLogin(channel)) {
            return;
        }

        ConsoleCommand consoleCommand = ConsoleCommandFactory.get(command);

        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.err.println("无法识别[" + command + "]指令，请重新输入!");
        }
    }
}
