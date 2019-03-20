package com.leon.distributed.zookeeper;

import java.util.concurrent.TimeUnit;

/**
 * DistributedLock
 */
public interface DistributedLock {

    /**
     * 上锁
     * @throws LockingException
     */
    boolean lock() throws LockingException;

    /**
     *  获取锁，直到超时
     * @param timeout  超时时间
     * @param unit  参数的单位
     * @return 是否获取到锁
     */
    boolean tryLock(long timeout, TimeUnit unit);

    /**
     * 释放锁
     * @throws LockingException
     */
    boolean unlock() throws LockingException;


    public static class LockingException extends RuntimeException {
        public LockingException(String msg, Exception e) {
            super(msg, e);
        }

        public LockingException(String msg) {
            super(msg);
        }
    }
}
