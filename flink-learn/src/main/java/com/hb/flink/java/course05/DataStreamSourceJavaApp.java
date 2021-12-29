package com.hb.flink.java.course05;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * DataStream
 */
public class DataStreamSourceJavaApp {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

//        socketFunction(env); //从socket创建datastream
        nonParallelSourceJavaFunction(env); //自定义数据源方式sourcefunction

        env.execute("DataStreamSourceJavaApp");
    }

    /*
      自定义数据源方式sourcefunction
     */
    private static void nonParallelSourceJavaFunction(StreamExecutionEnvironment env) {
        DataStreamSource<Long> data = env.addSource(new CustomNonParallelSourceJavaFunction());
        data.print();
    }

    /**
     * 从socket创建datastream
     * @param env
     */
    private static void socketFunction(StreamExecutionEnvironment env) {

        DataStreamSource<String> data = env.socketTextStream("localhost",9999);
        data.print().setParallelism(1);
    }
}
