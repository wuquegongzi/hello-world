package com.leon.designpatterns.creation.singleton;

/**
 * @package: com.leon.designpatterns.creation.singleton
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 13:41
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class InnerClassSingleton {

    private InnerClassSingleton(){}

    /**
     *    类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     *    没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。
     */
    private static class SingleHolder{

        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static InnerClassSingleton instance
                = new InnerClassSingleton();
    }

    public static InnerClassSingleton getInstance(){
        return SingleHolder.instance;
    }

}
