package com.leon.rabbitmq.quickstart;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 生产者
 * @author chenminglei
 *
 */
public class Procuder {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		//1。创建一个connectionFactory,并进行配置
		ConnectionFactory  connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.150.31");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		//2.通过连接工厂创建连接
		Connection connection = connectionFactory.newConnection();
		
		//3.通过connection创建一个Channel
		Channel channel = connection.createChannel();
		
		//4.通过Channel发送数据
		for (int i = 0; i < 200; i++) {
			String msg = i+".Hello RabbitMQ!!!";
			channel.basicPublish("", "test001", null, msg.getBytes());
		}
		
		//5. 关闭连接
		channel.close();
		connection.close();
		
	}

}
