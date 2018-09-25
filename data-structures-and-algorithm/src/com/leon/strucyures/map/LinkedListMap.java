package com.leon.strucyures.map;

/**
 *链表 实现Map
 * @package: com.leon.strucyures.map
 * @author: 陈明磊<minglei.chen @ gm-medicare.com>
 * @date: 2018/9/25 15:10
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class LinkedListMap<K,V> implements Map<K,V> {

    /**
     * 私有 Node
     */
    private class Node{
        public K key;
        public V value;
        Node next;

        public Node(K key,V value,Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Node(K key){
            this(key,null,null);
        }

        public Node(){
            this(null,null, null);
        }

        @Override
        public String toString(){
            return key.toString()+":"+value.toString();
        }
    }

    private Node dummyHead;
    private int size;

    public LinkedListMap(){
        //初始化一个虚拟头节点
        dummyHead = new Node();
        size = 0;
    }

    /**
     * 私有 根据key获取节点
     * @param key
     * @return
     */
    private Node getNode(K key){

        Node cur = dummyHead.next;
        while(cur != null){
            if(cur.key.equals(key)){
                return cur;
            }else {
                cur = cur.next;
            }
        }
        return null;
    }

    /**
     * 添加并覆盖
     * @param key
     * @param value
     */
    @Override
    public void add(K key, V value) {
        Node node = getNode(key);
        if(node == null){
            dummyHead.next = new Node(key,value,dummyHead.next);
            size ++;
        }else{
            node.value = value;
        }
    }

    /**
     * 修改
     * @param key
     * @param newValue
     */
    @Override
    public void set(K key, V newValue) {
        Node node = getNode(key);
        if(node == null){
            try {
                throw new IllegalAccessException(key+" doesn't exist!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }else{
            node.value = newValue;
        }
    }

    /**
     * 清除
     * @param key
     * @return
     */
    @Override
    public V remove(K key) {

        Node prev = dummyHead;
        while(prev.next != null){
            if(prev.next.key.equals(key)){
               break;
            }else{
                prev = prev.next;
            }
        }

        if(prev.next != null){
            Node delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
            size --;
            return delNode.value;
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        return getNode(key) != null;
    }

    /**
     * 根据key获取值
     * @param key
     * @return
     */
    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node == null ? null :node.value;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

}
