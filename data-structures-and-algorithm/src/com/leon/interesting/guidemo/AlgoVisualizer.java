package com.leon.interesting.guidemo;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 控制层 算法可视化
 * @package: com.leon.example.gui
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/10/9 17:25
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class AlgoVisualizer {

    private Circle[] circles;
    private AlgoFrame frame;
    private boolean isAnimated = true; //动画是否在执行，默认true

    AlgoVisualizer(int sceneWidth,int sceneHeight, int N){

        circles = new Circle[N];
        int R =50;
        for (int i=0 ; i < N ; i++){
            int x = (int)(Math.random() * (sceneWidth-2*R)) + R;
            int y = (int)(Math.random() * (sceneHeight-2*R)) + R;
            int vx = (int)(Math.random() * 11) - 5;
            int vy = (int)(Math.random() * 11) - 5;
            circles[i] = new Circle(x,y,R,vx,vy);
        }

        //放入事件分发线程
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Welcome",sceneWidth,sceneHeight);
            frame.addKeyListener(new AlgoKeyListener());
            new Thread(() -> {
                run();
            }).start();

        });
    }

    /**
     * 动画逻辑
     */
    private void run(){
        while (true){
            //绘制数据
            frame.render(circles);
            AlgoVisHelper.pause(20);
            //更新数据
            if(isAnimated){
                for (Circle circle:circles) {
                    circle.move(0,0,frame.getCanvasWidth(),frame.getCanvasHeight());
                }
            }

        }
    }

    /**
     * 按键
     */
    private class AlgoKeyListener extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent e) {

            if(e.getKeyChar() == ' '){
              isAnimated = !isAnimated;
            }
        }
    }


    /**
     * 启动
     * @param args
     */
    public static void main(String[] args) {

        int sceneWidth = 800;
        int sceneHeight = 800;

        int N =10;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth,sceneHeight,N);

    }

}


