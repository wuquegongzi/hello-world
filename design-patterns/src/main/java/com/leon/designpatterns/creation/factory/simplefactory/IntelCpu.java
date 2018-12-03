package com.leon.designpatterns.creation.factory.simplefactory;

/**
 * @package: com.leon.designpatterns.creation.factory.simplefactory
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 14:08
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class IntelCpu implements Cpu {

    /**
     * CPU的针脚数
     */
    private int pins = 0;

    public IntelCpu(int pins){
        this.pins = pins;
    }

    @Override
    public void calculate() {
        System.out.println("Intel CPU的针脚数：" + pins);
    }
}
