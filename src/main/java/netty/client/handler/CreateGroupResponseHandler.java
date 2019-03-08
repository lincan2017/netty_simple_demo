package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.pocket.impl.response.CreateGroupResponsePacket;
import org.apache.commons.lang3.StringUtils;

/**
 * 创建群聊的响应处理
 *
 * @author : Lin Can
 * @date : 2019/3/7 14:07
 */
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) {
        System.out.print("群创建成功，id 为[" + msg.getGroupId() + "], ");
        System.out.println("群里面有：" + StringUtils.join(msg.getUserNames(),","));
    }
}
