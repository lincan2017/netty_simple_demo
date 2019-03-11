package netty.protocol.pocket.impl.response;

import lombok.Getter;
import lombok.Setter;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 退群响应数据包
 *
 * @author : Lin Can
 * @date : 2019/3/11 10:14
 */
@Setter
@Getter
public class QuitGroupResponsePacket extends Packet {

    /**
     * 群id
     */
    private Long groupId;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 原因
     */
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_RESPONSE_COMMAND;
    }
}
