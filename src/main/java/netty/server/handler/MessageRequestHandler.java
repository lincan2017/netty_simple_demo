package netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.pocket.impl.request.MessageRequestPacket;
import netty.protocol.pocket.impl.response.MessageResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;

import java.util.Date;

/**
 * 服务端接收客户端发送的消息处理器
 *
 * @author : Lin Can
 * @date : 2019/3/1 15:09
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    /**
     * 在 channelRead0 中实现 接收消息后的处理逻辑
     * 1. 泛型定义为 MessageRequestPacket 表示该处理器只处理 该类型的消息
     * 2. 不用手动进行对象传递
     * 3. 不用臃肿的 if...else...
     * 4. 不用在业务代码中体现对消息类型的判断
     *
     * @param ctx 连接的上下文对象
     * @param msg 接收消息的 Java 对象
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) {
        // 响应
        receiveMessage(msg, ctx);
    }

    private void receiveMessage(MessageRequestPacket messageRequestPacket, ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

        // 获取当前连接的用户信息
        Session session = SessionUtil.getSession(ctx.channel());

        // 将用户信息（即是发送方的用户信息）和发送消息封装到 MessageResponsePacket 中
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUsername());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        // 获取接收方的用户连接
        Channel channel = SessionUtil.getChannel(messageRequestPacket.getToUserId());
        if (channel != null && SessionUtil.hasLogin(channel)) {
            // 将消息发送到对应的用户连接
            channel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
        }
    }
}
