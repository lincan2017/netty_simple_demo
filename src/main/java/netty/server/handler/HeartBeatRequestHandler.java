package netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.pocket.impl.request.HeartBeatRequestPacket;
import netty.protocol.pocket.impl.response.HeartBeatResponsePacket;

import java.util.Date;

/**
 * 心跳包处理器
 *
 * @author : Lin Can
 * @date : 2019/3/11 15:48
 */
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket>{

    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) {
        System.out.println(new Date() + ": 收到心跳包：");
        HeartBeatResponsePacket packet = new HeartBeatResponsePacket();
        ctx.writeAndFlush(packet);
    }
}
