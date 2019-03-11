package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.pocket.impl.response.ListGroupMembersResponsePacket;
import org.apache.commons.lang3.StringUtils;

/**
 * @author : Lin Can
 * @date : 2019/3/11 9:45
 */
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket msg) {
        if (msg.getSuccess()) {
            System.out.println("群[" + msg.getGroupId() + "]中的人包括：" + StringUtils.join(msg.getSessions(), ";"));
        } else {
            System.out.println("展示群[" + msg.getGroupId() + "]成员失败：" + msg.getReason());
        }
    }
}
