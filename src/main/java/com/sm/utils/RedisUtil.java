package com.sm.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * Created by Victor on 2017/6/18.
 */
@Component
public class RedisUtil{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
   // @Autowired
   // RedisTemplate redisTemplate;

    public void set(String key, String value) throws Exception {
        stringRedisTemplate.opsForValue().set(key,value);
    }

    public  String get(String key) throws Exception  {
       return  stringRedisTemplate.opsForValue().get(key);
    }

    public  boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public void delete(String key) {
         stringRedisTemplate.delete(key);
    }


}
