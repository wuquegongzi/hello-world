package com.leon.strucyures;

import java.util.LinkedList;
import java.util.Queue;

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

    /**
     * 查看二分搜索树中是否包含元素e
     * @param e
     * @return
     */
    public boolean contains(E e){
        return contains(root,e);
    }

    private boolean contains(Node node, E e) {

        if(node == null){
           return false;
        }

        if(e.compareTo(node.e) == 0 ){
            return true;
        }else if(e.compareTo(node.e) < 0){
            return contains(node.left,e);
        }else if(e.compareTo(node.e) > 0){
            return contains(node.right,e);
        }

        return false;
    }


    /**
     * 前序遍历 递归算法
     * @param
     */
    public void preOrder(){
        preOrder(root);
    }

    private void preOrder(Node node) {

        if(node == null){
            return;
        }

        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * 前序遍历 非递归算法
     */
    public void preOrderNR(){
        Stack<Node> stack = new ArrayStack<Node>();
        stack.push(root);
        while(!stack.isEmpty()){
            Node cur = stack.pop();
            System.out.println(cur.e);
            if(cur.right != null){
                stack.push(cur.right);
            }
            if(cur.left != null){
                stack.push(cur.left);
            }
        }
    }

    /**
     * 中序遍历 递归算法
     */
    public void inOrder(){
        inOrder(root);
    }

    private void inOrder(Node node) {

        if(node == null){
            return;
        }
        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }

    /**
     * 后序遍历 递归算法
     */
    public void postOrder(){
        postOrder(root);
    }

    private void postOrder(Node node) {
        if(node == null){
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.e);
    }

    /**
     * 二分搜索树的层序遍历
     */
    public void levelOrder(){

        Queue<Node> q = new LinkedList<Node>();
        q.add(root);
        while (!q.isEmpty()){
            Node cur = q.remove();
            System.out.println(cur.e);

            if(cur.left != null){
                q.add(cur.left);
            }
            if(cur.right != null){
                q.add(cur.right);
            }
        }
    }

    /**
     * 寻找最小元素
     * @return
     */
    public E minimum(){
        if(size == 0){
            try {
                throw new IllegalAccessException("BST is empty!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return minimum(root).e;
    }

    private Node minimum(Node node) {
        if(node.left == null){
            return node;
        }
        return minimum(node.left);
    }

    /**
     * 寻找最大元素
     * @return
     */
    public E maximum(){
        if(size == 0){
            try {
                throw new IllegalAccessException("BST is empty!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return maximum(root).e;
    }

    private Node maximum(Node node) {
        if(node.right == null){
            return node;
        }
        return maximum(node.right);
    }

    /**
     * 删除最小元素
     * @return
     */
    public E removeMin(){

        E ret = minimum();
        root = removeMin(root);
        return ret;
    }

    private Node removeMin(Node node) {

       if(node.left == null){
           Node rightNode = node.right;
           node.right = null;
           size --;
           return rightNode;
       }

       node.left = removeMin(node.left);
       return node;

    }

    /**
     * 删除最大元素
     * @return
     */
    public E removeMax(){

        E ret = minimum();
        root = removeMax(root);
        return ret;
    }

    private Node removeMax(Node node) {

        if(node.right == null){
            Node leftNode = node.left;
            node.left = null;
            size --;
            return leftNode;
        }

        node.right = removeMax(node.right);
        return node;

    }

    /**
     * 删除元素e
     * @param e
     */
    public void remove(E e){

        root = remove(root,e);
    }

    private Node remove(Node node, E e) {

        if(node == null){
            return null;
        }

        if(e.compareTo(node.e) < 0){
            node.left = remove(node.left,e);
            return node;
        }else if(e.compareTo(node.e) > 0){
            node.right = remove(node.right,e);
            return node;
        }else{ //e == node.e

            //待删除节点左子树为空的情况
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;
            }

            //待删除节点右子树为空的情况
            if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size --;
                return leftNode;
            }

            //待删除节点左右子树均不为空的情况
            //找到比待删除节点大的最小节点，即待删除节点右子树的最小节点
            //用这个节点顶替待删除节点的位置
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;

            node.left = node.right = null;
            return successor;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){

        StringBuilder res = new StringBuilder();
        generateBSTString(root,0,res);
        return res.toString();
    }

    private void generateBSTString(Node node, int depth, StringBuilder res) {
        if(node == null){
            res.append(generateBSTString(depth)+"null \n");
            return;
        }
        res.append(generateBSTString(depth) + node.e +"\n");
        generateBSTString(node.left,depth + 1,res);
        generateBSTString(node.right,depth + 1,res);
    }

    private String generateBSTString(int depth) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            res.append("--");
        }
        return res.toString();
    }


    public static void main(String[] args) {

        BST<Integer> bst = new BST<>();
        int[] nums = {5, 3, 6, 8, 4, 2};

        for (int i = 0; i < nums.length; i++) {
            bst.add(nums[i]);
        }

//        bst.removeMin();
//        bst.removeMax();

        System.out.println("前序递归遍历：");
        bst.preOrder();
        System.out.println("前序非递归遍历：");
        bst.preOrderNR();
        System.out.println("中序递归遍历：");
        bst.inOrder();
        System.out.println("后序递归遍历：");
        bst.postOrder();
        System.out.println("层序遍历:");
        bst.levelOrder();
        System.out.println("toString:");
        System.out.println(bst);
        System.out.println("最小值:");
        System.out.println(bst.minimum());
        System.out.println("最大值:");
        System.out.println(bst.maximum());

    }
}
