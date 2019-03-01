package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.pocket.impl.request.LoginRequestPacket;
import netty.protocol.pocket.impl.response.LoginResponsePacket;
import netty.util.LoginUtil;

import java.util.Date;

/**
 * 登录响应处理器
 *
 * @author : Lin Can
 * @date : 2019/3/1 15:28
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 客户端开始登录");
        // 构建请求对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(12346789L);
        loginRequestPacket.setUsername("flash_fans");
        loginRequestPacket.setPassword("pwd");

        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    /**
     * 在该方法中实现对服务端的登录响应处理
     *
     * @param ctx 连接的上下文对象
     * @param msg 响应的内容
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) {
        if (msg.getSuccess()) {
            LoginUtil.markAsLogin(ctx.channel());
            System.out.println(new Date() + ": 客户端登录成功");
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + msg.getReson());
        }
    }
}
