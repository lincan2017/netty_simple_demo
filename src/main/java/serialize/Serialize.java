package serialize;

import netty.protocol.pocket.Packet;
import serialize.impl.MyJsonSerialize;

/**
 * 序列化的接口
 * @author : Lin Can
 * @date : 2019/2/27 9:05
 */
public interface Serialize {

    /**
     * 默认的序列化方式
     */
    Serialize DEFAULT = new MyJsonSerialize();

    /**
     * 获取序列化算法
     * @return 返回序列化算法
     */
    byte getSerializeAlgorithm();

    /**
     * 对象转换为二进制
     * @param object 序列化的对象
     * @return 二进制字节流
     */
    byte[] serialize(Object object);

    /**
     * 二进制字节流转换为对象
     * @param data 收到的二进制字节流
     * @param clazz 期待转换的类型
     * @param <T> 泛型用于限定返回值
     * @return 返回指定类型的数据
     */
    <T> T deserialize(byte[] data, Class<T> clazz);
}
