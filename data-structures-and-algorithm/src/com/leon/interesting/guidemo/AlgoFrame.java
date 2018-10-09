package com.leon.interesting.guidemo;

import javax.swing.*;
import java.awt.*;

/**
 * @package: com.leon.example.gui
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/10/9 14:05
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class AlgoFrame extends JFrame {

    private int canvasWidth;
    private int canvasHeight;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight){
        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        canvas.setPreferredSize(new Dimension(canvasWidth,canvasHeight));
        setContentPane(canvas);
        pack();
//        setSize(canvasWidth,canvasHeight);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public AlgoFrame(String title){
        new AlgoFrame(title,1024,768);
    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    private Circle[] circles;
    public void render(Circle[] circles){
        this.circles = circles;
        repaint();
    }

    /**
     * 面板
     */
    private class AlgoCanvas extends JPanel{

        public AlgoCanvas(){
            super(true); //支持双缓存
        }

         @Override
         public void paintComponent(Graphics g) {
             super.paintComponent(g);

             //绘制圆形
//             g.drawOval(50,50,300,300);
             Graphics2D g2d = (Graphics2D) g;

             //抗锯齿
             RenderingHints hints = new RenderingHints(
                     RenderingHints.KEY_ANTIALIASING,
                     RenderingHints.VALUE_ANTIALIAS_ON);
             g2d.addRenderingHints(hints);

             //具体绘制
             AlgoVisHelper.setStrokeWidth(g2d,1);
             AlgoVisHelper.setColor(g2d,Color.red);

             for (Circle circle: circles) {
                 AlgoVisHelper.strokeCircle(g2d, circle.x,circle.y,circle.getR());
             }
         }

         @Override
         public Dimension getPreferredSize(){
             return new Dimension(canvasWidth, canvasHeight);
         }
    }
}
