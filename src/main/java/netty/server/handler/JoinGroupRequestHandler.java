package netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import netty.protocol.pocket.impl.request.JoinGroupRequestPacket;
import netty.protocol.pocket.impl.response.JoinGroupResponsePacket;
import netty.util.GroupUtil;

/**
 * 加入群组请求的处理器
 *
 * @author : Lin Can
 * @date : 2019/3/8 16:50
 */
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    protected JoinGroupRequestHandler() {

    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) {
        // 获取客户端期望加入的群组信息
        Long groupId = msg.getGroupId();
        ChannelGroup channelGroup = GroupUtil.getChannelGroup(groupId);

        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
        responsePacket.setGroupId(groupId);
        if (channelGroup == null) {
            // 如果群组不存在，则返回加入失败
            responsePacket.setSuccess(false);
            responsePacket.setReason("该群组不存在");
        } else {
            responsePacket.setSuccess(true);
            // 将该客户端对应的链接加入对应的群
            channelGroup.add(ctx.channel());
        }

        ctx.channel().writeAndFlush(responsePacket);
    }
}
