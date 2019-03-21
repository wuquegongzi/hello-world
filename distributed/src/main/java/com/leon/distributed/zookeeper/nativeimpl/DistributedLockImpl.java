package com.leon.distributed.zookeeper.nativeimpl;

import com.leon.distributed.zookeeper.DistributedLock;
import com.leon.distributed.zookeeper.LockInfo;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    // 竞争的资源
    private LockInfo lockInfo;

    public DistributedLockImpl(LockInfo lockInfo) throws InterruptedException, IOException {
        this.lockInfo = lockInfo;
        this.zookeeper = ZooKeeperClient.getInstance();
    }


    @Override
    public  synchronized  boolean lock() throws LockingException {

        boolean isLock = tryLock();
        if(!isLock){

            CountDownLatch releaseSignal = new CountDownLatch(1);
            try {
                zookeeper.exists(lockInfo.getLockname(), new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        if (lockInfo.getLockname().equals(watchedEvent.getPath())
                                && Event.EventType.NodeDeleted.equals(watchedEvent.getType())) {
                            releaseSignal.countDown();
                        }
                    }
                });

                releaseSignal.await(60000,TimeUnit.MILLISECONDS);

                isLock = lock(); // 递归调用自己,默认一定会获取到锁
//                isLock = tryLock(); //再尝试一次
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return isLock;
    }

    @Override
    public boolean tryLock(){

        try {
            if(zookeeper.exists(lockInfo.getLockname(),false) != null){
                String currentOwner = new String(zookeeper.getData(lockInfo.getLockname(), false, null));
                System.out.println(lockInfo.getLockname()+"已经被加上锁了，锁的持有者是：" + currentOwner);
                return false;
            } else {
                //持久化节点（PERSISTENT_SEQUENTIAL）、临时顺序节点（EPHEMERAL_SEQUENTIAL）。
                zookeeper.create(lockInfo.getLockname(), lockInfo.getLockOwner().getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

                System.out.println(Thread.currentThread().getName()+"-->成功创建了lock节点,节点ID="+lockInfo.getLockname()+"开始去竞争锁");

                //获取当前根节点下所有的节点,然后判断是不是最小节点
                List<String> childrenNodes = zookeeper.getChildren(lockInfo.getLockname(),true);

                SortedSet<String> sortedSet = new TreeSet<String>();
                for (String children : childrenNodes){
                    System.out.println("子节点-children:"+children);
                    sortedSet.add(lockInfo.getLockname().concat("/").concat(children));
                }

                //拿到最小的节点
                if(!sortedSet.isEmpty() && lockInfo.getLockname().equals(sortedSet.first())){
                    //表示当前就是最小的节点
                    System.out.println(Thread.currentThread().getName()+"拿到最小的节点---->成功的获取锁,lock节点为="+lockInfo.getLockname());
                    return true;
                }

                // 若不是最小节点，则找到自己的前一个节点
                //拿到这个节点之前的所有节点,再拿最后一个节点，就是拿当前节点的上一个节点,用于监听变化
                SortedSet<String> lessThanLockID = sortedSet.headSet(lockInfo.getLockname());
                if (!lessThanLockID.isEmpty()){
                    String prevLockID = lessThanLockID.last();

                    CountDownLatch countDownLatch = new CountDownLatch(1);
                    zookeeper.exists(prevLockID,new Watcher(){

                        @Override
                        public void process(WatchedEvent watchedEvent) {
                            //判断是不是节点删除了
                            if(watchedEvent.getType()== Event.EventType.NodeDeleted){
                                countDownLatch.countDown();
                            }
                        }
                    });
                    countDownLatch.await(6000, TimeUnit.MILLISECONDS);
                    //上面这段代码意味着会话超时或者节点被删除（释放）了
                    //再次调用getChildren("/root/lock_",false)来确保自己是最小的节点
                    childrenNodes = zookeeper.getChildren(lockInfo.getLockname(),false);

                    sortedSet = new TreeSet<String>();
                    for (String children : childrenNodes){
                        System.out.println("子节点-children:"+children);
                        sortedSet.add(lockInfo.getLockname().concat("/").concat(children));
                    }

                    //拿到最小的节点
                    if(!sortedSet.isEmpty() && lockInfo.getLockname().equals(sortedSet.first())){
                        //表示当前就是最小的节点
                        System.out.println(Thread.currentThread().getName()+"拿到最小的节点2---->成功的获取锁,lock节点为="+lockInfo.getLockname());
                        return true;
                    }

                }

                return true;
            }
        } catch (KeeperException e) {
//            e.printStackTrace();
            tryLock();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("####################3333333333####################");
        }

        return false;
    }

    @Override
    public boolean unlock() throws LockingException {

        try {
            System.out.println(lockInfo.getLockOwner()+"--->开始释放锁lock="+lockInfo.getLockname());
            if(zookeeper.exists(lockInfo.getLockname(),false) != null){
                String existOwner = new String(zookeeper.getData(lockInfo.getLockname(), null, null), "UTF-8");
                if (lockInfo.getLockOwner().equals(existOwner)) {
                    zookeeper.delete(lockInfo.getLockname(), -1);
                    System.out.println(lockInfo.getLockOwner() + ": 解锁成功,节点:"+lockInfo.getLockname());
                    return true;
                } else {
                    System.out.println(lockInfo.getLockOwner()+ ":无法释放锁，因为没有获得锁");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args){

        Runnable runnable = new Runnable() {
            public void run() {
                boolean isLock = false;
                DistributedLock lock = null;
                try {
                    LockInfo lockInfo = new LockInfo("/zk-lock-"+new Random().nextInt(10), "person"+ System.currentTimeMillis()+new Random().nextInt(100));
                    lock = new DistributedLockImpl( lockInfo);

                    isLock = lock.lock();
                    System.out.println(lockInfo.getLockOwner() + "获取锁结果："+isLock);

                    if(isLock){
                        //TODO 获取到锁，处理相关业务
                        System.out.println(" 获取到锁，处理相关业务"+lockInfo.getLockOwner());
                    }
                    Thread.sleep(1000);

                }catch (Exception e){
                    e.printStackTrace();
                }finally{
                    if (lock != null &&  isLock) {
                        lock.unlock();
                    }
                }
            }
        };

        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }

    }

}


