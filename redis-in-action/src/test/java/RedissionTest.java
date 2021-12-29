import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLongAsync;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * test
 *
 * @author ml.c
 * @date 11:55 PM 5/5/21
 **/
public class RedissionTest {

    RedissonClient redisson;

    @BeforeEach
    public void getRedissonClient(){

        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        redisson = Redisson.create(config);
    }


    /**
     * 异步执行方式
     */
    @Test
    public void test(){
        RAtomicLongAsync longObject = redisson.getAtomicLong("myLong");
        RFuture<Boolean> future = longObject.compareAndSetAsync(1, 401);
        future.whenComplete((res, exception) -> {
            System.out.println(res);
            System.out.println(exception.getMessage());
        });

    }


    @Test
    public void getLock(){
        RLock lock = redisson.getLock("myLock");
        System.out.println(lock.getName());
        try {
            lock.tryLock(50000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true){
            if(lock.isLocked()){
                System.out.println(lock.getHoldCount());
            }else {
                break;
            }
        }
        System.out.println(lock.isLocked());
    }

}
