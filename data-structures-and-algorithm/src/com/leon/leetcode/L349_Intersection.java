package com.leon.leetcode;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * 349. 两个数组的交集
 * @package: com.leon.leetcode
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/9/26 17:54
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class L349_Intersection {

    public int[] intersection(int[] nums1, int[] nums2) {

        TreeSet<Integer> set =new TreeSet<>();
        for (int num: nums1){
            set.add(num);
        }

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int num: nums2){
            if(set.contains(num)){
                list.add(num);
                set.remove(num);
            }
        }

        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    public static void main(String[] args) {

        int[] nums1 = {1,2,2,1};
        int[] nums2 = {2,2,9,0,1};

        L349_Intersection Intersection = new L349_Intersection();

        int[] result = Intersection.intersection(nums1,nums2);

        for (int num:result) {
            System.out.println(num);
        }

    }
}
