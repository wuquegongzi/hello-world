package com.hb.flink.java.course05;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @ClassName CustomNonParallelSourceJavaFunction
 * @Description 其他方式见scala
 * @Author minglei.chen
 * @Date 2020/2/3 3:04 下午
 * @Version 1.0
 */
public class CustomNonParallelSourceJavaFunction implements SourceFunction<Long> {

    Long count = 1L;
    boolean isRunning = true;

    @Override
    public void run(SourceContext<Long> ctx) throws Exception {
        while (isRunning){
            ctx.collect(count);
            count += 1;
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
