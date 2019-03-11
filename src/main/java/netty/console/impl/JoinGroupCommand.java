package netty.console.impl;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import netty.console.ConsoleCommand;
import netty.protocol.pocket.impl.request.JoinGroupRequestPacket;
import netty.util.GroupUtil;

import java.util.Scanner;

/**
 * 加入群聊的控制台指令
 *
 * @author : Lin Can
 * @date : 2019/3/8 16:08
 */
public class JoinGroupCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("输入 groupId 加入群聊");

        String groupId = scanner.nextLine();

        JoinGroupRequestPacket requestPacket = new JoinGroupRequestPacket();
        requestPacket.setGroupId(Long.valueOf(groupId));

        channel.writeAndFlush(requestPacket);
    }
}
