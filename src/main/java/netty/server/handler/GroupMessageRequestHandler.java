package netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import netty.protocol.pocket.impl.request.GroupMessageRequestPacket;
import netty.protocol.pocket.impl.response.GroupMessageResponsePacket;
import netty.util.GroupUtil;
import netty.util.SessionUtil;

/**
 * 群消息请求处理器
 *
 * @author : Lin Can
 * @date : 2019/3/11 11:35
 */
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) {
        Long groupId = msg.getGroupId();
        String message = msg.getMessage();

        GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
        responsePacket.setGroupId(groupId);

        ChannelGroup channelGroup = GroupUtil.getChannelGroup(groupId);
        // 如果群不存在，则返回发送失败给发送方
        if (channelGroup == null || channelGroup.isEmpty()) {
            responsePacket.setSuccess(false);
            responsePacket.setReason("消息[" + message + "]发送失败：不存在[" + groupId + "]群");
            ctx.channel().writeAndFlush(responsePacket);
            return;
        }

        // 遍历群成员，给每个群成员发送消息
        for (Channel channel : channelGroup) {
            responsePacket.setSuccess(true);
            responsePacket.setMessage(message);
            responsePacket.setUserId(SessionUtil.getSession(ctx.channel()).getUserId());
            channel.writeAndFlush(responsePacket);
        }
    }
}
