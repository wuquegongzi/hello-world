package com.hb.flink.java.course05;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * @ClassName SinkToMySQL
 * @Description 自定义MySQL Sink
 *
 * 1、RichSinkFunction<T> T是写入对象的类型
 * 2、open/close 生命周期方法
 *
 *
 * @Author minglei.chen
 * @Date 2020/2/3 7:04 下午
 * @Version 1.0
 */
public class SinkToMySQL extends RichSinkFunction<Student> {

    Connection connection;
    PreparedStatement pstmt;


    private Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/test";

            conn = DriverManager.getConnection(url,"root","123456");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * 在open中建立连接
     * @param parameters
     * @throws Exception
     */
    @Override
    public void open(Configuration parameters) throws Exception {

        super.open(parameters);

        connection = getConnection();
        String sql = "insert into student(name,age) values (?,?)";
        pstmt = connection.prepareStatement(sql);

        System.out.println("open");
    }

    /**
     *  每条记录插入时调用一次
     * @param value
     * @param context
     * @throws Exception
     */
    @Override
    public void invoke(Student value, Context context) throws Exception {
        System.out.println("invoke~~~~~~~~~");

        if(null != value){
            // 未前面的占位符赋值
//            pstmt.setInt(1, value.getId());
            pstmt.setString(1, value.getName());
            pstmt.setInt(2, value.getAge());

            pstmt.executeUpdate();
        }
    }

    /**
     * 在close方法中要释放资源
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        super.close();


        if(pstmt != null) {
            pstmt.close();
        }

        if(connection != null) {
            connection.close();
        }
    }
}
