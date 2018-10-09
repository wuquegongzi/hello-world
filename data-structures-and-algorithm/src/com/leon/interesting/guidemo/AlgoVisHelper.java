package com.leon.interesting.guidemo;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * 工具
 * @package: com.leon.example.gui
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/10/9 15:50
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class AlgoVisHelper {

    private AlgoVisHelper(){};
    /**
     * 设置线条
     */
    public static void setStrokeWidth(Graphics2D g2d, int w){
        int strokeWidth = w;
        g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    public static void setColor(Graphics2D g2d, Color color){
       g2d.setColor(color);
    }
    /**
     *  设置空心圆
     * @param g2d
     * @param x
     * @param y
     * @param r
     */
    public static void strokeCircle(Graphics2D g2d, int x, int y, int r){
        Ellipse2D circle = new Ellipse2D.Double(x-r, y-r, 2*r, 2*r);
        g2d.draw(circle);
    }

    /**
     * 设置实心圆
     * @param g2d
     * @param x
     * @param y
     * @param r
     */
    public static void fillCircle(Graphics2D g2d, int x, int y, int r){
        Ellipse2D circle = new Ellipse2D.Double(x-r, y-r, 2*r, 2*r);
        g2d.fill(circle);
    }

    public static void pause(int t){

        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
