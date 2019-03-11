package netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import netty.protocol.pocket.impl.request.QuitGroupRequestPacket;
import netty.protocol.pocket.impl.response.QuitGroupResponsePacket;
import netty.util.GroupUtil;

/**
 * 退群请求处理器
 *
 * @author : Lin Can
 * @date : 2019/3/11 10:21
 */
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) {
        Long groupId = msg.getGroupId();
        ChannelGroup channelGroup = GroupUtil.getChannelGroup(groupId);

        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
        responsePacket.setGroupId(groupId);

        if (channelGroup == null || channelGroup.isEmpty()) {
            responsePacket.setSuccess(false);
            responsePacket.setReason("不存在该群组");
            ctx.channel().writeAndFlush(responsePacket);
            return;
        }
        channelGroup.remove(ctx.channel());
        responsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
