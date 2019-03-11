package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.pocket.impl.response.GroupMessageResponsePacket;

/**
 * 群消息响应处理器
 *
 * @author : Lin Can
 * @date : 2019/3/11 11:44
 */
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket msg) {

        Long fromGroupId = msg.getGroupId();
        Long fromUser = msg.getUserId();
        if (msg.isSuccess()) {
            System.out.println("收到群[" + fromGroupId + "]中[" + fromUser + "]发来的消息：" + msg.getMessage());
        } else {
            System.out.println("发送到消息到群[" + fromGroupId + "]失败：" + msg.getReason());
        }
    }
}
