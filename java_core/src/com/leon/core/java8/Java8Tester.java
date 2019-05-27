package com.leon.core.java8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * JAVA8 编程风格
 */
public class Java8Tester {

    public static void main(String[] args) {

        List<String> names = new ArrayList<String>();
        names.add("Google ");
        names.add("Runoob ");
        names.add("Taobao ");
        names.add("Baidu ");
        names.add("Sina ");

        Java8Tester tester = new Java8Tester();
        tester.sortUsingJava7(names);
        System.out.println("使用7排序:"+names);

        names = new ArrayList<String>();
        names.add("Google ");
        names.add("Runoob ");
        names.add("Taobao ");
        names.add("Baidu ");
        names.add("Sina ");
        tester.sortUsingJava8(names);
        System.out.println("使用8排序:"+names);

    }

    /**
     * 使用Java 7 排序
     * @param names
     */
    private void sortUsingJava7(List<String> names){

        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    /**
     * 使用Java 8 排序
     * @param names
     */
    private void sortUsingJava8(List<String> names){
        Collections.sort(names,(s1,s2) -> s1.compareTo(s2));
    }
}


