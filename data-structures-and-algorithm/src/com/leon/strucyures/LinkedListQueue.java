package com.leon.strucyures;

public class LinkedListQueue<E> implements Queue<E> {


    /**
     * 私有 Node
     */
    private class Node{
        public E e;
        Node next;

        public Node(E e,Node next){
            this.e = e;
            this.next = next;
        }

        public Node(E e){
            this(e,null);
        }

        public Node(){
            this(null, null);
        }

        @Override
        public String toString(){
            return e.toString();
        }
    }

    private Node head , tail;
    private int size;

    /**
     * 显性构造函数
     */
    public LinkedListQueue(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * 入队
     * @param e
     */
    @Override
    public void enqueue(E e) {

        if(tail == null){
            tail = new Node(e);
            head = tail;
        }else{
            tail.next = new Node(e);
            tail = tail.next;
        }

        size ++;

    }

    /**
     * 出队
     * @return
     */
    @Override
    public E dequeue() {

        if(isEmpty()){
            try {
                throw new IllegalAccessException("Cannot dequeue from an empty queue.");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        Node retNode = head;
        head = head.next;

        retNode.next = null;
        if(head == null){
            tail = null;
        }
        size --;
        return retNode.e;
    }

    @Override
    public E getFront() {

        if(isEmpty()){
            try {
                throw new IllegalAccessException("Cannot getFront from an empty queue.");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        return head.e;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        res.append("Queue： front  ");
        Node cur = head;
        while (cur != null) {
            res.append(cur + "->");
            cur = cur.next;
        }
        res.append("NULL tail");
        return res.toString();

    }

    public static void main(String[] args) {

        LinkedListQueue<Integer> queue = new LinkedListQueue<>();

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
