package com.leon;

import com.leon.strucyures.Array;
import com.leon.strucyures.ArrayStack;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        System.out.println("欢迎来到leon的数据结构世界~ \n ");
    }


    public static LinkedBlockingQueue<Map> queue;

    static {
        if(null == queue || queue.isEmpty()){
            queue=new LinkedBlockingQueue<Map>(5000);
        }

        Producer t1 = new Producer();
        Consumer t2 = new Consumer();
        t1.start();
        t2.start();
    }

}

//队列消费
class Consumer extends Thread{
    @Override
    public void run() {
        while(true){
            try {
                Map map = Main.queue.take();
                System.out.println(new Date()+"任务消费: "+map.toString());
            } catch (InterruptedException e1) {
                System.out.println("任务消费异常");
                return;
            }
        }
    }
}


class Producer extends Thread{
    @Override
    public void run() {
        int i=0;
        while(i<1000000){
            try {
                Map map = new HashMap();
                map.put(i,i);
                System.out.println(new Date()+"入队: "+i);
                Main.queue.put(map);
            } catch (InterruptedException e1) {
                System.out.println("入队异常.");
                return;
            }
            i++;
        }
    }
}
