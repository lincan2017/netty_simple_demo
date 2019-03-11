package netty.protocol.pocket.impl.request;

import lombok.Getter;
import lombok.Setter;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 展示群成员请求指令包
 *
 * @author : Lin Can
 * @date : 2019/3/11 9:30
 */
@Getter
@Setter
public class ListGroupMembersRequestPacket extends Packet {
    private Long groupId;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_REQUEST_COMMAND;
    }
}
