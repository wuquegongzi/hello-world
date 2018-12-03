package com.leon.designpatterns.creation.factory.simplefactory;

/**
 * 简单工厂
 * @package: com.leon.designpatterns.creation.factory.simplefactory
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 14:49
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class Client {

    public static void main(String[] args) {
        ComputerEngineer cf = new ComputerEngineer();
        cf.makeComputer(2,2);
    }
}
