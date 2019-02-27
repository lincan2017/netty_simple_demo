package netty.protocol.pocket.impl;

import lombok.Data;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 登录响应的类
 * @author : Lin Can
 * @date : 2019/2/27 15:41
 */

@Data
public class LoginResponsePacket extends Packet {

    /**
     * 成功/失败的标识
     */
    private Boolean success;

    /**
     * 失败原因
     */
    private String reson;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE_COMMAND;
    }
}
