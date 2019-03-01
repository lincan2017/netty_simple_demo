package netty.protocol.pocket.impl.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 登录的数据包类
 * @author : Lin Can
 * @date : 2019/2/27 9:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequestPacket extends Packet {
    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST_COMMAND;
    }
}
