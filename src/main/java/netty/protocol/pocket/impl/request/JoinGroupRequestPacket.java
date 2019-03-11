package netty.protocol.pocket.impl.request;

import lombok.Getter;
import lombok.Setter;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 加入群聊的数据包
 *
 * @author : Lin Can
 * @date : 2019/3/8 16:38
 */
@Getter
@Setter
public class JoinGroupRequestPacket extends Packet {

    /**
     * 群组id
     */
    private Long groupId;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_REQUEST_COMMAND;
    }
}
