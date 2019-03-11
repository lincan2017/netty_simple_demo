package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.pocket.impl.response.JoinGroupResponsePacket;

/**
 * @author : Lin Can
 * @date : 2019/3/8 17:00
 */
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket msg) {
        if (msg.isSuccess()) {
            System.out.println("加入群[" + msg.getGroupId() + "]成功!");
        } else {
            System.err.println("加入群[" + msg.getGroupId() + "]失败，原因为：" + msg.getReason());
        }
    }
}
