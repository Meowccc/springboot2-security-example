package com.example.demo.service.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author meow
 */
@Component
@RequiredArgsConstructor
public class CacheService<T> {

    private final RedisTemplate<String, Object> redisTemplate;

    public Object getByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setByKey(String key, Object obj, long ttl) {
        redisTemplate.opsForValue().set(key, obj, ttl);
    }

    public void setByKey(String key, Object obj) {
        redisTemplate.opsForValue().set(key, obj);
    }
}
