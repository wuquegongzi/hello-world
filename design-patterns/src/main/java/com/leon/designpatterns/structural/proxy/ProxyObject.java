package com.leon.designpatterns.structural.proxy;

/**
 * @package: com.leon.designpatterns.structural.proxy
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 16:07
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class ProxyObject extends AbstractObject{

    RealObject realObject = new RealObject();

    @Override
    public void operation() {
        //调用目标对象之前可以做相关操作
        System.out.println("before");
        realObject.operation();
        //调用目标对象之后可以做相关操作
        System.out.println("after");
    }
}
