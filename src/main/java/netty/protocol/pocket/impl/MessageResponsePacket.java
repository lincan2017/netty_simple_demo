package netty.protocol.pocket.impl;

import lombok.Data;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 服务端->客户端消息类
 * @author : Lin Can
 * @date : 2019/2/28 10:23
 */

@Data
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
