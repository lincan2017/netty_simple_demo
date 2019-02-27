package netty.protocol.command;

/**
 * 指令接口
 * @author : Lin Can
 * @date : 2019/2/27 9:29
 */
public interface Command {

    /**
     * 登录请求的指令
     */
    byte LOGIN_REQUEST_COMMAND = 1;

    /**
     * 登录响应的指令
     */
    byte LOGIN_RESPONSE_COMMAND = 2;
}
