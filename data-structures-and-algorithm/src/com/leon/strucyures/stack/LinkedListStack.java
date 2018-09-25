package com.leon.strucyures.stack;

import com.leon.strucyures.LinkedList;

/**
 * @package: com.leon.strucyures
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/8/17 16:27
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class LinkedListStack<E> implements Stack<E> {

    private LinkedList<E> list;

    public LinkedListStack(){
        list = new LinkedList<>();
    }


    @Override
    public void push(E e) {
        list.addFirst(e);
    }

    @Override
    public E pop() {
        return list.removeFirst();
    }

    @Override
    public E peek() {
        return list.getFirst();
    }

    @Override
    public int getSize() {
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("Stack：top ");
        res.append(list);
        return res.toString();
    }

    public static void main(String[] args) {
        System.out.println("Stack 链表自我实现的栈 \n");

        LinkedListStack<Integer> stack= new LinkedListStack<Integer>();
        for (int i = 0; i < 10 ; i++){
            stack.push(i);
            System.out.println(stack);
        }

        stack.pop();
        System.out.println(stack);
        System.out.println(stack.peek());
    }
}
