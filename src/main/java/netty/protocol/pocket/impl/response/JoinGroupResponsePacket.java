package netty.protocol.pocket.impl.response;

import lombok.Getter;
import lombok.Setter;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 加入群组响应数据包
 *
 * @author : Lin Can
 * @date : 2019/3/8 16:42
 */
@Setter
@Getter
public class JoinGroupResponsePacket extends Packet {

    /**
     * 群id
     */
    private Long groupId;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 失败原因
     */
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE_COMMAND;
    }
}
