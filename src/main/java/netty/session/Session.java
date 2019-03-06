package netty.session;

import lombok.Data;

/**
 * 用户的 session ： 存放用户的登录信息
 *
 * @author : Lin Can
 * @date : 2019/3/6 9:29
 */

@Data
public class Session {
    private Long userId;
    private String username;

    public Session(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    @Override
    public String toString() {
        return "Session{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }
}
