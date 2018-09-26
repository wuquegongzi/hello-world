package com.leon.leetcode;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * 350. 两个数组的交集 II
 * @package: com.leon.leetcode
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/9/26 18:07
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class L350_Intersect {

    public int[] intersect(int[] nums1, int[] nums2) {

        TreeMap<Integer,Integer> map = new TreeMap<Integer,Integer>();

        for (int num: nums1) {
            if(!map.containsKey(num)){
                map.put(num,1);
            }else{
                map.put(num,map.get(num) + 1);
            }
        }

        ArrayList<Integer> list = new ArrayList<>();
        for (int num: nums2) {
            if(map.containsKey(num)){
                list.add(num);
                map.put(num, map.get(num) - 1);
                if(map.get(num) == 0){
                    map.remove(num);
                }
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

        L350_Intersect Intersect = new L350_Intersect();

        int[] result = Intersect.intersect(nums1,nums2);

        for (int num:result) {
            System.out.println(num);
        }

    }
}
