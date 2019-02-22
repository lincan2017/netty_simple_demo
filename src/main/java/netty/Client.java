package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author : Lin Can
 * @date : 2019/2/22 14:44
 */
public class Client {
    public static void main(String[] args) {
        // 引导类
        Bootstrap bootstrap = new Bootstrap();
        // 线程模型
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group);

        // IO 模型
        bootstrap.channel(NioSocketChannel.class);

        // 处理连接的逻辑
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {

            }
        });

        // 连接到指定的端口
        ChannelFuture channelFuture = bootstrap.connect("juejin.im", 80);

        // 监听连接，打印连接结果
        channelFuture.addListener(future ->  {
            if (future.isSuccess()) {
                System.out.println(" 连接成功.......");
                return;
            }
            System.out.println(" 连接失败........");
        });
    }
}
