package netty.console.impl;

import io.netty.channel.Channel;
import netty.console.ConsoleCommand;
import netty.protocol.pocket.impl.request.LogoutRequestPacket;

import java.util.Scanner;

/**
 * 登出的控制台指令
 *
 * @author : Lin Can
 * @date : 2019/3/7 14:21
 */
public class LogoutCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket requestPacket = new LogoutRequestPacket();
        channel.writeAndFlush(requestPacket);
    }
}
