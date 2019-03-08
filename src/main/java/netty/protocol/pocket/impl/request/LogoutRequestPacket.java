package netty.protocol.pocket.impl.request;

import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 登出请求的数据包
 *
 * @author : Lin Can
 * @date : 2019/3/7 15:36
 */
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST_COMMAND;
    }
}
