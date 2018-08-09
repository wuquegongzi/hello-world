package com.leon.algorithm;


import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 * @package: com.leon.sort
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/8/9 14:52
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class SortAlgorithm<E> {

    /**
     * 冒泡排序
     * 算法描述
     *         1.比较相邻的元素。如果第一个比第二个大，就交换它们两个；
     *         2.对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
     *         3.针对所有的元素重复以上的步骤，除了最后一个；
     *         4.重复步骤1~3，直到排序完成。
     * 算法分析 最佳情况：T(n) = O(n)
     *        最差情况：T(n) = O(n2)
     *        平均情况：T(n) = O(n2)
     * @param array
     * @return
     */
    public static int[] bubbleSort(int[] array){
        if ( array.length == 0 ){
           return array;
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if(array[j + 1] < array[j]){
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }

    /**
     * 选择排序
     * 算法描述
     *        1.初始状态：无序区为R[1..n]，有序区为空；
     *        2.第i趟排序(i=1,2,3…n-1)开始时，当前有序区和无序区分别为R[1..i-1]和R(i..n）。
     *           该趟排序从当前无序区中-选出关键字最小的记录 R[k]，将它与无序区的第1个记录R交换，
     *           使R[1..i]和R[i+1..n)分别变为记录个数增加1个的新有序区和记录个数减少1个的新无序区；
     *        3.n-1趟结束，数组有序化了。
     * 算法分析
     *       最佳情况：T(n) = O(n2)
     *       最差情况：T(n) = O(n2)
     *       平均情况：T(n) = O(n2)
     * @param array
     * @return
     */
    public static int[] selectionSort(int[] array){
        if (array.length == 0){
            return array;
        }
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                //找到最小的数
                if(array[j] < array[minIndex]){
                    //将最小数的索引保存
                    minIndex = j;
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }

        return array;
    }

    /**
     * 插入排序
     * 算法描述
     *       1.从第一个元素开始，该元素可以认为已经被排序；
     *       2.取出下一个元素，在已经排序的元素序列中从后向前扫描；
     *       3.如果该元素（已排序）大于新元素，将该元素移到下一位置；
     *       4.重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
     *       5.将新元素插入到该位置后；
     *       6.重复步骤2~5。
     *算法分析
     *       最佳情况：T(n) = O(n)
     *       最坏情况：T(n) = O(n2)
     *       平均情况：T(n) = O(n2)
     * @param array
     * @return
     */
    public static int[] insertionSort(int[] array){
        if (array.length == 0){
            return array;
        }

        int current;
        for (int i = 0; i < array.length - 1; i++) {
            current = array[i + 1];
            int preIndex = i;
            while(preIndex >= 0 && current < array[preIndex]){
                 array[preIndex + 1] = array[preIndex];
                 preIndex --;
            }
            array[preIndex + 1] = current;
        }

        return array;
    }


    /**
     * 希尔排序
     * 算法描述
     *     我们选择增量gap=length/2，缩小增量继续以gap = gap/2的方式，
     *     这种增量选择我们可以用一个序列来表示，{n/2,(n/2)/2...1}，称为增量序列。
     *     希尔排序的增量序列的选择与证明是个数学难题，
     *     我们选择的这个增量序列是比较常用的，也是希尔建议的增量，称为希尔增量，
     *     但其实这个增量序列不是最优的。此处我们做示例使用希尔增量。
     *     先将整个待排序的记录序列分割成为若干子序列分别进行直接插入排序，具体算法描述：
     *
     *     1.选择一个增量序列t1，t2，…，tk，其中ti>tj，tk=1；
     *     2.按增量序列个数k，对序列进行k 趟排序；
     *     3.每趟排序，根据对应的增量ti，将待排序列分割成若干长度为m 的子序列，分别对各子表进行直接插入排序。
     *       仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。
     * @param array
     * @return
     */
    public static int[] ShellSort(int[] array){

        int len = array.length;
        int temp,gap = len / 2;
        while (gap > 0){
            for (int i = gap; i < len; i++) {
                 temp = array[i];
                 int preIndex = i - gap;
                 while (preIndex >= 0 && array[preIndex] > temp){
                     array[preIndex + gap] = array[preIndex];
                     preIndex -= gap;
                 }
                array[preIndex + gap] = temp;
            }
            gap /= 2;
        }

        return array;
    }


    /**
     * 转字符串
     * @param array
     * @return
     */
    public static String toString(int[] array){
        StringBuilder res = new StringBuilder();
        res.append("[");
        for (int i =0 ; i < array.length ; i ++){
            res.append(array[i]);
            if(i != array.length -1){
                res.append(",");
            }
        }
        res.append("]");

        return res.toString();
    }

    public static void main(String[] args) {

        int[] array = new int[100];

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            array[i] = random.nextInt(9)+i;
        }

        System.out.println("排序前："+toString(array));
        System.out.println("冒泡后："+toString(bubbleSort(array)));
    }


}
