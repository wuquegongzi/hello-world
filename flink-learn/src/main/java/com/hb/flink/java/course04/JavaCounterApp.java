package com.hb.flink.java.course04;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.accumulators.LongCounter;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.FileSystem;

/**
 * 计数器
 * 基于flink编程的计时器之java实现
 */
public class JavaCounterApp {

    public static void main(String[] args) throws Exception {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<String> data = env.fromElements("hadoop","flink");

        DataSet info = data.map(new RichMapFunction<String, String>() {

            LongCounter counter = new LongCounter();

            @Override
            public void open(Configuration parameters) throws Exception {
                super.open(parameters);

                getRuntimeContext().addAccumulator("ele-counts-java",counter);
            }

            @Override
            public String map(String s) throws Exception {

                counter.add(1);
                return s;
            }

        });

        String filePath = "file:///Users/leon/Documents/items/test/counter-app-java";
        info.writeAsText(filePath,FileSystem.WriteMode.OVERWRITE);

        JobExecutionResult jobResult = env.execute("CounterApp");

        long num = (Long)jobResult.getAccumulatorResult("ele-counts-java");

        System.out.println(num);
    }
}
