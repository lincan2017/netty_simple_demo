package netty;

import io.netty.util.AttributeKey;
import netty.session.Session;

/**
 * netty channel attr instance interface
 *
 * @author : Lin Can
 * @date : 2019/2/28 10:26
 */
public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
