package com.leon.designpatterns.creation.factory.simplefactory;

/**
 * @package: com.leon.designpatterns.creation.factory.simplefactory
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/12/3 14:33
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class MainboardFactory {

    public static Mainboard createMainboard(int type){
        Mainboard mainboard = null;
        if(type == 1){
            mainboard = new IntelMainboard(755);
        }else if(type == 2){
            mainboard = new AmdMainboard(938);
        }
        return mainboard;
    }
}
