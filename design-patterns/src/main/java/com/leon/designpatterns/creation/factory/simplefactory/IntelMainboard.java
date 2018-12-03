package com.leon.designpatterns.creation.factory.simplefactory;

/**
 * @package: com.leon.designpatterns.creation.factory.simplefactory
 * @author: 陈明磊<minglei.chen @ gm-medicare.com>
 * @date: 2018/12/3 14:14
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class IntelMainboard implements Mainboard {

    /**
     * CPU插槽的孔数
     */
    private int cpuHoles = 0;

    /**
     * 构造方法，传入CPU插槽的孔数
     * @param cpuHoles
     */
    public IntelMainboard(int cpuHoles){
        this.cpuHoles = cpuHoles;
    }

    @Override
    public void installCPU() {
        System.out.println("Intel主板的CPU插槽孔数是：" + cpuHoles);
    }
}
