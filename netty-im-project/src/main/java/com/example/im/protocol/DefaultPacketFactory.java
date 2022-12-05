package com.example.im.protocol;

import com.example.im.protocol.request.*;
import com.example.im.protocol.response.*;
import java.util.HashMap;
import java.util.Map;

import static com.example.im.protocol.command.Command.*;

/**
 * @Author yanzx
 * @Date 2022/12/2 22:46
 */
public class DefaultPacketFactory {
    private static final Map<Byte, Class<? extends Packet>> SUPPORT_PACKET_MAP = new HashMap<>();

    static {
        SUPPORT_PACKET_MAP.put(LOGIN_REQUEST, LoginRequestPacket.class);
        SUPPORT_PACKET_MAP.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        SUPPORT_PACKET_MAP.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        SUPPORT_PACKET_MAP.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
        SUPPORT_PACKET_MAP.put(LOGOUT_REQUEST, LogoutRequestPacket.class);
        SUPPORT_PACKET_MAP.put(LOGOUT_RESPONSE, LogoutResponsePacket.class);
        SUPPORT_PACKET_MAP.put(CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        SUPPORT_PACKET_MAP.put(CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
//        SUPPORT_PACKET_MAP.put(JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
//        SUPPORT_PACKET_MAP.put(JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
//        SUPPORT_PACKET_MAP.put(QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
//        SUPPORT_PACKET_MAP.put(QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
//        SUPPORT_PACKET_MAP.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
//        SUPPORT_PACKET_MAP.put(LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
//        SUPPORT_PACKET_MAP.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
//        SUPPORT_PACKET_MAP.put(GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);
        SUPPORT_PACKET_MAP.put(HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
        SUPPORT_PACKET_MAP.put(HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);
    }

    public static Class<? extends Packet> getPacketClazz(Byte command) {
        if (SUPPORT_PACKET_MAP.containsKey(command)) {
            Class<? extends Packet> clazz = SUPPORT_PACKET_MAP.get(command);
            if (clazz != null) {
                return clazz;
            }
            throw new IllegalArgumentException("[Protocol] obtain packet failed, command code: " + command);
        }
        throw new IllegalArgumentException("[Protocol] not support command, command code: " + command);
    }
}
