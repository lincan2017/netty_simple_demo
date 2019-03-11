package netty.console.impl;

import io.netty.channel.Channel;
import netty.console.ConsoleCommand;
import netty.protocol.pocket.impl.request.GroupMessageRequestPacket;

import java.util.Scanner;

/**
 * 发送群消息的指令
 *
 * @author : Lin Can
 * @date : 2019/3/11 11:24
 */
public class SentToGroupCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个某个群组：");

        String nextLine = scanner.nextLine();

        String[] strs = nextLine.split(" ", 2);

        GroupMessageRequestPacket requestPacket = new GroupMessageRequestPacket();
        requestPacket.setGroupId(Long.valueOf(strs[0]));
        requestPacket.setMessage(strs[1]);

        channel.writeAndFlush(requestPacket);
    }
}
