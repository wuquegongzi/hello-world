package com.leon;

import com.leon.strucyures.Array;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("欢迎来到leon的数据结构世界~ \n " +
                "Array 自我实现的数组 \n");

        Array<Integer> arr= new Array<Integer>();
        for (int i = 0; i < 10 ; i++){
            arr.addLast(i);
        }
        System.out.println(arr);
        arr.add(0,19);
        System.out.println(arr.toString());
        arr.addFirst(-1);
        System.out.println(arr);
    }

}
