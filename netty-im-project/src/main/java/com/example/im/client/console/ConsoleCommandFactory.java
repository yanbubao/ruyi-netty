package com.example.im.client.console;

import com.example.im.client.console.impl.CreateGroupConsoleCommand;
import com.example.im.client.console.impl.LogoutConsoleCommand;
import com.example.im.client.console.impl.SendToUserConsoleCommand;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author yanzx
 * @Date 2022/12/3 14:34
 */
public class ConsoleCommandFactory {
    private final static ConcurrentHashMap<String, ConsoleCommand> SUPPORT_CONSOLE_COMMAND_MAP = new ConcurrentHashMap<>();

    static {
        SUPPORT_CONSOLE_COMMAND_MAP.put(ConsoleCommand.SEND_TO_USER, new SendToUserConsoleCommand());
        SUPPORT_CONSOLE_COMMAND_MAP.put(ConsoleCommand.CREATE_GROUP, new CreateGroupConsoleCommand());
//        SUPPORT_CONSOLE_COMMAND_MAP.put(ConsoleCommand.JOIN_GROUP, new JoinGroupConsoleCommand());
//        SUPPORT_CONSOLE_COMMAND_MAP.put(ConsoleCommand.QUIT_GROUP, new QuitGroupConsoleCommand());
//        SUPPORT_CONSOLE_COMMAND_MAP.put(ConsoleCommand.LIST_GROUP_MEMBERS, new ListGroupMembersConsoleCommand());
//        SUPPORT_CONSOLE_COMMAND_MAP.put(ConsoleCommand.SEND_TO_GROUP, new SendToGroupConsoleCommand());

        SUPPORT_CONSOLE_COMMAND_MAP.put(ConsoleCommand.LOGOUT, new LogoutConsoleCommand());
    }

    public static ConsoleCommand get(String consoleCommand) {
        return SUPPORT_CONSOLE_COMMAND_MAP.get(consoleCommand);
    }
}
