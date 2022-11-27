package com.example.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author yanzx
 * @Date 2022/11/26 16:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response implements Serializable {
    private Object data;
    private String message;
}
