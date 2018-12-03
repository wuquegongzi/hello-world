package com.leon.designpatterns.creation.factory.abstractfactory;

import com.leon.designpatterns.creation.factory.simplefactory.Cpu;
import com.leon.designpatterns.creation.factory.simplefactory.IntelCpu;
import com.leon.designpatterns.creation.factory.simplefactory.IntelMainboard;
import com.leon.designpatterns.creation.factory.simplefactory.Mainboard;

/**
 * @package: com.leon.designpatterns.creation.factory.abstractfactory
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 15:16
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class IntelFactory implements AbstractFactory {
    @Override
    public Cpu createCpu() {
        return new IntelCpu(755);
    }

    @Override
    public Mainboard createMainboard() {
        return new IntelMainboard(755);
    }
}
