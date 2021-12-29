package com.hb.flink.java.course07;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;


/**
 * @ClassName WindowsJavaApp
 * @Description TODO
 * @Author minglei.chen
 * @Date 2020/2/4 6:06 下午
 * @Version 1.0
 */
public class WindowsJavaApp {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> text = env.socketTextStream("localhost",9999);

        text.flatMap(new FlatMapFunction<String, Tuple2<String,Integer>>() {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
               String splits[] = value.split(",");
                for (int i = 0; i < splits.length; i++) {
                    out.collect(new Tuple2<>(splits[i],1));
                }
            }
        }).keyBy(0)
//                .timeWindow(Time.seconds(5)) 滚动窗口
                .timeWindow(Time.seconds(10),Time.seconds(5)) //滑动窗口，数据可能有重叠
                .sum(1)
                .print()
                .setParallelism(1);

        env.execute("WindowsJavaApp");
    }
}
