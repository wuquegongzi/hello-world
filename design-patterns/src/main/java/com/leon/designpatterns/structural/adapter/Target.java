package com.leon.designpatterns.structural.adapter;

/**
 * 目标(Target)角色
 */
public interface Target {

    /**
     * 这是源类Adaptee也有的方法
     */
    public void sampleOperation1();
    /**
     * 这是源类Adapteee没有的方法
     */
    public void sampleOperation2();
}
