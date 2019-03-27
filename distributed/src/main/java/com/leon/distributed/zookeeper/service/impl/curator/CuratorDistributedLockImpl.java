package com.leon.distributed.zookeeper.service.impl.curator;

import com.leon.distributed.zookeeper.model.LockInfo;
import com.leon.distributed.zookeeper.service.DistributedLock;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 基于 curator 实现zookeeper分布式锁
 */
public class CuratorDistributedLockImpl implements DistributedLock {

    // 竞争的资源
    private LockInfo lockInfo;
    private CuratorFramework zookeeperClient;
    private InterProcessMutex lock;

    public CuratorDistributedLockImpl(LockInfo lockInfo) throws InterruptedException, IOException {
        this.lockInfo = lockInfo;

        zookeeperClient = ZookeeperCuratorClient.build();
        zookeeperClient.start();
        //Curator提供的InterProcessMutex是分布式锁的实现。通过acquire获得锁，并提供超时机制，release方法用于释放锁。
        lock = new InterProcessMutex(zookeeperClient, lockInfo.getLockname());
    }

    @Override
    public boolean lock() throws LockingException {

        try {
            if (lock.acquire(1, TimeUnit.SECONDS))
            {
                System.out.println(lockInfo.getLockOwner() + " has the lock:"+lockInfo.getLockname());
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean tryLock() throws KeeperException, InterruptedException {
        return false;
    }

    @Override
    public boolean unlock() throws LockingException {
        try {

            lock.release();

            return true;

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return false;
    }
}


