package com.hb.flink.java.demo.stream.example;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.streaming.connectors.wikiedits.WikipediaEditEvent;
import org.apache.flink.streaming.connectors.wikiedits.WikipediaEditsSource;

/**
 *  @author: minglei.chen
 *  @Date: 2019/12/16 11:28 上午
 *  @Description:第一个Flink程序
 */
public class WikipediaAnalysis {

    public static void main(String[] args) {

        StreamExecutionEnvironment see = StreamExecutionEnvironment.getExecutionEnvironment();

        //创建一个从Wikipedia IRC日志中读取的源
        DataStream<WikipediaEditEvent> edits = see.addSource(new WikipediaEditsSource());

        //要为Stream输入密钥，我们必须提供KeySelector
        //提供一个WikipediaEditEvent具有String密钥的流，即用户名
        KeyedStream<WikipediaEditEvent, String> keyedEdits = edits
                .keyBy(new KeySelector<WikipediaEditEvent, String>() {
                    @Override
                    public String getKey(WikipediaEditEvent event) {
                        return event.getUser();
                    }
                });

        DataStream<Tuple2<String, Long>> result = keyedEdits
                //第一个调用.timeWindow()表示我们希望有五秒钟的翻滚（不重叠）窗口
                .timeWindow(Time.seconds(5))
                .aggregate(new AggregateFunction<WikipediaEditEvent, Tuple2<String, Long>, Tuple2<String, Long>>() {
                    @Override
                    public Tuple2<String, Long> createAccumulator() {
                        return new Tuple2<>("", 0L);
                    }

                    @Override
                    public Tuple2<String, Long> add(WikipediaEditEvent value, Tuple2<String, Long> accumulator) {
                        accumulator.f0 = value.getUser();
                        accumulator.f1 += value.getByteDiff();
                        return accumulator;
                    }

                    @Override
                    public Tuple2<String, Long> getResult(Tuple2<String, Long> accumulator) {
                        return accumulator;
                    }

                    @Override
                    public Tuple2<String, Long> merge(Tuple2<String, Long> a, Tuple2<String, Long> b) {
                        return new Tuple2<>(a.f0, a.f1 + b.f1);
                    }
                });

//        result.print();

        result
                .map(new MapFunction<Tuple2<String,Long>, String>() {
                    @Override
                    public String map(Tuple2<String, Long> tuple) {
                        return tuple.toString();
                    }
                })
                .addSink(new FlinkKafkaProducer011<>("localhost:9092", "my_test", new SimpleStringSchema()));

        try {
            see.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
