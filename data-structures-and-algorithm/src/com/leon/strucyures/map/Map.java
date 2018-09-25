package com.leon.strucyures.map;

/**
 * 映射 接口
 * @package: com.leon.strucyures.map
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/9/25 14:55
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public interface Map<K,V> {

    void add(K key,V value);
    V remove(K key);
    boolean contains(K key);
    V get(K key);
    void set(K key,V newValue);
    int getSize();
    boolean isEmpty();
}
