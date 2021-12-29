package com.hb.flink.java.course05;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DataStreamTransformationJavaApp
 * @Description TODO
 * @Author minglei.chen
 * @Date 2020/2/3 3:23 下午
 * @Version 1.0
 */
public class DataStreamTransformationJavaApp {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //设置窗口时间类型，默认类型是ProcessingTime
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

//        filterFunction(env);
//        unionFUnction(env);
        splitAndSelectFunction(env);

        env.execute("DataStreamTransformationJavaApp");
    }

    /**
     * split 和 Select
     * @param env
     */
    static void splitAndSelectFunction(StreamExecutionEnvironment env) {
        DataStreamSource<Long> data = env.addSource(new CustomNonParallelSourceJavaFunction());

        SplitStream<Long> splits = data.split(new OutputSelector<Long>() {
            @Override
            public Iterable<String> select(Long value) {
                List list = new ArrayList<String>();
                if(value % 2 == 0){
                    list.add("even");
                }else{
                    list.add("odd");
                }
                return list;
            }
        });

        splits.select("odd").print();

    }

    /**
     * union
     * @param env
     */
    static void unionFUnction(StreamExecutionEnvironment env) {
        DataStreamSource<Long> data1 = env.addSource(new CustomNonParallelSourceJavaFunction());
        DataStreamSource<Long> data2 = env.addSource(new CustomNonParallelSourceJavaFunction());

        data1.union(data2).print().setParallelism(1);
    }


    /**
     * filter
     * @param env
     */
    static void filterFunction(StreamExecutionEnvironment env) {

        DataStreamSource<Long> data = env.addSource(new CustomNonParallelSourceJavaFunction());

        data.map(new MapFunction<Long, Long>() {
            @Override
            public Long map(Long a) throws Exception {
                System.out.println("接收到："+a);
                return a;
            }
        }).filter(new FilterFunction<Long>() {
            @Override
            public boolean filter(Long a) throws Exception {
                if(a%2==0) {
                    return true;
                }
                return false;
            }
        }).print().setParallelism(1);
    }
}
