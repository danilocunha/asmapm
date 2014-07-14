package asmapm.queue;

import java.io.IOException;

import org.apache.commons.lang3.SerializationUtils;

import asmapm.model.CallStackTrace;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

	private static Send instance = null;

	private final static String QUEUE_NAME = "hello";
	static ConnectionFactory factory;
	static Connection connection;
	static Channel channel;

	public Send() {

	}

	public static Send getInstance() {
		if (instance == null) {
			instance = new Send();
			factory = new ConnectionFactory();

			factory.setHost("centos1");
			factory.setUsername("admin");
			factory.setPassword("123456");

			try {
				connection = factory.newConnection();
				channel = connection.createChannel();
				channel.queueDeclare(QUEUE_NAME, false, false, false, null);
				// String message = "Hello World!";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return instance;
	}

	public void testSend(CallStackTrace data) {

		/*
		 * try { channel.basicPublish("", QUEUE_NAME, null,
		 * SerializationUtils.serialize(data)); System.out.println("Enviado"); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		try {
			channel.basicPublish("", QUEUE_NAME, null,
					 SerializationUtils.serialize(data));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Teste de envio");

	}
}
