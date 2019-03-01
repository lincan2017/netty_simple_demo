package netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import netty.protocol.PacketCodec;

import java.util.List;

/**
 * 解码器
 * @author : Lin Can
 * @date : 2019/3/1 14:34
 */
public class PacketDecoder extends ByteToMessageDecoder {

    /**
     * 复写解码的方法
     * 1. in 不需要类型转换就是 ByteBuf 类型
     * 2. out 会将解码后的对象传递到下一个 Handler
     * 3. 无需自己手动处理 ByteBuf 的内存回收问题
     *
     * @param ctx 连接的上下文对象
     * @param in 收到的消息
     * @param out 存放解码后的内容，并传递到下一个 Handler
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        out.add(PacketCodec.INSTANCE.decode(in));
    }
}
