package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.pocket.impl.response.MessageResponsePacket;

import java.util.Date;

/**
 * 接收服务器的消息响应处理器
 *
 * @author : Lin Can
 * @date : 2019/3/1 15:32
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    /**
     * 对收到的响应进行处理
     *
     * @param ctx 连接的上下文对象
     * @param msg 响应
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) {
        System.out.println(new Date() + " 收到服务器消息：" + msg.getMessage());

    }
}
