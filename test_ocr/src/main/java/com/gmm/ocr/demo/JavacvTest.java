package com.gmm.ocr.demo;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

/**
 * 用Laplacian 梯度函数实现
 */
public class JavacvTest {

    public static void main(String[] args) {
        String path = "D:\\360MoveData\\Users\\chenminglei.GM-MEDICARE\\Desktop\\test";        //要遍历的路径
        File file = new File(path);        //获取其file对象
        File[] fs = file.listFiles();    //遍历path下的文件和目录，放在File数组中

        Map<String,Double> map = new HashMap<String,Double>();
        Mat srcImage = null;
        for (File f : fs) {                    //遍历File[]数组
            if (!f.isDirectory())        //若非目录(即文件)，则打印
                srcImage = imread(f.getAbsolutePath());
            Mat dstImage = new Mat();
            cvtColor(srcImage, dstImage, COLOR_BGR2GRAY);        //转化为灰度图
            //在gray目录下生成灰度图片
            imwrite(path+"gray//gray-"+f.getName(), dstImage);

            Mat laplacianDstImage = new Mat();
            //阈值太低会导致正常图片被误断为模糊图片，阈值太高会导致模糊图片被误判为正常图片
            Laplacian(dstImage, laplacianDstImage, CV_64F);
            //在laplacian目录下升成经过拉普拉斯掩模做卷积运算的图片
            imwrite(path+"laplacian//laplacian-"+f.getName(), laplacianDstImage);

            //矩阵标准差
            Mat stddev = new Mat();

            //求矩阵的均值与标准差
            meanStdDev(laplacianDstImage, new Mat(), stddev);
            //double norm = Core.norm(laplacianDstImage); // ((全部元素的平方)的和)的平方根
            //System.out.println("\n矩阵的均值：\n" + mean.dump());
            System.out.println(f.getName() + "矩阵的标准差：\n" + stddev.createIndexer().getDouble()+"\n");
            // System.out.println(f.getName()+"平方根：\n" + norm);
            map.put(f.getName(),stddev.createIndexer().getDouble());
        }

        for (String key:map.keySet()){
            Double d = map.get(key);
            if(d > 50){
                System.out.println("质量检测不通过："+key+":"+d);
            }
        }

    }

}
