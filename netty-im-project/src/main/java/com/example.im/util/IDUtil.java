package com.example.im.util;

import java.util.UUID;

/**
 * @Author yanzx
 * @Date 2022/12/1 22:10
 */
public class IDUtil {

    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
