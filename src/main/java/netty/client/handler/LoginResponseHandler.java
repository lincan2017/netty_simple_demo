package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.pocket.impl.response.LoginResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;

import java.util.Date;

/**
 * 登录响应处理器
 *
 * @author : Lin Can
 * @date : 2019/3/1 15:28
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    /**
     * 在该方法中实现对服务端的登录响应处理
     *
     * @param ctx 连接的上下文对象
     * @param msg 响应的内容
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) {
        if (msg.getSuccess()) {
            SessionUtil.bindSession(new Session(msg.getUserId(),msg.getUsername()),ctx.channel());
            System.out.println(new Date() + " 客户端登录成功，用户ID: " + msg.getUserId());
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + msg.getReson());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭!");
        super.channelInactive(ctx);
    }
}
