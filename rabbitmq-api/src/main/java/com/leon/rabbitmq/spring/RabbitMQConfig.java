package com.leon.rabbitmq.spring;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author chenminglei
 *
 */
@Configuration
@ComponentScan({"com.leon.rabbitmq.spring.*"})
public class RabbitMQConfig {

	@Bean
	public ConnectionFactory connectionFactory() {
		
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses("192.168.150.31:5672");
//		connectionFactory.setPort(5672);
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		connectionFactory.setVirtualHost("/");
		
		return connectionFactory;
	}
	
	/**
	 * SpringAMQP用户管理组件-RabbitAdmin应用
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		//必须设置为true
		rabbitAdmin.setAutoStartup(true);
		return rabbitAdmin;
		
	}
}
