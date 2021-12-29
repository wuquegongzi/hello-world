package com.hb.flink.java.demo.stream.example;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

public class CsvJob {

    public static void main(String[] args) {
       final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> stream = env.readTextFile("/Users/leon/Downloads/iris.csv");

//        dsSource.writeAsText("/Users/leon/Downloads/666.txt");
//        stream.print();

        FlinkKafkaProducer011<String> myProducer = new FlinkKafkaProducer011<String>(
                "localhost:9092",            // broker list
                "my_test",                  // target topic
                new SimpleStringSchema());   // serialization schema

// versions 0.10+ allow attaching the records' event timestamp when writing them to Kafka;
// this method is not available for earlier Kafka versions
        myProducer.setWriteTimestampToKafka(true);

        stream.addSink(myProducer);
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
