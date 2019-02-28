package netty.protocol.pocket.impl;

import lombok.Data;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 客户端发送消息给服务端的消息类
 * @author : Lin Can
 * @date : 2019/2/28 10:19
 */
@Data
public class MessageRequestPacket extends Packet {

    /**
     * 消息内容
     */
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST_COMMAND;
    }
}
