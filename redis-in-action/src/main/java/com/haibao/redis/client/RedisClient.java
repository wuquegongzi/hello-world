package com.haibao.redis.client;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * redis
 *
 * @author ml.c
 * @date 11:34 PM 5/5/21
 **/
public class RedisClient {


    public RedissonClient getRedissonClient(){

        //todo 需要读取配置
        //todo 策略模式 区分集群、单例

        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        RedissonClient redisson = Redisson.create(config);

        return redisson;
    }

}
