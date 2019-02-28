package netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.protocol.PacketCodec;
import netty.protocol.pocket.impl.MessageRequestPacket;
import netty.util.LoginUtil;

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
                        ch.pipeline().addLast(new ClientHandler());
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

                startConsoleThread(((ChannelFuture)future).channel());
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
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!LoginUtil.hasLogin(channel)) {
                    continue;
                }
                System.out.println("输入需要发送到服务端的消息：");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();

                MessageRequestPacket requestPacket = new MessageRequestPacket();
                requestPacket.setMessage(line);
                ByteBuf requestByteBuf = PacketCodec.INSTANCE.encode(channel.alloc(), requestPacket);

                channel.writeAndFlush(requestByteBuf);
            }
        }).start();
    }

}
