package netty.protocol.pocket.impl.response;

import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;

/**
 * @author : Lin Can
 * @date : 2019/3/11 15:47
 */
public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_RESPONSE_COMMAND;
    }
}
