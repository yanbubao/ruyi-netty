package com.example.im.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yanzx
 * @Date 2022/12/3 15:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    /**
     * 用户唯一标识
     */
    private String userId;

    private String userName;

    @Override
    public String toString() {
        return userId + ":" + userName;
    }
}
