package com.hb.flink.java.course03;

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
public class StreamingWCKeyByFieldJavaApp {

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
        text.flatMap(new FlatMapFunction<String, WC>() {

            @Override
            public void flatMap(String s, Collector<WC> collector) throws Exception {
                String[] tokens = s.toLowerCase().split(",");
                for (String token : tokens) {
                    if (token.length() > 0){
                        collector.collect(new WC(token,1));
                    }
                }
            }

            //流处理使用 keyby分组
        }).keyBy("word")
                .timeWindow(Time.seconds(5))
                .sum("count")
                .print()
                .setParallelism(1);

        //step4 : 提交执行
        env.execute("StreamingWCJavaApp");

        //test:  nc -lk 9999
    }

    public static class WC{

        private String word;
        private int count;


        public WC(String word,int count) {
            this.count = count;
            this.word = word;
        }

        public WC() {
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        @Override
        public String toString() {
            return "WC{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
