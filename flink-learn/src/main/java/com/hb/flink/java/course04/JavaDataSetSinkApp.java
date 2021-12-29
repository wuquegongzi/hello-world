package com.hb.flink.java.course04;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.core.fs.FileSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * sink DataSet
 */
public class JavaDataSetSinkApp {

    public static void main(String[] args) throws Exception {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        List<Integer> list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        String filePath = "/Users/leon/Documents/items/test/sink-out-java";
        DataSource data = env.fromCollection(list);

        data.writeAsText(filePath, FileSystem.WriteMode.OVERWRITE);

        env.execute("JavaDataSetSinkApp");
    }
}
