package netty.util;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import netty.Attributes;

/**
 * 登录相关的工具类
 *
 * @author : Lin Can
 * @date : 2019/2/28 10:28
 */
public class LoginUtil {

    /**
     * 绑定登录标志
     *
     * @param channel 链接
     */
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    /**
     * 是否已绑定登录标志
     *
     * @param channel 链接
     * @return status about login
     */
    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }

}
