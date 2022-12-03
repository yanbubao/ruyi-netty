package com.example.im.attribute;

import com.example.im.session.Session;
import io.netty.util.AttributeKey;

/**
 * @Author yanzx
 * @Date 2022/12/3 15:41
 */
public interface Attributes {

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
