package com.gmm.ocr.demo;

import com.gmm.ocr.utils.ImageUI;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgproc.Imgproc.CHAIN_APPROX_SIMPLE;
import static org.opencv.imgproc.Imgproc.RETR_EXTERNAL;

public class test {

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread("D:\\360MoveData\\Users\\chenminglei.GM-MEDICARE\\Desktop\\pic\\888.jpg");//输入图像

        if (src.empty()) {
            return;
        }

        ImageUI out = new ImageUI();

        //1、转灰度，降噪====================
        // 彩色转灰度
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        // 高斯滤波，降噪
        Mat dst = new Mat();
        Imgproc.GaussianBlur(gray, dst, new Size(3,3), 2, 2);


        //2、边缘检测======================
        // Canny边缘检测
        Mat edges = new Mat();
        Imgproc.Canny(dst, edges, 20, 60, 3, false);

        File outputfile = new File("D:\\360MoveData\\Users\\chenminglei.GM-MEDICARE\\Desktop\\pic\\saved-canny.png");
        try {
            ImageIO.write(out.convert2BufferedImage(edges), "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 膨胀，连接边缘
        Mat dilate = new Mat();
        Imgproc.dilate(edges, dilate, new Mat(), new Point(-1,-1), 3, 1, new Scalar(1));

        outputfile = new File("D:\\360MoveData\\Users\\chenminglei.GM-MEDICARE\\Desktop\\pic\\saved-4.png");
        try {
            ImageIO.write(out.convert2BufferedImage(dilate), "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //3、轮廓提取=====================
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(dilate, contours, hierarchy, RETR_EXTERNAL, CHAIN_APPROX_SIMPLE);

        //4、寻找凸包，拟合多边形==================================
        // 找出轮廓对应凸包的四边形拟合
        List<MatOfPoint> squares = new ArrayList<>();
        List<MatOfPoint> hulls = new ArrayList<>();
        MatOfInt hull = new MatOfInt();
        MatOfPoint2f approx = new MatOfPoint2f();
        approx.convertTo(approx, CvType.CV_32F);

        for (MatOfPoint contour: contours) {
            // 边框的凸包
            Imgproc.convexHull(contour, hull);

            // 用凸包计算出新的轮廓点
            Point[] contourPoints = contour.toArray();
            int[] indices = hull.toArray();
            List<Point> newPoints = new ArrayList<>();
            for (int index : indices) {
                newPoints.add(contourPoints[index]);
            }
            MatOfPoint2f contourHull = new MatOfPoint2f();
            contourHull.fromList(newPoints);

            // 多边形拟合凸包边框(此时的拟合的精度较低)
            Imgproc.approxPolyDP(contourHull, approx, Imgproc.arcLength(contourHull, true)*0.02, true);

            // 筛选出面积大于某一阈值的，且四边形的各个角度都接近直角的凸四边形
            MatOfPoint approxf1 = new MatOfPoint();
            approx.convertTo(approxf1, CvType.CV_32S);
            if (approx.rows() == 4 && Math.abs(Imgproc.contourArea(approx)) > 40000 &&
                    Imgproc.isContourConvex(approxf1)) {
                double maxCosine = 0;
                for (int j = 2; j < 5; j++) {
                    double cosine = Math.abs(getAngle(approxf1.toArray()[j%4], approxf1.toArray()[j-2], approxf1.toArray()[j-1]));
                    maxCosine = Math.max(maxCosine, cosine);
                }
                // 角度大概72度
                if (maxCosine < 0.3) {
                    MatOfPoint tmp = new MatOfPoint();
                    contourHull.convertTo(tmp, CvType.CV_32S);
                    squares.add(approxf1);
                    hulls.add(tmp);
                }
            }
        }

        // 5、找到最大的正方形==================================
        // 找出外接矩形最大的四边形
        int index = findLargestSquare(squares);
        if(squares.isEmpty()){
            System.out.println("无法找到矩形");
          return;
        }
        MatOfPoint largest_square = squares.get(index);
        if (largest_square.rows() == 0 || largest_square.cols() == 0)
            return;
//            return result;

        //6、重新执行步骤3，提升精度==================================
        // 找到这个最大的四边形对应的凸边框，再次进行多边形拟合，此次精度较高，拟合的结果可能是大于4条边的多边形
        MatOfPoint contourHull = hulls.get(index);
        MatOfPoint2f tmp = new MatOfPoint2f();
        contourHull.convertTo(tmp, CvType.CV_32F);
        Imgproc.approxPolyDP(tmp, approx, 3, true);
        List<Point> newPointList = new ArrayList<>();
        double maxL = Imgproc.arcLength(approx, true) * 0.02;

        //7、找到长方形四条边，即为纸张的外围四边形==================================
        // 找到高精度拟合时得到的顶点中 距离小于低精度拟合得到的四个顶点maxL的顶点，排除部分顶点的干扰
        for (Point p : approx.toArray()) {
            if (!(getSpacePointToPoint(p, largest_square.toList().get(0)) > maxL &&
                    getSpacePointToPoint(p, largest_square.toList().get(1)) > maxL &&
                    getSpacePointToPoint(p, largest_square.toList().get(2)) > maxL &&
                    getSpacePointToPoint(p, largest_square.toList().get(3)) > maxL)) {
                newPointList.add(p);
            }
        }

        // 找到剩余顶点连线中，边长大于 2 * maxL的四条边作为四边形物体的四条边
        List<double[]> lines = new ArrayList<>();
        for (int i = 0; i < newPointList.size(); i++) {
            Point p1 = newPointList.get(i);
            Point p2 = newPointList.get((i+1) % newPointList.size());
            if (getSpacePointToPoint(p1, p2) > 2 * maxL) {
                lines.add(new double[]{p1.x, p1.y, p2.x, p2.y});
            }
        }

        // 计算出这四条边中 相邻两条边的交点，即物体的四个顶点
        List<Point> corners = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            Point corner = computeIntersect(lines.get(i),lines.get((i+1) % lines.size()));
            corners.add(corner);
        }
        //8、透视变换，提取四边形=====================
        // 对顶点顺时针排序
        sortCorners(corners);

       // 计算目标图像的尺寸
        Point p0 = corners.get(0);
        Point p1 = corners.get(1);
        Point p2 = corners.get(2);
        Point p3 = corners.get(3);
        double space0 = getSpacePointToPoint(p0, p1);
        double space1 = getSpacePointToPoint(p1, p2);
        double space2 = getSpacePointToPoint(p2, p3);
        double space3 = getSpacePointToPoint(p3, p0);

        double imgWidth = space1 > space3 ? space1 : space3;
        double imgHeight = space0 > space2 ? space0 : space2;

        // 如果提取出的图片宽小于高，则旋转90度
        if (imgWidth < imgHeight) {
            double temp = imgWidth;
            imgWidth = imgHeight;
            imgHeight = temp;
            Point tempPoint = p0.clone();
            p0 = p1.clone();
            p1 = p2.clone();
            p2 = p3.clone();
            p3 = tempPoint.clone();
        }

        Mat quad = Mat.zeros((int)imgHeight * 2, (int)imgWidth * 2, CvType.CV_8UC3);

        MatOfPoint2f cornerMat = new MatOfPoint2f(p0, p1, p2, p3);
        MatOfPoint2f quadMat = new MatOfPoint2f(new Point(imgWidth*0.4, imgHeight*1.6),
                new Point(imgWidth*0.4, imgHeight*0.4),
                new Point(imgWidth*1.6, imgHeight*0.4),
                new Point(imgWidth*1.6, imgHeight*1.6));

        // 提取图像
        Mat transmtx = Imgproc.getPerspectiveTransform(cornerMat, quadMat);
        Imgproc.warpPerspective(src, quad, transmtx, quad.size());
//        return quad;

//        out.imshow("OpenCV_Canny_Output", quad);

        outputfile = new File("D:\\360MoveData\\Users\\chenminglei.GM-MEDICARE\\Desktop\\pic\\saved.png");
        BufferedImage bi = out.convert2BufferedImage(quad);
        try {
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 根据三个点计算中间那个点的夹角   pt1 pt0 pt2
    private static double getAngle(Point pt1, Point pt2, Point pt0)
    {
        double dx1 = pt1.x - pt0.x;
        double dy1 = pt1.y - pt0.y;
        double dx2 = pt2.x - pt0.x;
        double dy2 = pt2.y - pt0.y;
        return (dx1*dx2 + dy1*dy2)/Math.sqrt((dx1*dx1 + dy1*dy1)*(dx2*dx2 + dy2*dy2) + 1e-10);
    }

    // 找到最大的正方形轮廓
    private static int findLargestSquare(List<MatOfPoint> squares) {
        if (squares.size() == 0)
            return -1;
        int max_width = 0;
        int max_height = 0;
        int max_square_idx = 0;
        int currentIndex = 0;
        for (MatOfPoint square : squares) {
            Rect rectangle = Imgproc.boundingRect(square);
            if (rectangle.width >= max_width && rectangle.height >= max_height) {
                max_width = rectangle.width;
                max_height = rectangle.height;
                max_square_idx = currentIndex;
            }
            currentIndex++;
        }
        return max_square_idx;
    }

    // 点到点的距离
    private static double getSpacePointToPoint(Point p1, Point p2) {
        double a = p1.x - p2.x;
        double b = p1.y - p2.y;
        return Math.sqrt(a * a + b * b);
    }

    // 两直线的交点
    private static Point computeIntersect(double[] a, double[] b) {
        if (a.length != 4 || b.length != 4)
            throw new ClassFormatError();
        double x1 = a[0], y1 = a[1], x2 = a[2], y2 = a[3], x3 = b[0], y3 = b[1], x4 = b[2], y4 = b[3];
        double d = ((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4));
        if (d != 0) {
            Point pt = new Point();
            pt.x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
            pt.y = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;
            return pt;
        }
        else
            return new Point(-1, -1);
    }

    // 对多个点按顺时针排序
    private static void sortCorners(List<Point> corners) {
        if (corners.size() == 0) return;
        Point p1 = corners.get(0);
        int index = 0;
        for (int i = 1; i < corners.size(); i++) {
            Point point = corners.get(i);
            if (p1.x > point.x) {
                p1 = point;
                index = i;
            }
        }

        corners.set(index, corners.get(0));
        corners.set(0, p1);

        Point lp = corners.get(0);
        for (int i = 1; i < corners.size(); i++) {
            for (int j = i + 1; j < corners.size(); j++) {
                Point point1 = corners.get(i);
                Point point2 = corners.get(j);
                if ((point1.y-lp.y*1.0)/(point1.x-lp.x)>(point2.y-lp.y*1.0)/(point2.x-lp.x)) {
                    Point temp = point1.clone();
                    corners.set(i, corners.get(j));
                    corners.set(j, temp);
                }
            }
        }
    }

}
