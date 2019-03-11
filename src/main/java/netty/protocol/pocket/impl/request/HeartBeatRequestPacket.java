package netty.protocol.pocket.impl.request;

import lombok.Getter;
import lombok.Setter;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * 心跳包请求
 *
 * @author : Lin Can
 * @date : 2019/3/11 15:41
 */
@Setter
@Getter
public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_REQUEST_COMMAND;
    }
}
