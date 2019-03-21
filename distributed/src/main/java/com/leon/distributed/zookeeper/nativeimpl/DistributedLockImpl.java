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

/**
 * 基于JAVA原生API方式 实现zookeeper分布式锁
 */
public class DistributedLockImpl implements DistributedLock {

    private final ZooKeeper zookeeper;
    private LockInfo lockInfo;
    private String lockID;

    public DistributedLockImpl(LockInfo lockInfo) throws InterruptedException, IOException {
        this.lockInfo = lockInfo;
        this.zookeeper = ZooKeeperClient.getInstance();
    }


    @Override
    public  boolean lock() throws LockingException {

        if(!tryLock()){

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

                releaseSignal.await();

                // 递归调用自己
                lock();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public boolean tryLock(){

        try {
            if(zookeeper.exists(lockInfo.getLockname(),false) != null){
                String currentOwner = new String(zookeeper.getData(lockInfo.getLockname(), false, null));
                System.out.println("已经被加上锁了，锁的持有者是：" + currentOwner);
                return false;
            } else {
                //持久化节点（PERSISTENT_SEQUENTIAL）、临时顺序节点（EPHEMERAL_SEQUENTIAL）。
                lockID = zookeeper.create(lockInfo.getLockname(), lockInfo.getLockOwner().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL);

                System.out.println(lockInfo.getLockOwner() +"-->加锁成功,节点ID="+lockID);

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
            System.out.println(lockInfo.getLockOwner()+"--->开始释放锁lock="+lockID);
            if(zookeeper.exists(lockInfo.getLockname(),false) != null){
                String existOwner = new String(zookeeper.getData(lockInfo.getLockname(), null, null), "UTF-8");
                if (lockInfo.getLockOwner().equals(existOwner)) {
                    zookeeper.delete(lockInfo.getLockname(), -1);
                    System.out.println(lockInfo.getLockOwner() + ": 解锁成功,节点:"+lockID);
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
                DistributedLock lock = null;
                try {
                    LockInfo lockInfo = new LockInfo("/zk-lock", "person"+ System.currentTimeMillis()+new Random().nextInt(10));
                    lock = new DistributedLockImpl( lockInfo);

                    lock.lock();
                    System.out.println(lockInfo.getLockOwner() + "正在运行");
                    Thread.sleep(6000);

                }catch (Exception e){
                    e.printStackTrace();
                }finally{
                    if (lock != null) {
                        lock.unlock();
                    }
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }

    }

}


