package serialize.impl;

import com.alibaba.fastjson.JSON;
import netty.protocol.pocket.Packet;
import serialize.Serialize;
import serialize.SerializeAlgorithm;

/**
 * Json方式的序列化和反序列化
 * @author : Lin Can
 * @date : 2019/2/27 9:20
 */
public class MyJsonSerialize implements Serialize {
    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public byte getSerializeAlgorithm() {
        return SerializeAlgorithm.MY_JSON;
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return JSON.parseObject(data,clazz);
    }
}
