package com.leon.designpatterns.creation.factory.simplefactory;

/**
 * @package: com.leon.designpatterns.creation.factory.simplefactory
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 14:11
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class AmdCpu implements Cpu {

    /**
     * CPU的针脚数
     */
    private int pins = 0;

    public  AmdCpu(int pins){
        this.pins = pins;
    }

    @Override
    public void calculate() {
        System.out.println("AMD CPU的针脚数：" + pins);
    }
}
