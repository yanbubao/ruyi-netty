package com.example.im.client.console;

import java.nio.channels.Channel;
import java.util.Scanner;

/**
 * @Author yanzx
 * @Date 2022/12/3 14:31
 */
public class ConsoleCommandManager implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        // 获取第一个指令
        String command = scanner.next();

        //todo check login
        ConsoleCommand consoleCommand = ConsoleCommandFactory.get(command);

        // todo
    }
}
