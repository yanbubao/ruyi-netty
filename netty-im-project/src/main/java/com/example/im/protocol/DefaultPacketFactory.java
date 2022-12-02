package com.example.im.protocol;

import com.example.im.protocol.request.LoginRequestPacket;

import java.util.HashMap;
import java.util.Map;

import static com.example.im.protocol.command.Command.LOGIN_REQUEST;

/**
 * @Author yanzx
 * @Date 2022/12/2 22:46
 */
public class DefaultPacketFactory {
    private static final Map<Byte, Class<? extends Packet>> SUPPORT_PACKET_MAP = new HashMap<>();

    static {
        SUPPORT_PACKET_MAP.put(LOGIN_REQUEST, LoginRequestPacket.class);
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
