package netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import netty.protocol.PacketCodec;

/**
 * 自定义拆包器
 *
 * @author : Lin Can
 * @date : 2019/3/4 11:09
 */
public class Spliter extends LengthFieldBasedFrameDecoder {
    /**
     * 偏移量
     */
    private static final int LENGTH_FIELD_OFFSET = 7;

    /**
     * 存放核心数据长度的长度域长度
     */
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        int magicNum = in.getInt(in.readerIndex());
        if (magicNum != PacketCodec.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
