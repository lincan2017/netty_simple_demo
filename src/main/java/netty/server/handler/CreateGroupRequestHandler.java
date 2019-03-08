package netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import netty.protocol.pocket.impl.request.CreateGroupRequestPacket;
import netty.protocol.pocket.impl.response.CreateGroupResponsePacket;
import netty.util.SessionUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 创建群聊的请求管理器
 *
 * @author : Lin Can
 * @date : 2019/3/7 14:07
 */
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) {
        String[] userIds = msg.getUserIds();
        String[] userNames = new String[userIds.length];
        int i = 0;

        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        for (String userIdStr : userIds) {
            Channel channel = SessionUtil.getChannel(Long.valueOf(userIdStr));
            if (channel != null) {
                userNames[i++] = SessionUtil.getSession(channel).getUsername();
                channelGroup.add(channel);
            }
        }

        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
        responsePacket.setGroupId(System.currentTimeMillis());
        responsePacket.setSuccess(true);
        responsePacket.setUserNames(userNames);

        channelGroup.writeAndFlush(responsePacket);

        System.out.print("群创建成功，id 为[" + responsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + StringUtils.join(responsePacket.getUserNames(),","));
    }
}
