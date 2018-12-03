package com.leon.designpatterns.structural.proxy.demo;

/**
 * 目标对象角色
 * @package: com.leon.designpatterns.structural.proxy
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 16:07
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class RealObject extends AbstractObject {
    @Override
    public void operation() {
        //一些操作
        System.out.println("一些操作");
    }
}
