package com.leon.strucyures;

/**
 * 数组队列
 * @package: com.leon.strucyures
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/8/8 16:37
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class ArrayQueue<E> implements Queue<E> {

    private Array<E> array;

    public ArrayQueue(int capacity){
        array = new Array<>(capacity);
    }

    public ArrayQueue(){
        array = new Array();
    }

    /**
     * 入队
     * @param e
     */
    @Override
    public void enqueue(E e) {
        array.addLast(e);
    }

    /**
     * 出队
     * @return
     */
    @Override
    public E dequeue() {
        return array.removeFirst();
    }

    /**
     * 获取队首
     * @return
     */
    @Override
    public E getFront() {
        return array.getFirst();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    public int getCapacity(){
        return array.getCapacity();
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("Queue :");
        res.append("front [");
        for (int i = 0 ; i < array.getSize() ; i++){
            res.append(array.get(i));

            if(i != array.getSize() - 1){
                res.append(",");
            }
        }
        res.append("] tail");
        return res.toString();
    }


    public static void main(String[] args) {
        ArrayQueue<Integer> queue = new ArrayQueue<>();

        for (int i=0 ; i < 10 ; i++){
            queue.enqueue(i);
            System.out.println(queue);
            if( i % 3 == 2){
                queue.dequeue();
                System.out.println(queue);
            }
        }

    }

}
