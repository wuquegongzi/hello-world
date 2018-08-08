package com.leon.strucyures;

/**
 * 数组栈
 * @package: com.leon.strucyures
 * @author: 陈明磊<minglei.chen @ gm-medicare.com>
 * @date: 2018/8/7 20:17
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class ArrayStack<E> implements Stack<E> {

    Array<E> array;

    public  ArrayStack(int capacity){
        array = new Array<>(capacity);
    }

    public ArrayStack(){
        array = new Array<>();
    }


    /**
     * 添加元素 e
     * @param e
     */
    @Override
    public void push(E e) {
       array.addLast(e);
    }

    /**
     * 取出栈顶元素
     * @return
     */
    @Override
    public E pop() {
        return array.removeLast();
    }

    /**
     * 查看栈顶元素
     * @return
     */
    @Override
    public E peek() {
        return array.getLast();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("Stack :");
        res.append("[");
        for (int i = 0 ; i < array.getSize() ; i++){
            res.append(array.get(i));

            if(i != array.getSize() - 1){
                res.append(",");
            }
        }
        res.append("] top");
        return res.toString();
    }

    public static void main(String[] args) {
        System.out.println("Stack 自我实现的栈 \n");

        ArrayStack<Integer> stack= new ArrayStack<Integer>();
        for (int i = 0; i < 10 ; i++){
            stack.push(i);
            System.out.println(stack);
        }

        stack.pop();
        System.out.println(stack);
        System.out.println(stack.peek());
    }
}
