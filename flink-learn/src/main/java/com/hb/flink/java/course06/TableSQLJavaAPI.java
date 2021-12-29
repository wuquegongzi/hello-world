package com.hb.flink.java.course06;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.types.Row;

/**
 * @ClassName TableSQLJavaAPI
 * @Description TODO
 * @Author minglei.chen
 * @Date 2020/2/4 11:09 上午
 * @Version 1.0
 */
public class TableSQLJavaAPI {

    public static void main(String[] args) throws Exception {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tableEnv = BatchTableEnvironment.create(env);

        String filePath = "file:///Users/leon/Documents/items/test/people.csv";

        DataSource<People> csv = env.readCsvFile(filePath).pojoType(People.class,"name","age","year");

//        csv.print();
        Table peopleTable = tableEnv.fromDataSet(csv);
        tableEnv.registerTable("people",peopleTable);
        Table resultTable = tableEnv.sqlQuery("select * from people where name='leon'");

        DataSet<Row> result = tableEnv.toDataSet(resultTable, Row.class);

        result.print();
    }
}
