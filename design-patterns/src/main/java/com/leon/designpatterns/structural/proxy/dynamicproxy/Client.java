package com.leon.designpatterns.structural.proxy.dynamicproxy;

import com.leon.designpatterns.structural.proxy.staticproxy.BuyHouse;
import com.leon.designpatterns.structural.proxy.staticproxy.BuyHouseImpl;

import java.lang.reflect.Proxy;

/**
 * 动态代理
 * @package: com.leon.designpatterns.structural.proxy.dynamicproxy
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 16:59
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class Client {

    public static void main(String[] args) {
        BuyHouse buyHouse = new BuyHouseImpl();
        /**
         * Proxy.newProxyInstance()方法接受三个参数：
         * ClassLoader loader:指定当前目标对象使用的类加载器,获取加载器的方法是固定的
         * Class<?>[] interfaces:指定目标对象实现的接口的类型,使用泛型方式确认类型
         * InvocationHandler:指定动态处理器，执行目标对象的方法时,会触发事件处理器的方法
         */
        BuyHouse proxyBuyHouse  = (BuyHouse)Proxy.newProxyInstance(BuyHouse.class.getClassLoader(),new
        Class[]{BuyHouse.class},new DynamicProxyHandler(buyHouse));
        proxyBuyHouse.buyHosue();
    }
}
