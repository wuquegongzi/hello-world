import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.SetArgs.Builder;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 *
 * @author ml.c
 * @date 6:47 PM 5/6/21
 **/
public class LettuceTest {


    RedisClient redisClient;

    @BeforeEach
    public void getRedissonClient(){
        // <1> 创建单机连接的连接信息
        RedisURI redisUri = RedisURI.builder()
                .withHost("127.0.0.1")
                .withPort(6379)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();

        // <2> 创建客户端
        redisClient = RedisClient.create(redisUri);
    }

    @Test
    public void testSyncSetGet() throws Exception {
        StatefulRedisConnection<String, String> connection = null;

        try {
            // <3> 创建线程安全的连接
            connection = redisClient.connect();
            // <4> 创建同步命令
            RedisCommands<String, String> redisCommands = connection.sync();
            SetArgs setArgs = Builder.nx().ex(5);
            String result = redisCommands.set("name", "throwable", setArgs);
            System.out.println("set:"+result);
            result = redisCommands.get("name");
            System.out.println("get:"+result);
            // ... 其他操作
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // <5> 关闭连接
            connection.close();
        }

    }

    @AfterEach
    public void shutdown(){
        // <6> 关闭客户端
        redisClient.shutdown();
    }


}
