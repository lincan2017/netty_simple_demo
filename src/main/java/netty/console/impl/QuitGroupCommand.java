package netty.console.impl;

import io.netty.channel.Channel;
import netty.console.ConsoleCommand;
import netty.protocol.pocket.impl.request.QuitGroupRequestPacket;

import java.util.Scanner;

/**
 * 退群指令
 *
 * @author : Lin Can
 * @date : 2019/3/11 10:17
 */
public class QuitGroupCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("输入 groupId，退出群聊：");

        String groupId = scanner.nextLine();
        QuitGroupRequestPacket requestPacket = new QuitGroupRequestPacket();
        requestPacket.setGroupId(Long.valueOf(groupId));

        channel.writeAndFlush(requestPacket);
    }
}
