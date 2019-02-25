package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author : Lin Can
 * @date : 2019/2/25 10:43
 */
public class Client {
    public static void main(String[] args) {
        // 客户端启动引导类
        Bootstrap bootstrap = new Bootstrap();

        NioEventLoopGroup group = new NioEventLoopGroup();

        // 确定线程模型
        bootstrap.group(group);

        // 确定IO模型
        bootstrap.channel(NioSocketChannel.class);

        // 读写数据的逻辑处理
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) {
                // 添加客户端的逻辑处理器
                ch.pipeline().addLast(new FirstClientHandlerAdapter());
            }
        });

        // 指定连接地址
        bootstrap.connect("localhost", 8000);
    }

    static class FirstClientHandlerAdapter extends ChannelInboundHandlerAdapter {

        /**
         * 复写连接成功后调用的方法，向服务器发送数据
         *
         * @param ctx 上下文对象
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            System.out.println(new Date() + " 客户端向服务器发送数据：");

            // 封装待传输的数据
            ByteBuf byteBuf = getByteBuf(ctx);

            // 写出到服务器
            ctx.writeAndFlush(byteBuf);
        }

        /**
         * 准备发送数据对象，并封装到数据载体中
         *
         * @param ctx 上下文对象
         * @return ByteBuf carrier
         */
        private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
            // 获取数据载体
            ByteBuf buffer = ctx.alloc().buffer();

            // 数据准备
            byte[] bytes = "你好，闪电侠！很荣幸跟你对上话。".getBytes(StandardCharsets.UTF_8);

            // 填充数据到 ByteBuf 对象中
            buffer.writeBytes(bytes);

            return buffer;
        }

        /**
         * 复写读取到消息后的方法，打印服务器发送的消息
         *
         * @param ctx 上下文对象
         * @param msg message
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf byteBuf = (ByteBuf) msg;

            System.out.println(new Date() + " 收到来自服务端的消息：" +
                    byteBuf.toString(StandardCharsets.UTF_8));
        }
    }
}
