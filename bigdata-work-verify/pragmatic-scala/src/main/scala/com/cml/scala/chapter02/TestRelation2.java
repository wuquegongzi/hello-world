package com.cml.scala.chapter02;

/**
 * @author wuque
 * @title: TestRelation2
 * @projectName pragmatic-scala
 * @description:
 * @date 2022/11/2414:41
 */
public class TestRelation2 {

//    Java：==比较两个变量本身的值，即两个对象在内存中的首地址；equals比较字符串中所包含的内容是否相同。
    public static void main(String[] args) {
        String s1 = "hhhh";
        String s2 = new String("hhhh");

        System.out.println(s1 == s2);   // false
        System.out.println(s1.equals(s2));  // true

    }
}
