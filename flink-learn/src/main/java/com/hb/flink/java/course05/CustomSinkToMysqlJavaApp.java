package com.hb.flink.java.course05;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @ClassName CustomSInkToMysqlJavaApp
 * @Description 自定义Sink到mysql
 * @Author minglei.chen
 * @Date 2020/2/3 6:55 下午
 * @Version 1.0
 */
public class CustomSinkToMysqlJavaApp {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source = env.socketTextStream("localhost",8888);

        SingleOutputStreamOperator<Student>  streamOperator = source.map(new MapFunction<String, Student>() {
            @Override
            public Student map(String s) throws Exception {

                if(null == s){
                    return null;
                }
                String splits[] = s.split(",");

                Student student = new Student();
                student.setId(Integer.valueOf(splits[0]));
                student.setName(splits[1]);
                student.setAge(Integer.valueOf(splits[2]));

                return student;
            }
        });

        streamOperator.addSink(new SinkToMySQL());

        env.execute("CustomSinkToMysqlJavaApp");
    }

}
