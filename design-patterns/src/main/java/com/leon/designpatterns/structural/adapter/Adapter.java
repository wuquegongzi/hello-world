package com.leon.designpatterns.structural.adapter;

/**
 * 适配器角色
 * 类适配器
 */
public class Adapter extends Adaptee implements Target{

    /**
     * 由于源类Adaptee没有方法sampleOperation2()
     * 因此适配器补充上这个方法
     */
    @Override
    public void sampleOperation2() {
        //写相关的代码
        System.out.println("我是类适配器哟，咿呀咿呀哟~~~");
    }
}
