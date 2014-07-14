package com.familiaborges.danilo.apm.collector;

import java.io.IOException;

import asmapm.model.CallStackTrace;
import asmapm.model.CallStackTraceBuilder;

import com.google.gwt.user.rebind.rpc.SerializationUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class RabbitMQCollector implements Runnable {

	private final static String QUEUE_NAME = "hello";

	@Override
	public void run() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("centos1");
		factory.setUsername("admin");
		factory.setPassword("123456");
		
		
		
		Connection connection;
		Channel channel = null;
		QueueingConsumer consumer = null;
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			consumer = new QueueingConsumer(channel);
			channel.basicConsume(QUEUE_NAME, true, consumer);
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
			while (true) {
				
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				CallStackTrace state  = org.apache.commons.lang3.SerializationUtils.deserialize(delivery.getBody());
						//new String(delivery.getBody());
				state.printOnTextFormat();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

	}

}