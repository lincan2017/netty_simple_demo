package netty.console.impl;

import io.netty.channel.Channel;
import netty.console.ConsoleCommand;
import netty.protocol.pocket.impl.request.MessageRequestPacket;

import java.util.Scanner;

/**
 * 发送消息给特定用户的控制台指令
 *
 * @author : Lin Can
 * @date : 2019/3/7 15:49
 */
public class SendToUserCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("输入需要接收方的id 和 消息：");
        String userId = scanner.nextLine();
        String msg = scanner.nextLine();

        MessageRequestPacket requestPacket = new MessageRequestPacket();
        requestPacket.setToUserId(Long.valueOf(userId));
        requestPacket.setMessage(msg);

        channel.writeAndFlush(requestPacket);
    }
}
