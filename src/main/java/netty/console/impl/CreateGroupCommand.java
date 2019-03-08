package netty.console.impl;

import io.netty.channel.Channel;
import netty.console.ConsoleCommand;
import netty.protocol.pocket.impl.request.CreateGroupRequestPacket;

import java.util.Scanner;

/**
 * 建群的控制台指令
 *
 * @author : Lin Can
 * @date : 2019/3/6 15:12
 */
public class CreateGroupCommand implements ConsoleCommand {

    private final String SPLIT = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("【拉人群聊】输入 userId 列表，userId 之间英文逗号隔开：");

        String nextLine = scanner.nextLine();
        String[] userIdStrs = nextLine.split(SPLIT);

        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        createGroupRequestPacket.setUserIds(userIdStrs);

        channel.writeAndFlush(createGroupRequestPacket);
    }
}
