package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author : Lin Can
 * @date : 2019/2/25 10:25
 */
public class Server {
    public static void main(String[] args) {
        // 服务器启动引导类
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 线程组 bossGroup 负责接入新连接，workerGroup 负责监听连接是否有数据
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 确定线程模型
        serverBootstrap.group(bossGroup, workerGroup);

        // 确定线程模型
        serverBootstrap.channel(NioServerSocketChannel.class);

        // 添加逻辑处理器
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) {
                ch.pipeline().addLast(new FirstServerHandlerAdapter());
            }
        });

        // 绑定端口
        serverBootstrap.bind(8000);

    }

    /**
     * 自定义消息处理逻辑
     *
     * @author : Lin Can
     */
    static class FirstServerHandlerAdapter extends ChannelInboundHandlerAdapter {

        /**
         * 复写连接成功方法，向连接推送消息
         *
         * @param ctx 上下文对象
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            System.out.println(new Date() + " 服务器向客户端推送消息");

            // 封装待发送的消息
            ByteBuf sendBuffer = getByteBuf(ctx, "欢迎你加入我们的大家庭，很高兴遇见你");

            // 写出到客户端
            ctx.writeAndFlush(sendBuffer);
        }

        /**
         * 复写读取消息后的调用的方法，打印收到的消息
         *
         * @param ctx 连接相关的上下文对象
         * @param msg 收到的数据
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            // 对获取到的msg 进行类型转换为 ByteBuf对象
            ByteBuf byteBuf = (ByteBuf) msg;

            System.out.println(new Date() + " 收到来自客户端的消息：" +
                    byteBuf.toString(StandardCharsets.UTF_8));
        }


        /**
         * 封装向客户端发送的消息到数据载体中
         *
         * @param ctx 上下文对象
         * @return carrier
         */
        private ByteBuf getByteBuf(ChannelHandlerContext ctx, String msg) {
            // 数据准备
            byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);

            // 获取数据载体
            ByteBuf byteBuf = ctx.alloc().buffer();

            byteBuf.writeBytes(bytes);

            return byteBuf;
        }


    }
}
