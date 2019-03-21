package com.leon.distributed.zookeeper;

import com.leon.distributed.zookeeper.model.LockInfo;
import com.leon.distributed.zookeeper.service.impl.nativeapi.DistributedLockImpl;
import com.leon.distributed.zookeeper.service.DistributedLock;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class TestRunnable implements Runnable {

    /** 处理main线程阻塞（等待所有子线程） */
    private CountDownLatch countDown;

    /** 线程名字 */
    private String  threadName;


    public TestRunnable(CountDownLatch countDownLatch, String threadName) {
        this.countDown = countDownLatch;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println( "[" + threadName + "] Running ! [countDownLatch.getCount() = " + countDown.getCount() + "]." );

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
            Thread.sleep(200);  //就按照每个业务处理平均200ms完成

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if (lock != null &&  isLock) {
                lock.unlock();
            }
            // 每个独立子线程执行完后,countDownLatch值减1
            countDown.countDown();
        }

    }

    public static void main(String [] args) throws InterruptedException {

        int countNum = 100;
        CountDownLatch countDownLatch = new CountDownLatch(countNum);

        long begin = System.currentTimeMillis();

        for (int i = 0; i < countNum; i++) {

            new Thread(new TestRunnable(countDownLatch,"子线程" + (i+100))).start();
        }

        System.out.println("主线程阻塞,等待所有子线程执行完成");
        countDownLatch.await();
        System.out.println("所有线程执行完成!");
        long end = System.currentTimeMillis();
        System.out.println("----------所有线程执行完成!------时间花费："+(end -begin)/1000 +"s");
    }
}
