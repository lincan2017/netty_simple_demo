package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.util.SessionUtil;

/**
 * 登出的响应处理器
 *
 * @author : Lin Can
 * @date : 2019/3/7 16:09
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponseHandler> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponseHandler msg) {
        SessionUtil.unBindSession(ctx.channel());
    }
}
