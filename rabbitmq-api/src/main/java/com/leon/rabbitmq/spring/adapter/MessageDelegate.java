package com.leon.rabbitmq.spring.adapter;

/**
 * 消息适配器 自定义
 * @author chenminglei
 *
 */
public class MessageDelegate {
	
	/**
	 * Out-of-the-box value for the default listener method: "handleMessage".
	 * public static final String ORIGINAL_DEFAULT_LISTENER_METHOD = "handleMessage";
	 */
	public void handleMessage(byte[] messageBody) {
		System.err.println("默认方法, 消息内容:" + new String(messageBody));
	}
	
	public void consumeMessage(byte[] messageBody) {
		System.err.println("字节数组方法, 消息内容:" + new String(messageBody));
	}
	
	public void consumeMessage(String messageBody) {
		System.err.println("字符串方法, 消息内容:" + messageBody);
	}
	
	public void method1(String messageBody) {
		System.err.println("method1 收到消息内容:" + new String(messageBody));
	}
	
	public void method2(String messageBody) {
		System.err.println("method2 收到消息内容:" + new String(messageBody));
	}
	

}
