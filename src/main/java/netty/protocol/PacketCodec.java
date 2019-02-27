package netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import netty.protocol.command.Command;
import netty.protocol.pocket.Packet;
import netty.protocol.pocket.impl.LoginRequestPacket;
import serialize.Serialize;

import java.util.HashMap;
import java.util.Map;

/**
 * 编解码类
 * @author : Lin Can
 * @date : 2019/2/27 9:23
 */
public class PacketCodec {
    private static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte,Serialize> SERIALIZE_MAP;
    private static final Map<Byte,Class<? extends Packet>> PACKET_MAP;

    static {
        SERIALIZE_MAP = new HashMap<>();
        Serialize serialize = Serialize.DEFAULT;
        SERIALIZE_MAP.put(serialize.getSerializeAlgorithm(),serialize);

        PACKET_MAP = new HashMap<>();
        PACKET_MAP.put(Command.LOGIN_COMMAND, LoginRequestPacket.class);
    }

    /**
     * 编码：将数据包转换为 Netty 传输的数据载体对象
     * @author : Lin Can
     * @date: 2019/2/27 10:43
     * @param packet  数据包
     * @return: io.netty.buffer.ByteBuf
     */
    public ByteBuf encode(Packet packet) {
        // 1 获取 Netty 的传输数据载体
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        // 2 序列化JAVA对象
        byte[] datas = Serialize.DEFAULT.serialize(packet);

        // 3 按照协议设计填充数据

        // 前四个字节是魔数
        byteBuf.writeInt(MAGIC_NUMBER);
        // 协议版本号
        byteBuf.writeByte(packet.getVersion());
        // 序列化类型
        byteBuf.writeByte(Serialize.DEFAULT.getSerializeAlgorithm());
        // 指令类型
        byteBuf.writeByte(packet.getCommand());

        // 四个字节表示核心数据长度
        byteBuf.writeInt(datas.length);
        // 核心数据
        byteBuf.writeBytes(datas);

        return byteBuf;
    }

    /**
     * 解码:将通过 Netty 收到的数据 转换为数据包对象
     * @author : Lin Can
     * @date: 2019/2/27 10:44
     * @param byteBuf 传输数据载体
     * @return: netty.protocol.pocket.Packet
     */
    public Packet decode(ByteBuf byteBuf) {
        // 获取前四个字节，判断是否符合协议要求
        int receiveMagicNum = byteBuf.readInt();
        if (receiveMagicNum != MAGIC_NUMBER) {
            throw new RuntimeException("收到不符合协议要求的数据包");
        }

        // 跳过版本号
        byteBuf.readByte();
        // 序列化/反序列化算法
        byte serializeAlgorithm = byteBuf.readByte();
        Serialize serialize = SERIALIZE_MAP.get(serializeAlgorithm);

        // 指令类型
        byte commandType = byteBuf.readByte();
        Class<? extends Packet> clazz = PACKET_MAP.get(commandType);

        // 核心数据长度
        int dataLen = byteBuf.readInt();
        System.out.println("核心数据字节长度"+dataLen);
        byte[] dist = new byte[dataLen];
        byteBuf.readBytes(dist);

        return serialize.deserialize(dist,clazz);
    }
}
