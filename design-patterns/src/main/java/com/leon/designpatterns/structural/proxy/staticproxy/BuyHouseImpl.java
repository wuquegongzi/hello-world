package com.leon.designpatterns.structural.proxy.staticproxy;

/**
 * @package: com.leon.designpatterns.structural.proxy.staticproxy
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 16:30
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class BuyHouseImpl implements BuyHouse {

    @Override
    public void buyHosue() {
        System.out.println("我要买房");
    }
}
