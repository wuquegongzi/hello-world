package com.leon.distributed.zookeeper.nativeimpl;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * java 原生api实现 zookeeper客户端
 */
public class ZooKeeperClient {

    private final static String ZOOKEEPER_CONNECTIONS="127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private static int sessionTimeout = 5000;


    private static ZooKeeper zookeeper;

    /**
     * 获取单例实例
     * @return
     */
    public static ZooKeeper getInstance(){

        if (null == zookeeper){
            synchronized (ZooKeeperClient.class){
                if(null == zookeeper){
                    //连接zookeeper server，创建会话的时候，是异步去进行的
                    try {
                        CountDownLatch countDownLatch = new CountDownLatch(1);

                        zookeeper = new ZooKeeper(ZOOKEEPER_CONNECTIONS,sessionTimeout,new ZooKeeperWatcher(countDownLatch));

                        //获取一下连接状态
                        System.out.println(zookeeper.getState());

                        countDownLatch.await();

                        //连接已建立
                        System.out.println("ZooKeeper client established......");

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println("获取zookeeper连接实例："+zookeeper);
        return zookeeper;
    }

}

/**
 * 建立zk client的watcher
 * 监听连接状态
 */
class ZooKeeperWatcher implements Watcher {

    private CountDownLatch countDownLatch;

    public ZooKeeperWatcher(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("Receive watched event: "+watchedEvent.getState());

        if(Event.KeeperState.SyncConnected == watchedEvent.getState()){
            this.countDownLatch.countDown();
        }
    }
}