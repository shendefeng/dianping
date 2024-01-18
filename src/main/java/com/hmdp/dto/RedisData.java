package com.hmdp.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 逻辑删除的传输类
 * @param <T>
 */
@Data
public class RedisData<T> {
    private LocalDateTime expireTime;
    private T data;
}
