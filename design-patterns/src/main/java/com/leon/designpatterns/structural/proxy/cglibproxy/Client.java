package com.leon.designpatterns.structural.proxy.cglibproxy;

import com.leon.designpatterns.structural.proxy.staticproxy.BuyHouse;
import com.leon.designpatterns.structural.proxy.staticproxy.BuyHouseImpl;

/**
 * CGLIB代理
 */
public class Client {
    public static void main(String[] args) {
        BuyHouse buyHouse = new BuyHouseImpl();
        CglibProxy cglibProxy = new CglibProxy();
        BuyHouseImpl buyHouseCglibProxy = (BuyHouseImpl) cglibProxy.getInstance(buyHouse);
        buyHouseCglibProxy.buyHosue();
    }
}
