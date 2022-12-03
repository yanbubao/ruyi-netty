package com.example.im.server.handler;

import com.example.im.protocol.request.LoginRequestPacket;
import com.example.im.protocol.response.LoginResponsePacket;
import com.example.im.util.IDUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author yanzx
 * @Date 2022/12/3 15:21
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    private final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    private final Map<String, String> USER_INFO_MAP;

    protected LoginRequestHandler() {
        USER_INFO_MAP = new HashMap<>();
        USER_INFO_MAP.put("姆巴佩", "123");
        USER_INFO_MAP.put("内马尔", "123");
        USER_INFO_MAP.put("梅西", "123");
        USER_INFO_MAP.put("维尼修斯", "123");
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUserName());

        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = IDUtil.randomId();
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + loginRequestPacket.getUserName() + "] 登录成功");
            // todo session bind
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println("[" + loginRequestPacket.getUserName() + "] 登录失败");
        }

        // 响应登录
        ctx.writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        String userName = loginRequestPacket.getUserName();
        String password = loginRequestPacket.getPassword();
        return USER_INFO_MAP.containsKey(userName) && USER_INFO_MAP.get(userName).equals(password);
    }
}
