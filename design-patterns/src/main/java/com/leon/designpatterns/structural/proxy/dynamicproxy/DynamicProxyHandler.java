package com.leon.designpatterns.structural.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @package: com.leon.designpatterns.structural.proxy.dynamicproxy
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 16:54
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class DynamicProxyHandler implements InvocationHandler {

    private Object object;

    public DynamicProxyHandler(final Object object){
          this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("买房前准备");
        Object result = method.invoke(object,args);
        System.out.println("买房后装修");
        return result;
    }
}
