package netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.protocol.PacketCodec;
import netty.protocol.pocket.Packet;
import netty.protocol.pocket.impl.LoginRequestPacket;
import netty.protocol.pocket.impl.LoginResponsePacket;
import netty.protocol.pocket.impl.MessageRequestPacket;
import netty.protocol.pocket.impl.MessageResponsePacket;

import java.util.Date;

/**
 * 服务端逻辑处理器
 *
 * @author : Lin Can
 * @date : 2019/2/27 16:15
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 服务器接收到数据的时候，会回调这个方法
     *
     * @param ctx 上下文
     * @param msg 接收到的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        System.out.println(new Date() + ": 服务器接收到消息");

        ByteBuf request = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodec.INSTANCE.decode(request);

        // 如果时登录信息，则处理后返回登录响应
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginService loginService = new LoginService();

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            if (loginService.valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReson("客户端用户名密码校验失败");
            }

            // 编码登录响应对象
            ByteBuf response = PacketCodec.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            // 写到客户端
            ctx.channel().writeAndFlush(response);
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date() + " 收到客户端的消息：" + messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务器回复【" + messageRequestPacket.getMessage() + "】");

            ByteBuf response = PacketCodec.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(response);
        }


    }


}
