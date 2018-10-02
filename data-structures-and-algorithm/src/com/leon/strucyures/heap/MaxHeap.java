package com.leon.strucyures.heap;

import com.leon.strucyures.Array;

import java.util.Random;

/**
 * 最大堆
 * 使用数组存储二叉堆
 *
 * 注：二叉堆的性质：堆中某个节点的值总是小于其父节点的值
 * @param <E>
 */
public class MaxHeap<E extends Comparable<E>> {

    private Array<E> data;

    public MaxHeap(int capacity){
        data = new Array<>(capacity);
    }

    public MaxHeap(){
       data = new Array<>();
    }

    public int size(){
        return data.getSize();
    }

    public boolean isEmpty(){
        return data.isEmpty();
    }

    /**
     * 返回完全二叉树的数组表示中，一个索引所展示的元素的父节点的索引
     * @param index
     * @return
     */
    private int parent(int index){
        if(index == 0){
            try {
                throw new IllegalAccessException("index-0 doesn't have parent.");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return (index -1 ) / 2;
    }

    /**
     *返回完全二叉树的数组表示中，一个索引所展示的元素的左子节点的索引
     * @param index
     * @return
     */
    private int leftChild(int index){

        return index * 2 + 1;
    }

    /**
     *返回完全二叉树的数组表示中，一个索引所展示的元素的右子节点的索引
     * @param index
     * @return
     */
    private int rightChild(int index){

        return index * 2 + 2;
    }

    /**
     * 向堆中添加元素
     * @param e
     */
    public void add(E e){
        data.addLast(e);
        siftUp(data.getSize() -1);
    }

    /**
     * 上浮
     * @param k
     */
    private void siftUp(int k) {

        //与父节点比较大小
        while(k > 0 && data.get(parent(k)).compareTo(data.get(k)) < 0){
            data.swap(k, parent(k));
            k = parent(k);
        }
    }

    /**
     * 获取堆中最大元素
     * @return
     */
    public E findMax(){
        if(data.getSize() == 0){
            try {
                throw new IllegalAccessException("Can not findMax when heap is empty!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return data.get(0);
    }

    /**
     * 取出堆中最大元素
     * @return
     */
    public E extractMax(){

        E ret = findMax();
        //与最后子节点交换位置
        data.swap(0,data.getSize() - 1);
        data.removeLast();
        siftDown(0);

        return ret;
    }

    /**
     * 下沉
     * @param k
     */
    private void siftDown(int k) {

        while(leftChild(k) < data.getSize()){

            int j = leftChild(k);

            //  判断右孩子
            if(j + 1 <data.getSize() &&
                    data.get(j + 1).compareTo(data.get(j)) > 0){
               j = rightChild(k);
            }

            //此时data[j] 是 leftChild 和 rightChild 中最大值
            if(data.get(k).compareTo(data.get(j)) >= 0){
                break;
            }

            data.swap(k, j);
            k = j;

        }
    }


    public static void main(String[] args) throws IllegalAccessException {
        int n = 1000000;
        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            maxHeap.add(random.nextInt(Integer.MAX_VALUE));
//            System.out.println(maxHeap.size());
        }

        int[] arr = new int[n];
        for (int i = 0 ; i < n; i ++){
            arr[i] = maxHeap.extractMax();
//            System.out.println(arr[i]);
        }

        for(int i = 1 ;i < n; i++){
            if(arr[i-1] < arr[i]){
                throw  new IllegalAccessException("Error");
            }
        }

        System.out.println("Test MaxHeap completed.");
    }

}
