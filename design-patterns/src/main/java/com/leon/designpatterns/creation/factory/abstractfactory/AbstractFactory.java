package com.leon.designpatterns.creation.factory.abstractfactory;

import com.leon.designpatterns.creation.factory.simplefactory.Cpu;
import com.leon.designpatterns.creation.factory.simplefactory.Mainboard;

/**
 * @package: com.leon.designpatterns.creation.factory.abstractfactory
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 15:14
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public interface AbstractFactory {

    /**
     * 创建CPU对象
     * @return CPU对象
     */
    public Cpu createCpu();
    /**
     * 创建主板对象
     * @return 主板对象
     */
    public Mainboard createMainboard();
}
