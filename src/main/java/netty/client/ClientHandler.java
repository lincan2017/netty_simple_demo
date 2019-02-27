package netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.protocol.PacketCodec;
import netty.protocol.pocket.Packet;
import netty.protocol.pocket.impl.LoginRequestPacket;
import netty.protocol.pocket.impl.LoginResponsePacket;

import java.util.Date;
import java.util.UUID;

/**
 * @author : Lin Can
 * @date : 2019/2/27 16:32
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端成功连接上服务端时，Netty 会回调该方法
     * @param ctx 上下文对象
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 客户端开始登录");
        // 构建请求对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(12346789L);
        loginRequestPacket.setUsername("flash_fans");
        loginRequestPacket.setPassword("pwd");

        // 编码
        ByteBuf request = PacketCodec.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        // 写到服务端
        ctx.channel().writeAndFlush(request);
    }

    /**
     * 客户端读取到来自服务端的消息后，Netty 会回调该方法
     * @param ctx 连接相关的上下文对象
     * @param msg 接收到的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf response = (ByteBuf) msg;
        // 解码
        Packet packet = PacketCodec.INSTANCE.decode(response);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket responsePacket = (LoginResponsePacket) packet;
            if (responsePacket.getSuccess()) {
                System.out.println(new Date() + ": 客户端登录成功");
            } else {
                System.out.println(new Date() + ": 客户端登录失败，原因：" + responsePacket.getReson());
            }
        }

    }
}
