package com.leon.distributed.zookeeper.model;

public class LockInfo {

    // 锁的名字，体现为zookeeper上的节点名
    String lockname;
    // 锁的持有者，体现为zookeeper锁节点的数据
    String lockOwner;

    public LockInfo(String lockname, String lockOwner) {
        this.lockname = lockname;
        this.lockOwner = lockOwner;
    }

    public String getLockname() {
        return lockname;
    }

    public void setLockname(String lockname) {
        this.lockname = lockname;
    }

    public String getLockOwner() {
        return lockOwner;
    }

    public void setLockOwner(String lockOwner) {
        this.lockOwner = lockOwner;
    }
}
