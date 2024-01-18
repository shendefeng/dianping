package com.hmdp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTests {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    void testString(){
        // 写入一条 string 数据
//        stringRedisTemplate.opsForValue().set("name", "heima");
        // 获取string数据
        String name = stringRedisTemplate.opsForValue().get("name");
        System.out.println("name = " + name);
//        stringRedisTemplate.opsForValue().getOperations().delete(name);
        // 删除key
//        System.out.println("name = " + name);

    }
}
