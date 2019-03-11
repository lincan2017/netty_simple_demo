package netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import netty.protocol.pocket.impl.request.ListGroupMembersRequestPacket;
import netty.protocol.pocket.impl.response.ListGroupMembersResponsePacket;
import netty.session.Session;
import netty.util.GroupUtil;
import netty.util.SessionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 展示群信息的请求处理器
 *
 * @author : Lin Can
 * @date : 2019/3/11 9:38
 */
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    protected ListGroupMembersRequestHandler() {

    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) {
        Long groupId = msg.getGroupId();
        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        responsePacket.setGroupId(groupId);

        ChannelGroup channelGroup = GroupUtil.getChannelGroup(groupId);
        if (channelGroup == null || channelGroup.isEmpty()) {
            responsePacket.setReason("没有对应的群组");
            responsePacket.setSuccess(false);
            ctx.channel().writeAndFlush(responsePacket);
            return;
        }

        Session[] sessions = new Session[channelGroup.size()];

        int i=0;
        for (Channel channel : channelGroup) {
            Session session = SessionUtil.getSession(channel);
            sessions[i++] = session;
        }
        responsePacket.setSessions(sessions);
        responsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
