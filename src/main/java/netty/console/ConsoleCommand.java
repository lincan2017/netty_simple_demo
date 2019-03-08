package netty.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 控制台命令接口
 *
 * @author : Lin Can
 * @date : 2019/3/6 14:51
 */
public interface ConsoleCommand {
    void exec(Scanner scanner, Channel channel);
}
