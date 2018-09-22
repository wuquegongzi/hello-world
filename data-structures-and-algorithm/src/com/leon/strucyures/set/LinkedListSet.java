package com.leon.strucyures.set;

import com.leon.strucyures.LinkedList;

/**
 * 使用链表实现Set
 */
public class LinkedListSet<E> implements Set<E> {

    private LinkedList<E> list;

    public LinkedListSet(){
         list = new LinkedList<>();
    }


    @Override
    public void add(E e) {
        if(!contains(e)){
            list.addFirst(e);
        }
    }

    @Override
    public void remove(E e) {
       list.removeElement(e);
    }

    @Override
    public boolean contains(E e) {
        return list.Contains(e);
    }

    @Override
    public int getSize() {
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
