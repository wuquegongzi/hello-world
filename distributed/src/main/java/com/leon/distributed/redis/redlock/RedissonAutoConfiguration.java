package com.leon.distributed.redis.redlock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


@Configuration
@ConditionalOnClass(Config.class)
public class RedissonAutoConfiguration {

    @Bean
    public  RedissonClient getRedisson() {

        //支持单机，主从，哨兵，集群等模式
        Config config = new Config();
//        config.useClusterServers()
//                .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
//                //可以用"rediss://"来启用SSL连接
//                .addNodeAddress("redis://127.0.0.1:6379", "redis://127.0.0.1:6378")
//                .addNodeAddress("redis://127.0.0.1:6377");
////                .setPassword("")

        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6377")
                .setTimeout(2000)
                .setConnectionPoolSize(50)
                .setConnectionMinimumIdleSize(10);

        RedissonClient redisson = Redisson.create(config);

        try {
            System.out.println("检测是否配置完成:"+redisson.getConfig().toJSON().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return redisson;
    }
}
