package com.example.im.util;

import com.example.im.attribute.Attributes;
import com.example.im.session.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理用户Session
 *
 * @Author yanzx
 * @Date 2022/12/3 15:36
 */
public class SessionUtil {

    private static final Map<String, Channel> USERID_CHANNEL_MAP = new ConcurrentHashMap<>();

    /**
     * 给Channel绑定用户Session
     *
     * @param session user session
     * @param channel netty channel
     */
    public static void bindSession(Session session, Channel channel) {
        if (USERID_CHANNEL_MAP.containsKey(session.getUserId())) {
            System.out.println("[SessionUtil] user binder session, username: " + session.getUserName());
            return;
        }
        USERID_CHANNEL_MAP.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 移除Channel绑定的用户Session
     *
     * @param channel netty channel
     */
    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = getSession(channel);

            if (!USERID_CHANNEL_MAP.containsKey(session.getUserId())) {
                return;
            }

            USERID_CHANNEL_MAP.remove(session.getUserId());

            channel.attr(Attributes.SESSION).set(null);
            System.out.println("[" + session + "] 退出登录");
        }
    }

    public static boolean hasLogin(Channel channel) {
        return Objects.nonNull(getSession(channel));
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return USERID_CHANNEL_MAP.get(userId);
    }
}
