package netty.protocol.pocket.impl.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 服务端->客户端消息类
 * @author : Lin Can
 * @date : 2019/2/28 10:23
 */

@Getter
@Setter
public class MessageResponsePacket extends Packet {

    /**
     * 消息内容
     */
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE_COMMAND;
    }
}
