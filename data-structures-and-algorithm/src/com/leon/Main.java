package com.leon;

import com.leon.strucyures.Array;
import com.leon.strucyures.ArrayStack;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("欢迎来到leon的数据结构世界~ \n ");

//        arrayTest();
          stackTest();

    }

    public static void stackTest(){
        System.out.println("Stack 自我实现的栈 \n");

        ArrayStack<Integer> stack= new ArrayStack<Integer>();
        for (int i = 0; i < 10 ; i++){
            stack.push(i);
            System.out.println(stack);
        }

        stack.pop();
        System.out.println(stack);
        System.out.println(stack.peek());
    }



    public static void arrayTest(){
        System.out.println("Array 自我实现的数组 \n");

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
