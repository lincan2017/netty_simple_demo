package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
            }
        });

        //绑定端口
        serverBootstrap.bind(8000);
    }
}
