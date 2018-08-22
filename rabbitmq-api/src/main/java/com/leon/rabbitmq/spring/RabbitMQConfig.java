package com.leon.rabbitmq.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.leon.rabbitmq.spring.adapter.MessageDelegate;
import com.leon.rabbitmq.spring.converter.TextMessageConverter;
import com.rabbitmq.client.Channel;

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
	
	/**
	 * SpringAMQP 声明  使用
	 *针对消费者的配置
	 *1、设置交换机类型
	 *2、将队列绑定到交换机
	    FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念  
        HeadersExchange ：通过添加属性key-value匹配  
        DirectExchange:按照routingkey分发到指定队列  
        TopicExchange:多关键字匹配  
	 * @return
	 */
	@Bean 
	public TopicExchange exchange001() {
		
		//TopicExchange(name, durable, autoDelete)
		return new TopicExchange("topic001", true, false);
	}
	
	@Bean  
    public Queue queue001() {  
        return new Queue("queue001", true); //队列持久  
    } 
	
	@Bean  
    public Binding binding001() {  
        return BindingBuilder.bind(queue001()).to(exchange001()).with("spring.*");  
    }  
	
	@Bean  
    public TopicExchange exchange002() {  
        return new TopicExchange("topic002", true, false);  
    }  
    
    @Bean  
    public Queue queue002() {  
        return new Queue("queue002", true); //队列持久  
    }
    
    @Bean  
    public Binding binding002() {  
        return BindingBuilder.bind(queue002()).to(exchange002()).with("rabbit.*");  
    } 
    
    @Bean  
    public Queue queue003() {  
        return new Queue("queue003", true); //队列持久  
    }
    
    @Bean  
    public Binding binding003() {  
        return BindingBuilder.bind(queue003()).to(exchange001()).with("mq.*");  
    } 
    
    @Bean  
    public Queue queue_image() {  
        return new Queue("image_queue", true); //队列持久  
    }
    
    @Bean  
    public Queue queue_pdf() {  
        return new Queue("pdf_queue", true); //队列持久  
    }
    
    /**
     * SpringAMQP 消息模板组件
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
    	
    	RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    	return rabbitTemplate;
    }
    
    /**
     * SimpleMessageListenerContainer
           简单消息监听容器
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {
		
    	SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
    	container.setQueues(queue001(),queue002(),queue003());
    	container.setConcurrentConsumers(1);
    	container.setMaxConcurrentConsumers(5);
    	container.setDefaultRequeueRejected(false); //不进入重复队列
    	container.setAcknowledgeMode(AcknowledgeMode.AUTO);//自动签收
    	container.setExposeListenerChannel(true); //是否显性显示
    	//设置标签
    	container.setConsumerTagStrategy(new ConsumerTagStrategy() {

			@Override
			public String createConsumerTag(String queue) {

				return queue + "_" +UUID.randomUUID();
			}
    		
    	});
    	
    	/*
    	 //设置监听
    	//ChannelAwareMessageListener 监听接口
    	container.setMessageListener(new ChannelAwareMessageListener() {
			
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {

              String msg = new String(message.getBody());
              System.out.println("消费者消费的消息："+msg);
				
			}
		});*/
    	
    	/*//适配器方式1
    	//可自定义更换默认方法，默认handleMessage
    	//如果传送 字符串 形式的信息，
    	//需要做自定义转换器,将字节转换为字符串,否则会抛异常, 但是可以发送成功    
    	MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
    	adapter.setDefaultListenerMethod("consumeMessage"); 
    	adapter.setMessageConverter(new TextMessageConverter());
    	container.setMessageListener(adapter);*/
    	
    	//适配器方式2
    	//我们的队列名称 和 方法名称 也可以进行一一的匹配
    	MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
    	adapter.setMessageConverter(new TextMessageConverter());
    	Map<String, String> queueOrTagToMethodName = new HashMap<>();
    	queueOrTagToMethodName.put("queue001", "method1");
    	queueOrTagToMethodName.put("queue002", "method2");
    	adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
    	container.setMessageListener(adapter);    	
    	
    	return container;
    }
	
	
}
