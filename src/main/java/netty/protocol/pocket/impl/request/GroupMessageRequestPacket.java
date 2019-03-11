package netty.protocol.pocket.impl.request;

import lombok.Getter;
import lombok.Setter;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 发送群消息的请求数据包
 *
 * @author : Lin Can
 * @date : 2019/3/11 11:15
 */
@Getter
@Setter
public class GroupMessageRequestPacket extends Packet {

    /**
     * 群id
     */
    private Long groupId;

    /**
     * 群消息
     */
    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST_COMMAND;
    }
}
