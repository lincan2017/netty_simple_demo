package netty.protocol.pocket.impl.request;

import lombok.Getter;
import lombok.Setter;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 退群请求数据包
 *
 * @author : Lin Can
 * @date : 2019/3/11 10:06
 */
@Getter
@Setter
public class QuitGroupRequestPacket extends Packet {

    /**
     * 群id
     */
    private Long groupId;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_REQUEST_COMMAND;
    }
}
