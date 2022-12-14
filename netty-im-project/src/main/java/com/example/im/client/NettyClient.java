package com.example.im.client;

import com.example.im.client.console.ConsoleCommandManager;
import com.example.im.client.console.impl.LoginConsoleCommand;
import com.example.im.client.handler.HeartBeatTimerHandler;
import com.example.im.client.handler.IMClientHandler;
import com.example.im.client.handler.LoginResponseHandler;
import com.example.im.client.handler.LogoutResponseHandler;
import com.example.im.codec.IMFrameDecoder;
import com.example.im.codec.PacketCodecHandler;
import com.example.im.handler.IMIdleStateHandler;
import com.example.im.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @Author yanzx
 * @Date 2022/12/2 22:53
 */
public class NettyClient {

    public static void main(String[] args) {
        startClient();
    }

    /**
     * 客户端连接失败最大重试次数
     */
    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;

    private static void startClient() {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast(new IMIdleStateHandler())
                                .addLast(new IMFrameDecoder())
                                .addLast(PacketCodecHandler.INSTANCE)
                                .addLast(LoginResponseHandler.INSTANCE)
                                .addLast(LogoutResponseHandler.INSTANCE)
                                // 业务处理器
                                .addLast(IMClientHandler.INSTANCE)
                                .addLast(new HeartBeatTimerHandler());
                    }
                });

        connect(bootstrap, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, int retry) {
        bootstrap.connect(HOST, PORT).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("[NettyClient] 客户端连接成功 -> 准备启动控制台线程");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("[NettyClient] 重试次数已用完，放弃连接！");
            } else {
                // order表示第几次连接
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连间隔
                int delay = 1 << order; // aka 1 2 4 8
                System.err.println("[NettyClient] 连接失败，第" + order + "次重连...");
                bootstrap.config()
                        .group()
                        .schedule(() -> connect(bootstrap, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {

        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();

        // 控制台输入
        Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (SessionUtil.hasLogin(channel)) {
                    consoleCommandManager.exec(scanner, channel);
                } else {
                    loginConsoleCommand.exec(scanner, channel);
                }
            }
        }).start();
    }
}
