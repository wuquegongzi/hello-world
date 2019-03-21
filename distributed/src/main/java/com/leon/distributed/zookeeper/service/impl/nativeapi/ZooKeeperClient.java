package com.leon.distributed.zookeeper.service.impl.nativeapi;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * java 原生api实现 zookeeper客户端
 */
public class ZooKeeperClient {

    private static ZooKeeper zookeeper;

    private static final String connectString ="127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static int sessionTimeout = 5000;

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
                        zookeeper = new ZooKeeper(connectString ,sessionTimeout,new Watcher(){

                            @Override
                            public void process(WatchedEvent watchedEvent) {
                                System.out.println("Receive watched event: "+watchedEvent.getState());

                                if(Event.KeeperState.SyncConnected == watchedEvent.getState()){
                                    countDownLatch.countDown();
                                }
                            }
                        });

                        //获取一下连接状态
                        System.out.println(zookeeper.getState());

                        countDownLatch.await();

                        //连接已建立
                        System.out.println("ZooKeeper client 连接已经建立......");

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

//        System.out.println("获取zookeeper连接实例："+zookeeper);
        return zookeeper;
    }

}
