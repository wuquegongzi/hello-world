package com.leon.interesting.guidemo;

/**
 * 小圆圈
 * @package: com.leon.example.gui
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/10/9 16:30
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class Circle {

    public int x,y;
    private int r;
    public int vx, vy; //速度

    public  Circle(int x,int y, int r,int vx, int vy){
           this.r = r;
           this.x = x;
           this.y = y;
           this.vx = vx;
           this.vy = vy;
    }

    public int getR() {
        return r;
    }

    public void move(int minx, int miny, int maxx, int maxy){
        x += vx;
        y += vy;
        checkCollision(minx, miny, maxx, maxy);
    }

    /**
     * 检测碰撞
     * @param minx
     * @param miny
     * @param maxx
     * @param maxy
     */
    private void checkCollision(int minx, int miny, int maxx, int maxy){
        if(x - r < minx){x = r; vx = -vx;}
        if(x + r >= maxx){x = maxx - r; vx = -vx;}
        if(y - r < miny){y = r; vy = -vy;}
        if(y + r >= maxy){y = maxy - r; vy = -vy;}
    }
}
