package com.leon.designpatterns.creation.factory.abstractfactory;

/**
 * 抽象工厂
 * @package: com.leon.designpatterns.creation.factory.abstractfactory
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 15:33
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class Client {

    public static void main(String[] args) {
        //创建装机工程师对象
        ComputerEngineer cf = new ComputerEngineer();
        //客户选择并创建需要使用的产品对象
        AbstractFactory af = new IntelFactory();
        //告诉装机工程师自己选择的产品，让装机工程师组装电脑
        cf.makeComputer(af);
    }
}
