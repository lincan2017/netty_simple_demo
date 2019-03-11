package netty.console.impl;

import io.netty.channel.Channel;
import netty.console.ConsoleCommand;
import netty.protocol.pocket.impl.request.ListGroupMembersRequestPacket;

import java.util.Scanner;

/**
 * 展示群成员的指令
 *
 * @author : Lin Can
 * @date : 2019/3/11 9:28
 */
public class ListGroupMembersCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("输入 groupId，获取群成员列表：");

        String groupId = scanner.nextLine();
        ListGroupMembersRequestPacket requestPacket = new ListGroupMembersRequestPacket();
        requestPacket.setGroupId(Long.valueOf(groupId));

        channel.writeAndFlush(requestPacket);
    }
}
