package netty.protocol.pocket.impl.response;

import lombok.Getter;
import lombok.Setter;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 群消息响应数据包
 *
 * @author : Lin Can
 * @date : 2019/3/11 11:17
 */
@Setter
@Getter
public class GroupMessageResponsePacket extends Packet {

    /**
     * 是否发送成功
     */
    private boolean success;

    /**
     * 消息所需群id
     */
    private Long groupId;

    /**
     * 发送者id
     */
    private Long userId;

    /**
     * 消息
     */
    private String message;

    /**
     * 失败原因
     */
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE_COMMAND;
    }
}
