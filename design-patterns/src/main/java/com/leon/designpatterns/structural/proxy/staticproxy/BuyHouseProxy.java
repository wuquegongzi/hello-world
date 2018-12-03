package com.leon.designpatterns.structural.proxy.staticproxy;

/**
 * @package: com.leon.designpatterns.structural.proxy.staticproxy
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 16:31
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class BuyHouseProxy implements BuyHouse {

    private BuyHouse buyHouse;

    public BuyHouseProxy(final BuyHouse buyHouse){
        this.buyHouse = buyHouse;
    }

    @Override
    public void buyHosue() {
        System.out.println("买房前准备");
        buyHouse.buyHosue();
        System.out.println("买房后装修");
    }
}
