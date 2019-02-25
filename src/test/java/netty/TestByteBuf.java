package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author : Lin Can
 * @date : 2019/2/25 16:26
 */
public class TestByteBuf {
    private final static int INIT_CAPACITY = 9;
    private final static int MAX_CAPACITY = 100;
    private ByteBuf buffer;

    private int readerIndex;
    private int writerIndex;

    private int readableBytes;
    private int writableBytes;

    private int maxWritableBytes;

    @Before
    public void setup() {
        buffer = ByteBufAllocator.DEFAULT.buffer(INIT_CAPACITY, MAX_CAPACITY);
        readerIndex = buffer.readerIndex();
        writerIndex = buffer.writerIndex();

        readableBytes = buffer.readableBytes();
        writableBytes = buffer.writableBytes();

        maxWritableBytes = buffer.maxWritableBytes();

    }

    @After
    public void after() {
        Assert.assertTrue(buffer.release());
        try {
            buffer.release();
            Assert.fail("release fail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * write 方法改变写指针，写完之后写指针未到 capacity 的时候，buffer 仍然可写
     */
    @Test
    public void changeWriteIndex() {
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        Assert.assertEquals(INIT_CAPACITY, buffer.capacity());
        Assert.assertEquals(MAX_CAPACITY, buffer.maxCapacity());
        Assert.assertEquals(readerIndex, buffer.readerIndex());
        Assert.assertEquals(writerIndex + 4, buffer.writerIndex());
        Assert.assertTrue(buffer.isReadable());
        Assert.assertTrue(buffer.isWritable());
        Assert.assertEquals(readableBytes + 4, buffer.readableBytes());
        Assert.assertEquals(writableBytes - 4, buffer.writableBytes());
        Assert.assertTrue(0 < buffer.writableBytes());
        Assert.assertEquals(MAX_CAPACITY - writerIndex - 4, buffer.maxWritableBytes());
    }

    /**
     * write 方法改变写指针，写完之后写指针未到 capacity 的时候，buffer 仍然可写
     * 写完 int 类型之后
     * 写指针增加4
     */
    @Test
    public void writeInt() {
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        buffer.writeInt(12);

        Assert.assertEquals(INIT_CAPACITY, buffer.capacity());
        Assert.assertEquals(MAX_CAPACITY, buffer.maxCapacity());
        Assert.assertEquals(readerIndex, buffer.readerIndex());
        Assert.assertEquals(writerIndex + 4 + 4, buffer.writerIndex());
        Assert.assertTrue(buffer.isReadable());
        Assert.assertTrue(buffer.isWritable());
        Assert.assertEquals(readableBytes + 4 + 4, buffer.readableBytes());
        Assert.assertEquals(writableBytes - 4 - 4, buffer.writableBytes());
        Assert.assertTrue(0 < buffer.writableBytes());
        Assert.assertEquals(MAX_CAPACITY - writerIndex - 4 - 4, buffer.maxWritableBytes());

    }

    /**
     * write 方法改变写指针, 写完之后写指针等于 capacity 的时候，buffer 不可写
     */
    @Test
    public void writerIndexEqualCapacity() {
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        buffer.writeInt(12);
        buffer.writeBytes(new byte[]{5});

        Assert.assertEquals(INIT_CAPACITY, buffer.capacity());
        Assert.assertEquals(MAX_CAPACITY, buffer.maxCapacity());
        Assert.assertEquals(readerIndex, buffer.readerIndex());
        Assert.assertEquals(writerIndex + 4 + 4 + 1, buffer.writerIndex());
        Assert.assertTrue(buffer.isReadable());
        Assert.assertFalse(buffer.isWritable());
        Assert.assertEquals(readableBytes + 4 + 4 + 1, buffer.readableBytes());
        Assert.assertEquals(writableBytes - 4 - 4 - 1, buffer.writableBytes());
        Assert.assertEquals(0, buffer.writableBytes());
        Assert.assertEquals(MAX_CAPACITY - INIT_CAPACITY, buffer.maxWritableBytes());

    }

    /**
     * write 方法改变写指针，写的时候发现 buffer 不可写则开始扩容，扩容之后 capacity 随即改变
     */
    @Test
    public void increaseCapacity() {
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        buffer.writeInt(12);
        buffer.writeBytes(new byte[]{5});
        buffer.writeBytes(new byte[]{6});

        int nowCapacity = buffer.capacity();
        Assert.assertTrue(INIT_CAPACITY < nowCapacity);
        Assert.assertEquals(MAX_CAPACITY, buffer.maxCapacity());
        Assert.assertEquals(readerIndex, buffer.readerIndex());
        Assert.assertEquals(writerIndex + 4 + 4 + 1 + 1, buffer.writerIndex());
        Assert.assertTrue(buffer.isReadable());
        Assert.assertTrue(buffer.isWritable());
        Assert.assertEquals(readableBytes + 4 + 4 + 1 + 1, buffer.readableBytes());
        Assert.assertEquals(nowCapacity - INIT_CAPACITY - 1, buffer.writableBytes());
        Assert.assertTrue(0 < buffer.writableBytes());
        Assert.assertEquals(MAX_CAPACITY - INIT_CAPACITY - 1, buffer.maxWritableBytes());
    }

    /**
     * get 方法不改变读写指针
     */
    @Test
    public void testGet() {
        buffer.getByte(3);
        buffer.getShort(3);
        buffer.getInt(3);

        Assert.assertEquals(MAX_CAPACITY, buffer.maxCapacity());
        Assert.assertEquals(readerIndex, buffer.readerIndex());
        Assert.assertEquals(writerIndex, buffer.writerIndex());
        Assert.assertFalse(buffer.isReadable());
        Assert.assertTrue(buffer.isWritable());
        Assert.assertEquals(readableBytes, buffer.readableBytes());
        Assert.assertEquals(writableBytes, buffer.writableBytes());
        Assert.assertTrue(0 < buffer.writableBytes());
        Assert.assertEquals(maxWritableBytes, buffer.maxWritableBytes());

    }

    /**
     * set 方法不改变读写指针
     */
    @Test
    public void testSet() {
        buffer.setByte(buffer.readableBytes() + 1, 0);
        Assert.assertEquals(INIT_CAPACITY, buffer.capacity());
        Assert.assertEquals(MAX_CAPACITY, buffer.maxCapacity());
        Assert.assertEquals(readerIndex, buffer.readerIndex());
        Assert.assertEquals(writerIndex, buffer.writerIndex());
        Assert.assertFalse(buffer.isReadable());
        Assert.assertTrue(buffer.isWritable());
        Assert.assertEquals(readableBytes, buffer.readableBytes());
        Assert.assertEquals(writableBytes, buffer.writableBytes());
        Assert.assertTrue(0 < buffer.writableBytes());
        Assert.assertEquals(MAX_CAPACITY - writerIndex, buffer.maxWritableBytes());
    }

    /**
     * read 方法改变读指针
     */
    @Test
    public void testRead() {
        buffer.writeBytes(new byte[]{1, 2, 3, 4});

        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);


        Assert.assertEquals(INIT_CAPACITY, buffer.capacity());
        Assert.assertEquals(MAX_CAPACITY, buffer.maxCapacity());
        Assert.assertEquals(readerIndex + 4, buffer.readerIndex());
        Assert.assertEquals(writerIndex + 4, buffer.writerIndex());
        Assert.assertFalse(buffer.isReadable());
        Assert.assertTrue(buffer.isWritable());
        Assert.assertEquals(0, buffer.readableBytes());
        Assert.assertEquals(writableBytes - 4, buffer.writableBytes());
        Assert.assertTrue(0 < buffer.writableBytes());
        Assert.assertEquals(MAX_CAPACITY - writerIndex - 4, buffer.maxWritableBytes());

    }
}
