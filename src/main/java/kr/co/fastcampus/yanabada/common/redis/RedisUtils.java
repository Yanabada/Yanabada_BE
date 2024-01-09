package kr.co.fastcampus.yanabada.common.redis;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.Map;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils<T> {

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOperations;
    private final Jackson2HashMapper mapper = new Jackson2HashMapper(true);


    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void setData(String key, String value, Long expiredTime) {
        redisTemplate.opsForValue().set(key, value, expiredTime, MILLISECONDS);
    }

    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void setDataAsHash(String key, T objValue, Long expiredTime) {
        Map<String, Object> mappedHash = mapper.toHash(objValue);
        hashOperations.putAll(key, mappedHash);
        redisTemplate.expire(key, expiredTime, MILLISECONDS);
    }

    public T getDataAsHash(String key) {
        Map<String, Object> loadedHash = hashOperations.entries(key);
        if (loadedHash.isEmpty()) {
            return null;
        }
        return (T) mapper.fromHash(loadedHash);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

}