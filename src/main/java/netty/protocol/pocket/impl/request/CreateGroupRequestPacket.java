package netty.protocol.pocket.impl.request;

import lombok.Getter;
import lombok.Setter;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 创建群组的数据包格式
 *
 * @author : Lin Can
 * @date : 2019/3/7 10:38
 */
@Setter
@Getter
public class CreateGroupRequestPacket extends Packet {
    private String[] userIds;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST_COMMAND;
    }
}
