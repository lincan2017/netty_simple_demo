package netty.util;

import io.netty.channel.Channel;
import netty.Attributes;
import netty.session.Session;

import java.util.HashMap;
import java.util.Map;

/**
 * Session 的相关操作工具类
 *
 * @author : Lin Can
 * @date : 2019/3/6 9:26
 */
public class SessionUtil {
    private final static Map<Long, Channel> sessionMap = new HashMap<>();

    /**
     * 绑定 Session 到连接上
     *
     * @param session user login msg
     * @param channel user channel
     */
    public static void bindSession(Session session, Channel channel) {
        sessionMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 根据连接获取对应的会话信息
     *
     * @param channel 连接
     * @return session of channel
     */
    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    /**
     * 根据用户 id 获取对应的 连接
     *
     * @param userId 用户 id
     * @return channel of userId
     */
    public static Channel getChannel(Long userId) {
        return sessionMap.get(userId);
    }

    /**
     * 不再根据 LOGIN 属性判断是否登录，而是根据 SESSION 属性判断是否登录
     *
     * @param channel user channel
     * @return login successAble
     */
    public static Boolean hasLogin(Channel channel) {
        //return channel.attr(Attributes.SESSION).get() != null;

        return channel.hasAttr(Attributes.SESSION);
    }

    /**
     * 解除会话绑定
     *
     * @param channel 连接抽象
     */
    public static void unBindSession(Channel channel) {
        if (!hasLogin(channel)) {
            return;
        }
        sessionMap.remove(getSession(channel).getUserId());
        channel.attr(Attributes.SESSION).set(null);
    }
}
