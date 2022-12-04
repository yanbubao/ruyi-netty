package com.example.im.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.time.LocalDateTime;

/**
 * @Author yanzx
 * @Date 2022/12/4 15:23
 */
public class TimeUtil {

    public static String timeString() {
        return DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATETIME_PATTERN);
    }
}
