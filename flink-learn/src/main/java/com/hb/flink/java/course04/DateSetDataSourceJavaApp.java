package com.hb.flink.java.course04;

import org.apache.flink.api.java.ExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于集合方式 创建数据源
 */
public class DateSetDataSourceJavaApp {

    public static void main(String[] args) throws Exception {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

//        fromCollection(env);
        readFlie(env);
    }

    /**
     * 读取文件 为数据源
     * @param env
     */
    public static void readFlie(ExecutionEnvironment env) throws Exception {

        //基于文件地址
        String fliePath ="file:///Users/leon/Documents/items/test/hello.txt";

        //基于文件夹地址
//        String fliePath ="file:///Users/leon/Documents/items/test";

        env.readTextFile(fliePath).print();
    }


    public static  void fromCollection(ExecutionEnvironment env) throws Exception {

        List<Integer> list = new ArrayList<Integer>(10);

        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
        env.fromCollection(list).print();
    }

}
