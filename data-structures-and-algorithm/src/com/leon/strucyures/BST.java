package com.leon.strucyures;

/**
 * 二分搜索树
 * @param <E>
 */
public class BST<E extends Comparable<E>>{

    /**
     * 节点
     */
    private class Node{

        public E e;
        public Node left , right;

        public Node(E e){
            this.e = e;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public BST(){

        root = null;
        size = 0 ;

    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 向二分搜索树添加新的元素e
     * @param e
     */
    public void add(E e){

        if(root == null){
            root = new Node(e);
            size ++;
        }else{
            add(root,e);
        }

    }

    /**
     * 递归 向以Node为根的二分搜索树中插入元素E
     * @param node
     * @param e
     */
    private Node add(Node node, E e) {

        if(node == null){
            size ++;
            return new Node(e);
        }

        if(e.compareTo(node.e) < 0){
            node.left = add(node.left,e);
        }else if(e.compareTo(node.e) > 0){
            node.right = add(node.right,e);
        }

        return node;

       /*
       if(e.equals(node.e)){
            return; //不添加重复数据
        } else if(e.compareTo(node.e) < 0 && node.left == null){
            node.left = new Node(e);
             size ++;
             return;
        } else if(e.compareTo(node.e) > 0 && node.right == null){
            node.right = new Node(e);
            size ++;
            return;
        }
        if(e.compareTo(node.e) < 0){
            add(node.left,e);
        }else{
            add(node.right,e);
        }*/

    }


}
