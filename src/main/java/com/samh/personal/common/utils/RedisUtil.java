package com.samh.personal.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description redis操作类
 * @Author WANKAI
 * @Date 2020/7/23 4:41 下午
 */
@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // Key（键），简单的key-value操作

    /**
     * 实现命令：TTL key，以秒为单位，返回给定 key的剩余生存时间(TTL, time to live)。
     *
     * @param key
     * @return
     */
    public long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 实现命令：expire 设置过期时间，单位秒
     *
     * @param key
     * @return
     */
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 实现命令：INCR key，增加key一次
     *
     * @param key
     * @return
     */
    public long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 实现命令：KEYS pattern，查找所有符合给定模式 pattern的 key
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 实现命令：DEL key，删除一个key
     *
     * @param key
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }

    // String（字符串）

    /**
     * 实现命令：SET key value，设置一个key-value（将字符串值 value关联到 key）
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 实现命令：SET key value EX seconds，设置key-value和超时时间（秒）
     *
     * @param key
     * @param value
     * @param timeout （以秒为单位）
     */
    public void set(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 实现命令：GET key，返回 key所关联的字符串值。
     *
     * @param key
     * @return value
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    // Hash（哈希表）

    /**
     * 实现命令：HSET key field value，将哈希表 key中的域 field的值设为 value
     *
     * @param key
     * @param field
     * @param value
     */
    public void hset(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 实现命令：HGET key field，返回哈希表 key中给定域 field的值
     *
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 实现命令：HDEL key field [field ...]，删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
     *
     * @param key
     * @param fields
     */
    public void hdel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 实现命令：HGETALL key，返回哈希表 key中，所有的域和值。
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hgetall(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    // List（列表）

    /**
     * 实现命令：LPUSH key value，将一个值 value插入到列表 key的表头
     *
     * @param key
     * @param value
     * @return 执行 LPUSH命令后，列表的长度。
     */
    public long lpush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 实现命令：LPOP key，移除并返回列表 key的头元素。
     *
     * @param key
     * @return 列表key的头元素。
     */
    public String lpop(String key) {
        return (String) redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 实现命令：RPUSH key value，将一个值 value插入到列表 key的表尾(最右边)。
     *
     * @param key
     * @param value
     * @return 执行 LPUSH命令后，列表的长度。
     */
    public long rpush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public Integer getRemainSecondsToday() {
        Date currentDate = new Date();
        Calendar midnight = Calendar.getInstance();
        midnight.setTime(currentDate);
        midnight.add(midnight.DAY_OF_MONTH, 1);//将日加1
        midnight.set(midnight.HOUR_OF_DAY, 0);//控制时
        midnight.set(midnight.MINUTE, 0);//控制分
        midnight.set(midnight.SECOND, 0);//控制秒
        midnight.set(midnight.MILLISECOND, 0);//毫秒
        //通过以上操作就得到了一个currentDate明天的0时0分0秒的Calendar对象，然后相减即可得到到明天0时0点0分0秒的时间差
        return (Integer) (int) ((midnight.getTime().getTime() - currentDate.getTime()) / 1000);
    }

    //REDIS限流
    public boolean incrAndCheck(String queue_name, long queue_size, long millisecond) {
        queue_name = "personal:limit:" + queue_name;
        //获取队列容量
        Long size = redisTemplate.opsForList().size(queue_name);
        long currentTimeMillis = System.currentTimeMillis();
        if (size < queue_size) {
            //若容量还没到上限则将当前时间戳放入队列左侧
            redisTemplate.opsForList().leftPush(queue_name, currentTimeMillis + "");
            // log.info("success");
            return true;
        } else {
            //若队列达到上限则获取队列右侧最早的时间戳
            long start = Long.parseLong(redisTemplate.opsForList().index(queue_name, queue_size - 1));
            //小于指定时长则说明触发限流
            if ((currentTimeMillis - start) < millisecond) {
                return false;
            } else {
                //大于指定时长则放入元素并截取最新的n条
                redisTemplate.opsForList().leftPush(queue_name, currentTimeMillis + "");
                redisTemplate.opsForList().trim(queue_name, 0, queue_size - 1);
                // log.info("success");
                return true;
            }
        }
    }
}
