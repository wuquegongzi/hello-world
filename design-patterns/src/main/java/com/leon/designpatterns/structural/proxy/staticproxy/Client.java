package com.leon.designpatterns.structural.proxy.staticproxy;

/**
 * @package: com.leon.designpatterns.structural.proxy.staticproxy
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 16:36
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class Client {

    public static void main(String[] args) {
        BuyHouse buyHouse = new BuyHouseImpl();
        buyHouse.buyHosue();
        BuyHouseProxy buyHouseProxy = new BuyHouseProxy(buyHouse);
        buyHouseProxy.buyHosue();
    }
}
