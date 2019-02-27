package netty.server;

import netty.protocol.pocket.impl.LoginRequestPacket;

/**
 * 处理登录相关的逻辑
 * @author : Lin Can
 * @date : 2019/2/27 16:21
 */
class LoginService {
    Boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
