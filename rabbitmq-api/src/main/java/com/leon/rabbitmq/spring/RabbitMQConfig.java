package com.leon.rabbitmq.spring;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
	
	
}
