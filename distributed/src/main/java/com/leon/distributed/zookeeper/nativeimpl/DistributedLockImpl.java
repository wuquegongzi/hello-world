package com.leon.distributed.zookeeper.nativeimpl;

import com.leon.distributed.zookeeper.DistributedLock;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 基于JAVA原生API方式 实现zookeeper分布式锁
 */
public class DistributedLockImpl implements DistributedLock {

    private final ZooKeeper zookeeper;
    private final String lockPath;
    private String lockID;//记录锁节点ID
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static int sessionTimeout = 5000;

    public DistributedLockImpl(String lockPath){
        this.lockPath = lockPath;
        this.zookeeper = ZooKeeperClient.getInstance();
    }

    @Override
    public  boolean lock() throws LockingException {

        try {
            //持久化节点（PERSISTENT_SEQUENTIAL）、临时顺序节点（EPHEMERAL_SEQUENTIAL）。
            lockID = zookeeper.create(lockPath,
                    "".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);

            System.out.println(Thread.currentThread().getName()+"-->成功创建了lock节点,节点ID="+lockID+"开始去竞争锁");

            //获取当前根节点下所有的节点,然后判断是不是最小节点
            List<String> childrenNodes = zookeeper.getChildren(lockPath,true);

            SortedSet<String> sortedSet = new TreeSet<String>();
            for (String children : childrenNodes){
                System.out.println("children:"+children);
                sortedSet.add(lockPath.concat("/").concat(children));
            }

            //拿到最小的节点
            String first = sortedSet.first();
            if(lockID.equals(first)){
                //表示当前就是最小的节点
                System.out.println(Thread.currentThread().getName()+"---->成功的获取锁,lock节点为="+lockID);
                return true;

            }

           /* //拿到这个节点之前的所有节点,再拿最后一个节点，就是拿当前节点的上一个节点,用于监听变化
            SortedSet<String> lessThanLockID = sortedSet.headSet(lockID);
            if (!lessThanLockID.isEmpty()){
                String prevLockID = lessThanLockID.last();

                zookeeper.exists(prevLockID,new LockWatcher(countDownLatch));
                countDownLatch.await(sessionTimeout, TimeUnit.MILLISECONDS);
                //上面这段代码意味着会话超时或者节点被删除（释放）了
                System.out.println(Thread.currentThread().getName()+"成功获取锁,lockID="+lockID);
            }
            return true;*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) {
        return false;
    }

    @Override
    public boolean unlock() throws LockingException {

        try {
            System.out.println(Thread.currentThread().getName()+"--->开始释放锁lock="+lockID);
            zookeeper.delete(lockID,-1);
            System.out.println("节点"+lockID+"成功被删除");
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(10);
        Random random = new Random();

        for (int i=0 ;i < 10;i++){

            new Thread(()->{
                DistributedLock lock = null;
                try {

                    lock = new DistributedLockImpl("/locks");
                    latch.countDown();
                    latch.await();
                    lock.lock();
                    Thread.sleep(random.nextInt(6000));

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (lock != null){
                        lock.unlock();
                    }
                }
            }).start();
        }

    }

}


/**
 * 监听节点被删除事件
 */
class LockWatcher implements Watcher {

    private CountDownLatch countDownLatch;

    public LockWatcher(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void process(WatchedEvent event) {
        //判断是不是节点删除了
        if(event.getType()== Event.EventType.NodeDeleted){
            countDownLatch.countDown();
        }
    }
}


