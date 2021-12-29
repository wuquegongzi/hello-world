package com.hb.flink.java.course04;

import org.apache.commons.io.FileUtils;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.configuration.Configuration;

import java.io.File;
import java.util.List;

/**
 * 分布式缓存 Java app
 */
public class DistributedCacheJavaApp {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        String filePath = "file:///Users/leon/Documents/items/test/hello.txt";

        //注册一个本地文件/HDFS文件
        env.registerCachedFile(filePath,"pk-java-dc");

        DataSource<String> data = env.fromElements("hadoop","spark","flink");


        data.map(new RichMapFunction<String, String>() {

            @Override
            public void open(Configuration parameters) throws Exception {
                super.open(parameters);
                File file = getRuntimeContext().getDistributedCache().getFile("pk-java-dc");
                List<String> list =  FileUtils.readLines(file);

                for (String str:list
                     ) {
                    System.out.println(str);
                }
            }

            @Override
            public String map(String s) throws Exception {
                return s;
            }
        }).print();
    }
}
