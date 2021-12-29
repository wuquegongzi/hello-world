package com.hb.flink.java.course02;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * 使用Java API来开发Flink的实时处理应用程序
 * WC的数据来源于SOCKET
 */
public class StreamingWCArgsJavaApp {

    public static void main(String[] args) throws Exception {

        int port;
        String hostname;

        try{
            ParameterTool tool = ParameterTool.fromArgs(args);
            port = tool.getInt("port");
            hostname  = tool.get("hostname");
        }catch (Exception e){
            e.printStackTrace();
            port = 9999;
            hostname = "localhost";
        }


        //step 1 :获取执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //step2 : 读取数据
        DataStreamSource<String> text = env.socketTextStream(hostname,port);

        //step3 : 数据转换
        text.flatMap(new FlatMapFunction<String, Tuple2<String,Integer>>() {

            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {

                String[] tokens = s.toLowerCase().split(",");
                for (String token : tokens) {
                    if (token.length() > 0){
                        collector.collect(new Tuple2<String,Integer>(token,1));
                    }
                }
            }
            //流处理使用 keyby分组
        }).keyBy(0).timeWindow(Time.seconds(5)).sum(1).print();

        //step4 : 提交执行
        env.execute("StreamingWCJavaApp");

        //test:  nc -lk 9999
    }
}
