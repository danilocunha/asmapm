package asmapm.queue;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.lang3.SerializationUtils;

import asmapm.config.APMConfig;
import asmapm.model.CallStackTrace;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

	private Logger log = Logger.getLogger(getClass().getName());

	private static Send instance = null;

	private final static String QUEUE_NAME = "asmapmEvents";
	static ConnectionFactory factory;
	static Connection connection;
	static Channel channel;

	public Send() {

	}

	public static Send getInstance() {
		if (instance == null) {
			instance = new Send();
			factory = new ConnectionFactory();

			factory.setHost(APMConfig.getInstance().getValue("rabbitmq.host"));
			factory.setUsername(APMConfig.getInstance().getValue(
					"rabbitmq.user"));
			factory.setPassword(APMConfig.getInstance().getValue(
					"rabbitmq.password"));
			factory.setVirtualHost(APMConfig.getInstance().getValue(
					"rabbitmq.vhost"));

			try {
				connection = factory.newConnection();
				channel = connection.createChannel();
				channel.queueDeclare(QUEUE_NAME, false, false, false, null);
				// String message = "Hello World!";
			} catch (IOException e) {
				
				e.printStackTrace();
			}

		}
		return instance;
	}

	public void testSend(CallStackTrace data) {

			try {
			if (channel == null) {
				log.info("channel nulo nao enviado");
			} else {
				channel.basicPublish("", QUEUE_NAME, null,
						SerializationUtils.serialize(data));
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		// System.out.println("Teste de envio");

	}
}
