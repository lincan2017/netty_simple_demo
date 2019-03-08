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

    /**
     * 客户端->服务端的消息指令
     */
    byte MESSAGE_REQUEST_COMMAND = 3;

    /**
     * 服务端->客户端的消息指令
     */
    byte MESSAGE_RESPONSE_COMMAND = 4;

    /**
     * 创建群聊请求指令
     */
    byte CREATE_GROUP_REQUEST_COMMAND = 5;

    /**
     * 创建群聊响应指令
     */
    byte CREATE_GROUP_RESPONSE_COMMAND = 6;

    /**
     * 登出请求指令
     */
    byte LOGOUT_REQUEST_COMMAND = 7;

    /**
     *  登出响应指令
     */
    byte LOGOUT_RESPONSE_COMMAND = 8;
}
