package netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.codec.PacketCodecHandler;
import netty.codec.Spliter;
import netty.idle.IMIdleStateHandler;
import netty.server.handler.AuthHandler;
import netty.server.handler.HeartBeatRequestHandler;
import netty.server.handler.IMHandler;
import netty.server.handler.LoginRequestHandler;

import java.util.Date;

/**
 * Netty 服务端
 *
 * @author : Lin Can
 * @date : 2019/2/27 16:10
 */
public class Server {
    private static final int INIT_PORT = 8000;

    public static void main(String[] args) {
        // 服务器启动引导类
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 两组线程组 分别负责监听接入新连接和对已接入连接的数据处理
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 确定线程模型，IO模型，逻辑处理器和绑定端口
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());

                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);

                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        ch.pipeline().addLast(IMHandler.INSTANCE);

                    }
                });

        bind(serverBootstrap, INIT_PORT);
    }

    /**
     * 如果绑定失败，则自动递增端口
     * @param bootstrap 服务器启动引导类
     * @param port 初始端口
     */
    private static void bind(ServerBootstrap bootstrap, int port) {
        bootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 服务器绑定" + port + "成功");
            } else {
                bind(bootstrap, port + 1);
            }
        });

    }
}
