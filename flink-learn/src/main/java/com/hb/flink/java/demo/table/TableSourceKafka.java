package com.hb.flink.java.demo.table;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Kafka;
import org.apache.flink.table.descriptors.Rowtime;
import org.apache.flink.table.descriptors.Schema;

public class TableSourceKafka {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        TableEnvironment tableEnv = StreamTableEnvironment.create(env);

        tableEnv
                // declare the external system to connect to
                .connect(
                        new Kafka()
                                .version("universal")
                                .topic("test1")
                                .startFromEarliest()
                                .property("zookeeper.connect", "localhost:2181")
                                .property("bootstrap.servers", "localhost:9092")
                                .property("group.id", "testGroup")
                )

                // declare a format for this system
//                .withFormat(
//                        new Avro()
//                                .avroSchema(
//                                        "{" +
//                                                "  \"namespace\": \"org.myorganization\"," +
//                                                "  \"type\": \"record\"," +
//                                                "  \"name\": \"UserMessage\"," +
//                                                "    \"fields\": [" +
//                                                "      {\"name\": \"timestamp\", \"type\": \"string\"}," +
//                                                "      {\"name\": \"user\", \"type\": \"long\"}," +
//                                                "      {\"name\": \"message\", \"type\": [\"string\", \"null\"]}" +
//                                                "    ]" +
//                                                "}"
//                                )
//                )

                // declare the schema of the table
                .withSchema(
                        new Schema()
                                .field("rowtime", Types.SQL_TIMESTAMP)
                                .rowtime(new Rowtime()
                                        .timestampsFromField("timestamp")
                                        .watermarksPeriodicBounded(60000)
                                )
                                .field("user", Types.LONG)
                                .field("message", Types.STRING)
                )

                // specify the update-mode for streaming tables
                .inAppendMode()

                // register as source, sink, or both and under a name
                .registerTableSource("MyUserTable");

//        JDBCAppendTableSink sink = JDBCAppendTableSink.builder()
//                .setDrivername("org.apache.derby.jdbc.EmbeddedDriver")
//                .setDBUrl("jdbc:derby:memory:ebookshop")
//                .setQuery("INSERT INTO books (id) VALUES (?)")
//                .setParameterTypes(INT_TYPE_INFO)
//                .build();
//
//        tableEnv.registerTableSink(
//                "jdbcOutputTable",
//                // specify table schema
//                new String[]{"id"},
//                new TypeInformation[]{Types.INT},
//                sink);
//
//        Table table = ...
//        table.insertInto("jdbcOutputTable");

        env.execute("test kafka source job");
    }

}
