package com.leon.designpatterns.creation.factory.abstractfactory;

import com.leon.designpatterns.creation.factory.simplefactory.AmdCpu;
import com.leon.designpatterns.creation.factory.simplefactory.AmdMainboard;
import com.leon.designpatterns.creation.factory.simplefactory.Cpu;
import com.leon.designpatterns.creation.factory.simplefactory.Mainboard;

/**
 * @package: com.leon.designpatterns.creation.factory.abstractfactory
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 15:24
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class AmdFactory implements AbstractFactory {
    @Override
    public Cpu createCpu() {
        return new AmdCpu(938);
    }

    @Override
    public Mainboard createMainboard() {
        return new AmdMainboard(938);
    }
}
