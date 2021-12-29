package com.hb.flink.java.demo.stream.example;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class kafkaJob {

    public static void main(String[] args) {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        // only required for Kafka 0.8
        properties.setProperty("zookeeper.connect", "localhost:2181");
        properties.setProperty("group.id", "test");
        //如果为true，则consumer的消费偏移offset会被记录到zookeeper。下次consumer启动时会从此位置继续消费。
        properties.setProperty("auto.commit.enable", "true");

        FlinkKafkaConsumer011<String> myConsumer =
                new FlinkKafkaConsumer011("my_test", new SimpleStringSchema(), properties);

//        myConsumer.setStartFromEarliest();     // start from the earliest record possible
//        myConsumer.setStartFromLatest();       // start from the latest record
//        myConsumer.setStartFromTimestamp(100000); // start from specified epoch timestamp (milliseconds)
//        myConsumer.setStartFromGroupOffsets(); // the default behaviour

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        long nowTime = now.getTime();
        System.out.println("当前时间: " + df.format(now));
        long fetchDataTime = nowTime - 1000 * 60 * 30;  // 计算30分钟之前的时间戳
//        myConsumer.setStartFromTimestamp(fetchDataTime);

        DataStream<String> stream = env
                .addSource(myConsumer);

        stream.print();

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
