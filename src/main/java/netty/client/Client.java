package netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.client.handler.CreateGroupResponseHandler;
import netty.client.handler.LoginResponseHandler;
import netty.client.handler.LogoutResponseHandler;
import netty.client.handler.MessageResponseHandler;
import netty.codec.PacketDecoder;
import netty.codec.PacketEncoder;
import netty.codec.Spliter;
import netty.console.ConsoleCommand;
import netty.console.ConsoleCommandManager;
import netty.console.impl.LoginCommand;
import netty.util.SessionUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Netty 客户端
 *
 * @author : Lin Can
 * @date : 2019/2/27 16:28
 */
public class Client {
    private static final int MAX_RETRY = 5;
    private static final String HOST = "localhost";
    private static final int PORT = 8000;

    public static void main(String[] args) {
        // 客户端启动引导类
        Bootstrap bootstrap = new Bootstrap();

        // 线程组
        NioEventLoopGroup group = new NioEventLoopGroup();

        // 确定线程模型，IO模型，逻辑处理器和连接地址
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new LogoutResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    /**
     * 连接失败则间隔2的幂指数秒重连
     *
     * @param bootstrap 客户端启动引导类
     * @param host      服务端host
     * @param port      服务端端口
     * @param retry     重连次数
     */
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 客户端连接成功");

                startConsoleThread(((ChannelFuture) future).channel());
                return;
            }
            if (retry <= 0) {
                System.out.println(new Date() + ": 客户端重连次数已用完，放弃连接");
                return;
            }

            // 第几次重连
            int order = (MAX_RETRY - retry) + 1;
            // 本次重连间隔
            int delay = 1 << order;

            System.out.println(new Date() + ": 连接失败，第" + order + "次尝试重连...");
            bootstrap.config().group().schedule(
                    () -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
        });
    }

    /**
     * 启动控制台线程，用于客户端向服务端发送消息
     *
     * @param channel 链接
     */
    private static void startConsoleThread(Channel channel) {
        Scanner sc = new Scanner(System.in);
        new Thread(() -> {
            while (!Thread.interrupted()) {
                // 客户端侧不作登录校验
                if (SessionUtil.hasLogin(channel)) {
                    ConsoleCommand consoleCommand = new ConsoleCommandManager();
                    consoleCommand.exec(sc, channel);
                } else {
                    LoginCommand loginCommand = new LoginCommand();
                    loginCommand.exec(sc, channel);
                }

            }
        }).start();
    }

}
