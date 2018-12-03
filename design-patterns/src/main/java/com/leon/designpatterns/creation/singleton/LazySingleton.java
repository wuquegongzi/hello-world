package com.leon.designpatterns.creation.singleton;

/**
 * @package: com.leon.designpatterns.creation.singleton
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 12:35
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class LazySingleton {
    private static LazySingleton lazySingleton = null;

    /**
     * 默认构造子函数
     */
    private LazySingleton(){}

    /**
     * 静态工厂方法
     * @return
     */
    public static LazySingleton getInstance(){
        if(lazySingleton == null){
            lazySingleton = new LazySingleton();
        }

        return lazySingleton;
    }
}
