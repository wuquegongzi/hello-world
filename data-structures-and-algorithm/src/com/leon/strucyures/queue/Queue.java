package com.leon.strucyures.queue;

/**
 * 队列接口
 * @package: com.leon.strucyures
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/8/8 16:33
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public interface Queue<E> {

    /**
     * 入队
     * @param e
     */
    void enqueue(E e);

    /**
     * 出队
     * @return
     */
    E dequeue();

    /**
     * 获取队首
     * @return
     */
    E getFront();

    /**
     * 获取队列大小
     * @return
     */
    int getSize();

    /**
     * 判断队列是否为空
     * @return
     */
    boolean isEmpty();
}
