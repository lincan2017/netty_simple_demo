package netty.protocol.pocket.impl.response;

import lombok.Getter;
import lombok.Setter;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 创建群聊的响应数据包
 *
 * @author : Lin Can
 * @date : 2019/3/7 10:42
 */
@Getter
@Setter
public class CreateGroupResponsePacket extends Packet {

    private boolean success;

    private Long groupId;

    private String[] userNames;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE_COMMAND;
    }
}
