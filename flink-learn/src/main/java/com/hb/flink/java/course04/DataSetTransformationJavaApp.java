package com.hb.flink.java.course04;

import com.hb.flink.scala.course04.DBUtils;
import org.apache.flink.api.common.functions.*;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

/**
 * 转换函数
 */
public class DataSetTransformationJavaApp {


    public static void main(String[] args) throws Exception {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

//        mapFunction(env); //map
//        filterFunction(env); //filter
//        mapPartitionFunction(env); //mapPartition
//        firstFunction(env); //first
//        flatMapFunction(env); //flatMap
//        distinctFunction(env); //distinct
//        joinFunction(env);
        outerJoinFunction(env);
    }

    /**
     * map
     * @param env
     */
    public static void mapFunction(ExecutionEnvironment env) throws Exception {

        List<Integer> list = new ArrayList<>(10);

        for (int i = 1; i < 11; i++) {
            list.add(i);
        }

        DataSource<Integer> data =  env.fromCollection(list);
        data.map(new MapFunction<Integer, Integer>() {

            @Override
            public Integer map(Integer input) throws Exception {

                return input + 1;
            }
        }).print();

    }

    /**
     * filter
     * @param env
     */
    public static void filterFunction(ExecutionEnvironment env) throws Exception {

        List<Integer> list = new ArrayList<>(10);

        for (int i = 1; i < 11; i++) {
            list.add(i);
        }

        DataSource<Integer> data =  env.fromCollection(list);
        data.map(new MapFunction<Integer, Integer>() {

            @Override
            public Integer map(Integer input) throws Exception {

                return input + 1;
            }
        }).filter(new FilterFunction<Integer>() {
            @Override
            public boolean filter(Integer input) throws Exception {
                return input > 5;
            }
        }).print();

    }

    /**
     * mapPartition
     * @param env
     */
    public static  void mapPartitionFunction(ExecutionEnvironment env) throws Exception {

        List<String> list = new ArrayList<String>(100);

        for (int i = 1; i < 101; i++) {
            list.add("student:"+i);
        }

        DataSource<String> data
                = env.fromCollection(list)
                    .setParallelism(2);

        data.mapPartition(new MapPartitionFunction<String, String>() {
            @Override
            public void mapPartition(Iterable<String> iterable, Collector<String> collector) throws Exception {

                String conn = DBUtils.getConnection();

                System.out.println("打开连接："+conn);
                DBUtils.returnConnection(conn);
            }
        }).print();
    }


    /**
     * first
     * @param env
     * @throws Exception
     */
    public static  void firstFunction(ExecutionEnvironment env) throws Exception {

        List<Tuple2<Integer,String>> list = new ArrayList<Tuple2<Integer,String>>(100);

        list.add(new Tuple2<>(1,"hadoop"));
        list.add(new Tuple2<>(1,"spark"));
        list.add(new Tuple2<>(1,"flink"));
        list.add(new Tuple2<>(2,"java"));
        list.add(new Tuple2<>(2,"spring boot"));
        list.add(new Tuple2<>(3,"linux"));
        list.add(new Tuple2<>(4,"vue"));

        DataSource<Tuple2<Integer,String>> data
                = env.fromCollection(list)
                .setParallelism(2);

//        data.first(2).print();
        data.groupBy(0).sortGroup(1, Order.ASCENDING).first(3).print();
    }

