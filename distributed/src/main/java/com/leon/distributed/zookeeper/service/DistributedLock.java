package com.leon.distributed.zookeeper.service;

import org.apache.zookeeper.KeeperException;

import java.util.concurrent.TimeUnit;

/**
 * DistributedLock
 */
public interface DistributedLock {

    /**
     * 同步加锁
     * @throws LockingException
     */
    boolean lock() throws LockingException;

    /**
     *  尝试加锁
     */
    boolean tryLock() throws KeeperException, InterruptedException;

    /**
     * 释放锁
     * @throws LockingException
     */
    boolean unlock() throws LockingException;


   class LockingException extends RuntimeException {
        public LockingException(String msg, Exception e) {
            super(msg, e);
        }

        public LockingException(String msg) {
            super(msg);
        }
    }
}
