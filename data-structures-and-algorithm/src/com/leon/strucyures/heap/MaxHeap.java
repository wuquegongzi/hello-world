package com.leon.strucyures.heap;

import com.leon.strucyures.Array;

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


}
