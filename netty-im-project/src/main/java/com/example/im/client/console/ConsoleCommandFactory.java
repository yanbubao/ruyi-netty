package com.example.im.client.console;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author yanzx
 * @Date 2022/12/3 14:34
 */
public class ConsoleCommandFactory {
    private final static ConcurrentHashMap<String, ConsoleCommand> SUPPORT_CONSOLE_COMMAND_MAP = new ConcurrentHashMap<>();

    static {
//        SUPPORT_CONSOLE_COMMAND_MAP.put(ConsoleCommand.SEND_TO_USER, new SendToUserConsoleCommand());
//        SUPPORT_CONSOLE_COMMAND_MAP.put(ConsoleCommand.LOGOUT, new LogoutConsoleCommand());
//        SUPPORT_CONSOLE_COMMAND_MAP.put(ConsoleCommand.CREATE_GROUP, new CreateGroupConsoleCommand());
//        SUPPORT_CONSOLE_COMMAND_MAP.put(ConsoleCommand.JOIN_GROUP, new JoinGroupConsoleCommand());
//        SUPPORT_CONSOLE_COMMAND_MAP.put(ConsoleCommand.QUIT_GROUP, new QuitGroupConsoleCommand());
//        SUPPORT_CONSOLE_COMMAND_MAP.put(ConsoleCommand.LIST_GROUP_MEMBERS, new ListGroupMembersConsoleCommand());
//        SUPPORT_CONSOLE_COMMAND_MAP.put(ConsoleCommand.SEND_TO_GROUP, new SendToGroupConsoleCommand());
    }

    public static ConsoleCommand get(String consoleCommand) {
        if (SUPPORT_CONSOLE_COMMAND_MAP.containsKey(consoleCommand)) {
            return SUPPORT_CONSOLE_COMMAND_MAP.get(consoleCommand);
        }
        throw new IllegalArgumentException("[Client] not support console command: " + consoleCommand);
    }
}
