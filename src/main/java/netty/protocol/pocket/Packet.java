package netty.protocol.pocket;

import lombok.Data;

/**
 * 数据包的抽象类
 * @author : Lin Can
 * @date : 2019/2/27 9:22
 */
@Data
public abstract class Packet {

    /**
     * 版本号
     */
    private byte version = 1;

    /**
     * 获取指令类型
     * @return Command
     */
    public abstract Byte getCommand();

}
