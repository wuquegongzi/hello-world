package com.leon.designpatterns.creation.factory.simplefactory;

/**
 * @package: com.leon.designpatterns.creation.factory.simplefactory
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 14:35
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class ComputerEngineer {

    /**
     * 定义组装机需要的CPU
     */
    private Cpu cpu = null;
    /**
     * 定义组装机需要的主板
     */
    private Mainboard mainboard = null;

    public void makeComputer(int cpuType , int mainboard){
        /**
         * 组装机器的基本步骤
         */
        //1:首先准备好装机所需要的配件
        prepareHardwares(cpuType, mainboard);
        //2:组装机器
        //3:测试机器
        //4：交付客户
    }

    private void prepareHardwares(int cpuType, int mainboard) {

        //直接找相应的工厂获取
        this.cpu = CpuFactory.createCpu(cpuType);
        this.mainboard = MainboardFactory.createMainboard(mainboard);

        //测试配件是否好用
        this.cpu.calculate();
        this.mainboard.installCPU();
    }
}
