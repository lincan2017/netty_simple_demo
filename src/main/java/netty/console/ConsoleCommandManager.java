package netty.console;

import io.netty.channel.Channel;
import netty.console.impl.*;
import netty.util.SessionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 控制台指令的管理类
 *
 * @author : Lin Can
 * @date : 2019/3/6 14:53
 */
public class ConsoleCommandManager implements ConsoleCommand {
    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("login", new LoginCommand());
        consoleCommandMap.put("logout", new LogoutCommand());
        consoleCommandMap.put("createGroup", new CreateGroupCommand());
        consoleCommandMap.put("sendToUser", new SendToUserCommand());
        consoleCommandMap.put("listGroupMembers", new ListGroupMembersCommand());
        consoleCommandMap.put("quitGroup", new QuitGroupCommand());
        consoleCommandMap.put("joinGroup", new JoinGroupCommand());
        consoleCommandMap.put("sentToGroup",new SentToGroupCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入指令：");
        String command = scanner.nextLine();

        if (!SessionUtil.hasLogin(channel)) {
            return;
        }

        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        if (consoleCommand == null) {
            System.err.println("无法识别[" + command + "]指令，请重新输入!");
            return;
        }

        consoleCommand.exec(scanner, channel);

    }
}
