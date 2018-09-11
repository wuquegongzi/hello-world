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
     * 前序遍历
     * @param e
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
     * 中序遍历
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
     * 后续遍历
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

        bst.preOrder();
        System.out.println();
        bst.inOrder();
        System.out.println();
        bst.inOrder();
        System.out.println();
        System.out.println(bst);
    }
}
