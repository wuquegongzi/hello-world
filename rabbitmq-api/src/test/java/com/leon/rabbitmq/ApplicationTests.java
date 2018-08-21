package com.leon.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Test
	public void contextLoads() {
		
	}
	
	@Autowired
	private RabbitAdmin rabbitAdmin;
	
	@Test
	public void testAdmin() throws Exception{
		
		rabbitAdmin.declareExchange(new DirectExchange("test.direct", false, false));
		rabbitAdmin.declareExchange(new TopicExchange("test.topic", false, false));
		rabbitAdmin.declareExchange(new FanoutExchange("test.fanout", false, false));
		
		rabbitAdmin.declareQueue(new Queue("test.direct.queue",false));
		rabbitAdmin.declareQueue(new Queue("test.topic.queue",false));
		rabbitAdmin.declareQueue(new Queue("test.fanout.queue",false));
		
		String destination = "test.direct.queue";
		DestinationType destinationType = Binding.DestinationType.QUEUE;
		String exchange = "test.direct";
		String routingKey = "direct";
		Map<String, Object> arguments = new HashMap<>();
		Binding binding = new Binding(destination, destinationType, exchange, routingKey, arguments);
		rabbitAdmin.declareBinding(binding);
		
	
		rabbitAdmin.declareBinding(
				BindingBuilder
				.bind(new Queue("test.topic.queue", false))		//直接创建队列
				.to(new TopicExchange("test.topic", false, false))	//直接创建交换机 建立关联关系
				.with("user.#"));	//指定路由Key
		
		rabbitAdmin.declareBinding(
				BindingBuilder
				.bind(new Queue("test.fanout.queue", false))		
				.to(new FanoutExchange("test.fanout", false, false)));
		
		//清空队列数据
		rabbitAdmin.purgeQueue("test.topic.queue", false);
	}

}
