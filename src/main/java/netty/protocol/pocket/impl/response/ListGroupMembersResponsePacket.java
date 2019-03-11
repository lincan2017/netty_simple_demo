package netty.protocol.pocket.impl.response;

import lombok.Getter;
import lombok.Setter;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;
import netty.session.Session;

import java.util.List;

/**
 * 展示群成员响应指令数据包
 *
 * @author : Lin Can
 * @date : 2019/3/11 9:33
 */
@Setter
@Getter
public class ListGroupMembersResponsePacket extends Packet {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 群id
     */
    private Long groupId;

    /**
     * 群成员信息列表
     */
    private Session[] sessions;

    /**
     * 失败原因
     */
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_RESPONSE_COMMAND;
    }
}
