package netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import netty.protocol.PacketCodec;
import netty.protocol.pocket.Packet;

/**
 * 编码器
 * @author : Lin Can
 * @date : 2019/3/1 14:42
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    /**
     * 泛型定义为 Packet 类型，表示该处理器处理 Packet 类型的数据
     * 1. 不用每次写数据到对端都单独进行一次编码
     * 2. 无需关心 ByteBuf 的创建
     *
     * @param ctx 连接上下文对象
     * @param packet 待编码的 java 对象
     * @param out 待写到对端的对象
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
        PacketCodec.INSTANCE.encode(out,packet);
    }
}
