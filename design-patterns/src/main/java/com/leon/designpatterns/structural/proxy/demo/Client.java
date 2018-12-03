package com.leon.designpatterns.structural.proxy.demo;

/**
 * 代理模式
 * @package: com.leon.designpatterns.structural.proxy
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 16:09
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class Client {

    public static void main(String[] args) {
        AbstractObject obj = new ProxyObject();
        obj.operation();
    }
}
