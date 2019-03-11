package netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.pocket.impl.request.LoginRequestPacket;
import netty.protocol.pocket.impl.response.LoginResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;

import java.util.Date;

/**
 * 登录请求指令的处理器
 *
 * @author : Lin Can
 * @date : 2019/3/1 15:07
 */

@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    protected LoginRequestHandler() {

    }

    /**
     * 在 channelRead0 中实现登录逻辑
     * 1. 泛型定义为 LoginRequestPacket 表示该处理器只处理该类型的指令消息
     * 2. 不用手动进行对象传递
     * 3. 不用臃肿的 if...else...
     * 4. 不用在业务代码中体现对消息类型的判断
     *
     * @param ctx 连接的上下文对象
     * @param loginRequestPacket  登录消息的 Java 对象
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        // 登录逻辑 -- 返回登录响应到对端
        ctx.channel().writeAndFlush(login(loginRequestPacket, ctx));

    }

    /**
     * 处理客户端登录消息，并返回登录响应
     * @param loginRequestPacket 登录请求
     * @return 登录响应
     */
    private LoginResponsePacket login(LoginRequestPacket loginRequestPacket, ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 收到客户端登录请求……");
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setUserId(System.currentTimeMillis());
            loginResponsePacket.setUsername(loginRequestPacket.getUsername());
            loginResponsePacket.setSuccess(true);
            System.out.println("[" + loginRequestPacket.getUsername() + "]登录成功");

            Session session = new Session(loginResponsePacket.getUserId(), loginResponsePacket.getUsername());
            SessionUtil.bindSession(session, ctx.channel());
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReson("客户端用户名密码校验失败");
            System.out.println(new Date() + "[" + loginRequestPacket.getUsername() + "]登录失败!");
        }

        return loginResponsePacket;
    }

    private Boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
        super.channelInactive(ctx);
    }
}
