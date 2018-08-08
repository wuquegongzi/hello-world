package com.leon.strucyures;

/**
 * 栈接口
 * @package: com.leon.strucyures
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/8/7 20:12
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public interface Stack<E> {


    /**
     * 添加
     * @param e
     */
    void push(E e);

    /**
     * 取出
     * @return
     */
    E pop();

    /**
     * 栈顶元素
     * @return
     */
    E peek();

    /**
     * 获取大小
     * @return
     */
    int getSize();

    /**
     * 判断非空
     * @return
     */
    boolean isEmpty();

}
