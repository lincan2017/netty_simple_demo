package netty.protocol.pocket.impl.response;

import lombok.Getter;
import lombok.Setter;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 登出响应的数据包
 *
 * @author : Lin Can
 * @date : 2019/3/7 15:36
 */
@Getter
@Setter
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE_COMMAND;
    }
}
