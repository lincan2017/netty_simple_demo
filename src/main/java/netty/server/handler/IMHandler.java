package netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

import java.util.HashMap;
import java.util.Map;

/**
 * 整合handler
 *
 * @author : Lin Can
 * @date : 2019/3/11 14:02
 */
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {
    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler() {
        handlerMap = new HashMap<>();

        handlerMap.put(Command.GROUP_MESSAGE_REQUEST_COMMAND, new GroupMessageRequestHandler());
        handlerMap.put(Command.QUIT_GROUP_REQUEST_COMMAND, new QuitGroupRequestHandler());
        handlerMap.put(Command.LIST_GROUP_REQUEST_COMMAND, new ListGroupMembersRequestHandler());
        handlerMap.put(Command.JOIN_GROUP_REQUEST_COMMAND, new JoinGroupRequestHandler());
        handlerMap.put(Command.LOGOUT_REQUEST_COMMAND, new LogoutRequestHandler());
        handlerMap.put(Command.CREATE_GROUP_REQUEST_COMMAND, new CreateGroupRequestHandler());
        handlerMap.put(Command.MESSAGE_REQUEST_COMMAND, new MessageRequestHandler());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        handlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }
}