    /**
     * flatMap
     * @param env
     * @throws Exception
     */
    public static  void flatMapFunction(ExecutionEnvironment env) throws Exception {

        List<String> list = new ArrayList<String>(100);

        list.add("hadoop,spark");
        list.add("hadoop,flink");
        list.add("flink,flink");

        DataSource<String> data
                = env.fromCollection(list)
                .setParallelism(2);

        data.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                String splits[] = s.split(",");

                for (String split : splits
                ) {
                    collector.collect(split);
                }
            }
        }).map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String s) throws Exception {
                return new Tuple2<String, Integer>(s, 1);
            }
        }).groupBy(0).sum(1).print();

    }


    /**
     * distinct
     * @param env
     * @throws Exception
     */
    private static void distinctFunction(ExecutionEnvironment env) throws Exception {

        List<String> list = new ArrayList<String>(100);

        list.add("hadoop,spark");
        list.add("hadoop,flink");
        list.add("flink,flink");

        DataSource<String> data
                = env.fromCollection(list)
                .setParallelism(2);

        data.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                String splits[] = s.split(",");

                for (String split : splits
                ) {
                    collector.collect(split);
                }
            }
        }).distinct().print();
    }


    /**
     * join
     * @param env
     * @throws Exception
     */
    public static  void joinFunction(ExecutionEnvironment env) throws Exception {

        List<Tuple2<Integer,String>> list = new ArrayList<Tuple2<Integer,String>>(100);

        list.add(new Tuple2<>(1,"leon"));
        list.add(new Tuple2<>(2,"cml"));
        list.add(new Tuple2<>(3,"afra"));
        list.add(new Tuple2<>(4,"haibao"));
        list.add(new Tuple2<>(5,"lei"));


        List<Tuple2<Integer,String>> list2 = new ArrayList<Tuple2<Integer,String>>(100);

        list2.add(new Tuple2<>(1,"北京"));
        list2.add(new Tuple2<>(2,"上海"));
        list2.add(new Tuple2<>(3,"杭州"));
        list2.add(new Tuple2<>(6,"郑州"));

        DataSource<Tuple2<Integer,String>> data
                = env.fromCollection(list);

        DataSource<Tuple2<Integer,String>> data2
                = env.fromCollection(list2);

        data.join(data2)
                .where(0)
                .equalTo(0)
                .with(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Object>() {

                    @Override
                    public Tuple3<Integer,String,String> join(Tuple2<Integer, String> t1, Tuple2<Integer, String> t2) throws Exception {

                        return new Tuple3<Integer,String,String>(t1.f0,t1.f1,t2.f1);
                    }
        }).print();
       }


    /**
     * outerJoin
     * @param env
     * @throws Exception
     */
    public static  void outerJoinFunction(ExecutionEnvironment env) throws Exception {

        List<Tuple2<Integer,String>> list = new ArrayList<Tuple2<Integer,String>>(100);

        list.add(new Tuple2<>(1,"leon"));
        list.add(new Tuple2<>(2,"cml"));
        list.add(new Tuple2<>(3,"afra"));
        list.add(new Tuple2<>(4,"haibao"));
        list.add(new Tuple2<>(5,"lei"));


        List<Tuple2<Integer,String>> list2 = new ArrayList<Tuple2<Integer,String>>(100);

        list2.add(new Tuple2<>(1,"北京"));
        list2.add(new Tuple2<>(2,"上海"));
        list2.add(new Tuple2<>(3,"杭州"));
        list2.add(new Tuple2<>(6,"郑州"));

        DataSource<Tuple2<Integer,String>> data
                = env.fromCollection(list);

        DataSource<Tuple2<Integer,String>> data2
                = env.fromCollection(list2);

        System.out.println("左连接");
        data.leftOuterJoin(data2)
                .where(0)
                .equalTo(0)
                .with(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Object>() {

                    @Override
                    public Tuple3<Integer,String,String> join(Tuple2<Integer, String> t1, Tuple2<Integer, String> t2) throws Exception {
                        Tuple3<Integer,String,String> t3;
                        if ( null == t2 ){
                            t3 = new Tuple3<Integer,String,String>(t1.f0,t1.f1,"-");
                        }else{
                            t3 =  new Tuple3<Integer,String,String>(t1.f0,t1.f1,t2.f1);
                        }
                        return t3;
                    }
                }).print();

        System.out.println("右连接");
        data.rightOuterJoin(data2)
                .where(0)
                .equalTo(0)
                .with(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Object>() {

                    @Override
                    public Tuple3<Integer,String,String> join(Tuple2<Integer, String> t1, Tuple2<Integer, String> t2) throws Exception {
                        Tuple3<Integer,String,String> t3;
                        if ( null == t1 ){
                            t3 = new Tuple3<Integer,String,String>(t2.f0,"-",t2.f1);
                        }else{
                            t3 =  new Tuple3<Integer,String,String>(t1.f0,t1.f1,t2.f1);
                        }
                        return t3;
                    }
                }).print();

        System.out.println("全连接");
        data.fullOuterJoin(data2)
                .where(0)
                .equalTo(0)
                .with(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Object>() {

                    @Override
                    public Tuple3<Integer,String,String> join(Tuple2<Integer, String> t1, Tuple2<Integer, String> t2) throws Exception {
                        Tuple3<Integer,String,String> t3;
                        if ( null == t1 ){
                            t3 = new Tuple3<Integer,String,String>(t2.f0,"-",t2.f1);
                        }else if ( null == t2 ){
                            t3 = new Tuple3<Integer,String,String>(t1.f0,t1.f1,"-");
                        }else{
                            t3 =  new Tuple3<Integer,String,String>(t1.f0,t1.f1,t2.f1);
                        }
                        return t3;
                    }
                }).print();
    }

}
