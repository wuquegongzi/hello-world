package com.leon.designpatterns.creation.singleton;

/**
 * @package: com.leon.designpatterns.creation.singleton
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/11/29 14:11
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class EagerSingleton {

    private static EagerSingleton eagerSingleton = new EagerSingleton();

    /**
     * 默认构造子函数
     */
    private EagerSingleton(){}

    /**
     * 静态工厂方法
     * @return
     */
    public static EagerSingleton getInstance(){
        return eagerSingleton;
    }

}
