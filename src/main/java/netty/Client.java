package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * @author : Lin Can
 * @date : 2019/2/22 14:44
 */
public class Client {
    private static int MAX_RETRY = 5;

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
        connect(bootstrap, "baidu.im", 80, MAX_RETRY);
    }

    /**
     * 重连
     * 延迟2的n次幂后重试
     * 重试超过5次返回
     * @param bootstrap 引导类
     * @param host 主机地址
     * @param port 端口
     * @param retry 重试次数
     */
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host,port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(host + ": " + port + " 连接成功");
                return;
            }
            if (retry <= 0) {
                throw new RuntimeException(host + ": " + port + " 重连次数超过" + MAX_RETRY + "次");
            }
            // 第几次重连
            int order = (MAX_RETRY - retry) + 1;
            long delay = 1 << order;
            System.out.println(host + ": " + port + " 失败重连" + order + "次，延迟" + delay + "s" );
            bootstrap.config().group().schedule(
                    () -> connect(bootstrap,host,port,retry - 1), delay, TimeUnit.SECONDS);
        });

    }
}
