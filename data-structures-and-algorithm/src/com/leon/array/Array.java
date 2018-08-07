package com.leon.array;

/**
 * @package: com.leon.array
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/8/7 13:11
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class Array<E> {

    /**
     * 元素组
     */
    private E[] data;
    /**
     * 元素个数
     */
    private int size;

    /**
     * 有参构造函数，传入容量
     * @param capacity 容量
     */
    public Array(int capacity){
        data = (E[])new Object[capacity];
        size = 0;
    }

    /**
     * 无参构造，默认容量为10
     */
    public Array(){
        this(10);
    }

    /**
     * 获取数组中的元素个数
     * @return
     */
    public int getSize(){
        return size;
    }

    /**
     * 获取数组的容量
     * @return
     */
    public int getCapacity(){
        return data.length;
    }

    /**
     * 判断数组是否为空
     * @return
     */
    public boolean isEmpty(){
        return size ==0;
    }

    /**
     * 在第index个位置插入一个新元素e
     * @param index
     * @param e
     */
    public void add(int index,E e){

        if(index <0 || index >size){
            try {
                throw new IllegalAccessException("Add faild. Require index >=0 and index <size");
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }

        if(size == data.length){
            //扩容
            resize(2*data.length);
        }

        for (int i=size - 1 ; i>= index ; i --){
            data[i + 1] = data[i];
        }

        data[index] = e;
        size++;
    }


    /**
     * 向所有元素后添加一个元素e
     * @param e
     */
    public void addLast(E e){
        add(size,e);
    }

    /**
     * 在第一个索引位置添加元素e
     * @param e
     */
    public void addFirst(E e){
        add(0,e);
    }


    /**
     * 是否包含元素e
     * @param e
     * @return
     */
    public boolean contains(E e){

        for (int i = 0; i<size; i++){
            if(data[i].equals(e)){
                return true;
            }
        }

        return false;
    }

    /**
     * 查找元素e的索引
     * @param e
     * @return
     */
    public int find(E e){

        for (int i = 0; i<size; i++){
            if(data[i].equals(e)){
                return i;
            }
        }

        return -1;
    }

    //TODO
   /* findAll(E e){

    }*/

    /**
     * 获取index索引位置的元素e
     * @param index
     * @return
     */
    public E get(int index){
        if(index < 0 || index >= size){
            try {
                throw new IllegalAccessException("Get faild.Index is illegal.");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return data[index];
    }

    /**
     * 修改 index 索引位置的元素值
     * @param index
     * @param e
     */
    public void set(int index,E e){
        if(index < 0 || index >= size){
            try {
                throw new IllegalAccessException("Set faild.Index is illegal.");
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
       data[index] = e;
    }

    /**
     * 删除index位置的元素，返回删除的元素
     * @param index
     * @return
     */
    public E remove(int index){
        if(index < 0 || index >= size){
            try {
                throw new IllegalAccessException("Remove faild.Index is illegal.");
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }

        E ret = data[index];
        for(int i = index+1 ; i< size; i++){
            data[i - 1] = data[i];
        }
        size -- ;
        //内存优化
        data[size] = null;
        //缩减容量  防止复杂度的震荡
        if(size == data.length / 4 && data.length / 2 != 0){
            resize(data.length / 2);
        }
        return ret;
    }

    /**
     * 删除首元素
     * @return
     */
    public E removeFirst(){
        return remove(0);
    }

    /**
     * 删除末尾元素
     * @return
     */
    public E removeLast(){
        return remove(size-1);
    }

    /**
     * 删除e元素
     * @param e
     * @return
     */
    public boolean removeElement(E e){
        int index = find(e);
        if(index != -1){
            remove(index);
            return true;
        }

        return false;
    }

    //TODO
   /* public boolean removeAllElement(int e){

    }*/

    @Override
    public String toString(){
       StringBuilder res = new StringBuilder();
       res.append(String.format("Array: size = %d , capacity = %d\n",size,data.length));
       res.append("[");
       for (int i =0 ; i < size ; i ++){
           res.append(data[i]);
           if(i != size -1){
               res.append(",");
           }
       }
       res.append("]");

       return res.toString();
    }

    /**
     * 动态数组扩容
     * @param i
     */
    private void resize(int newCapacity) {
        E[] newData = (E[])new Object[newCapacity];
        for (int i = 0 ; i < size ; i++){
            newData[i] = data[i];
        }
        data = newData;
    }

}
