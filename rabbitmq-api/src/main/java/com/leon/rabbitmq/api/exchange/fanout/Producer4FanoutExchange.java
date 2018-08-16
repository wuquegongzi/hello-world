package com.leon.rabbitmq.api.exchange.fanout;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 交换机  Fanout模式  生产者
 * @author chenminglei
 *
 */
public class Producer4FanoutExchange {
	
	 public static void main(String[] args) throws IOException, TimeoutException {
		 
		//1 创建ConnectionFactory
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.150.31");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		//2 创建Connection
		Connection connection = connectionFactory.newConnection();
		//3 创建Channel
		Channel channel = connection.createChannel();  
		//4 声明
		String exchangeName = "test_fanout_exchange";
		//5 发送
		for(int i = 0; i < 10; i ++) {
			String msg = i+".Hello World RabbitMQ 4 FANOUT Exchange Message ...";
			channel.basicPublish(exchangeName, "", null , msg.getBytes()); 			
		}
		
		channel.close();  
        connection.close();  
        
	 }
}
