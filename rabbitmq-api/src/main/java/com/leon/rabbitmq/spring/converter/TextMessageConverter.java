package com.leon.rabbitmq.spring.converter;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * Text转换器
 * @author chenminglei
 *
 */
public class TextMessageConverter implements MessageConverter {

	@Override
	public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
		
		return new Message(object.toString().getBytes(), messageProperties); 
	}

	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		
		String contentType = message.getMessageProperties().getContentType();
		if(null != contentType && contentType.contains("text")) {
			return new String(message.getBody());
		}
		return message.getBody();
	}

}
