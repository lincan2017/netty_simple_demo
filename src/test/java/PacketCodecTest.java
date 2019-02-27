
import io.netty.buffer.ByteBuf;
import netty.protocol.PacketCodec;
import netty.protocol.pocket.Packet;
import netty.protocol.pocket.impl.LoginRequestPacket;
import org.junit.Assert;
import org.junit.Test;
import serialize.Serialize;
import serialize.impl.MyJsonSerialize;

/**
 * 测试根据协议打包和解包
 * @author : Lin Can
 * @date : 2019/2/27 9:09
 */
public class PacketCodecTest {
    @Test
    public void encodeAndDecode() {
        Serialize serialize = new MyJsonSerialize();

        // 未发送的数据包
        LoginRequestPacket loginPacket = new LoginRequestPacket();

        loginPacket.setUsername("admin");
        loginPacket.setPassword("123456");

        // 编解码类
        PacketCodec packetCodec = new PacketCodec();
        // 发送的二进制数据流
        ByteBuf byteBuf = packetCodec.encode(loginPacket);
        // 接收数据后解码得到数据包
        Packet decodePacket = packetCodec.decode(byteBuf);

        // 发送前未编码的数据包序列化结果要跟接收数据包解码后得到的数据包的序列话结果一致
        Assert.assertArrayEquals(serialize.serialize(loginPacket),serialize.serialize(decodePacket));
    }
}
