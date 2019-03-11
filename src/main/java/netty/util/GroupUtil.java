package netty.util;

import io.netty.channel.group.ChannelGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * 群组相关工具类
 *
 * @author : Lin Can
 * @date : 2019/3/8 16:13
 */
public class GroupUtil {
    private static final Map<Long, ChannelGroup> groupMap = new HashMap<>();

    /**
     * 根据群组id 获取群组
     *
     * @param groupId groupId
     * @return ChannelGroup
     */
    public static ChannelGroup getChannelGroup(Long groupId) {
        return groupMap.get(groupId);
    }

    /**
     * 将群组id和群通道放入map
     *
     * @param groupId groupId
     * @param group channelGroup
     */
    public static void newGroup(Long groupId, ChannelGroup group) {
        groupMap.put(groupId,group);
    }

    /**
     * 解散群
     * @param groupId groupId
     */
    public static void releaseGroup(Long groupId) {
        groupMap.remove(groupId);
    }
}
