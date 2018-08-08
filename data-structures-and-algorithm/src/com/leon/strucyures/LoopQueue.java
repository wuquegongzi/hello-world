package com.leon.strucyures;

/**
 * 循环队列
 * @package: com.leon.strucyures
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/8/8 17:08
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class LoopQueue<E> implements Queue<E>{

    private E[] data;
    private int front,tail;
    private int size;

    public LoopQueue(int capacity){
        //循环队列，默认浪费一个空间
        data = (E[])new Object[capacity + 1];
        front = 0;
        tail = 0;
        size = 0;
    }

    public LoopQueue(){
        this(10);
    }

    /**
     * 获取容量
     * @return
     */
    public int getCapacity(){
       return data.length - 1;
    }

    @Override
    public void enqueue(E e) {
       if((tail + 1) % data.length == front){
           //扩容
           resize(getCapacity() * 2);
       }
       data[tail] = e;
       tail = (tail+1) % data.length;
       size ++;

    }


    @Override
    public E dequeue() {

        if(isEmpty()){
            try {
                throw new IllegalAccessException("Cnanot dequeue from empty queue.");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size --;

        //缩容
        if(size == getCapacity() / 4 && getCapacity() / 2 != 0){
               resize(getCapacity() / 2);
        }

        return ret;
    }

    /**
     * 获取对列 首个元素
     * @return
     */
    @Override
    public E getFront() {
        if(isEmpty()){
            try {
                throw new IllegalAccessException("Dequeue is empty.");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return data[front];
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        //当front == tail 时，说明队列为空
        return front == tail;
    }

    /**
     * 扩容 或者 缩减
     * @param newCapacity
     */
    private void resize(int newCapacity) {

        E[] newData = (E[])new Object[newCapacity + 1];
        for (int i = 0; i < size; i++) {
            //front 偏移  有可能超出 data的容量，所以要取余
            newData[i] = data[(i + front) % data.length];
        }
        data = newData;
        front = 0;
        tail = size;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append(String.format("Queue: size = %d , capacity = %d\n", size, getCapacity()));
        res.append("front [");
        for (int i = front; i !=tail ; i =(i + 1) % data.length ){
            res.append(data[i]);

            if((i+1) % data.length != tail){
                res.append(",");
            }
        }
        res.append("] tail");
        return res.toString();
    }

    public static void main(String[] args) {

        LoopQueue<Integer> queue = new LoopQueue<Integer>(20);

        for (int i=0 ; i < 150 ; i++){
            queue.enqueue(i);
            System.out.println(queue);
            if( i % 3 == 2){
                queue.dequeue();
                System.out.println(queue);
            }
        }

    }
}
