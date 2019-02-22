package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author : Lin Can
 * @date : 2019/2/22 9:43
 */
public class Server {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        //确定线程模型
        serverBootstrap.group(bossGroup, workerGroup);
        //确定IO模型
        serverBootstrap.channel(NioServerSocketChannel.class);
        //定义连接的类型
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) {
                        System.out.println(msg);
                    }
                });
            }
        });

        //绑定端口
        bind(serverBootstrap,135);
    }

    /**
     * 递归绑定端口，端口递增
     * @param bootstrap 服务器启动引导类
     * @param port 初始端口
     */
    private static void bind(ServerBootstrap bootstrap, int port) {
        ChannelFuture channelFuture = bootstrap.bind(port);
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(port + " port bind success!!");
                return;
            }
            // 如果绑定失败，则端口递增
            bind(bootstrap, port + 1);
        });
    }
}
