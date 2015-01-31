package asmapm.queue;

import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.stream.FactoryConfigurationError;

import org.apache.commons.lang3.SerializationUtils;

import asmapm.Agent;
import asmapm.config.APMConfig;
import asmapm.model.CallStackTrace;

import com.rabbitmq.client.AlreadyClosedException;
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

	private boolean createBrokerConnection() {
		if(factory==null) {
		  factory = new ConnectionFactory();
		}
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
			return false;
		}
		return true;
	}
	
	private boolean reconnectBrokerConnection() {
		while(!instance.createBrokerConnection()){
			int queueSize = Agent.getInstance().getQueue().size();
			instance.log.info("Problemas com a conexao no envio. Nova tentativa em 5s.");
			instance.log.info("Tamanho da fila em memória: " + queueSize);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public static Send getInstance() {
		if (instance == null) {
			instance = new Send();
			while(!instance.reconnectBrokerConnection());
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
		} catch (AlreadyClosedException e) {			
			while(!instance.reconnectBrokerConnection());
			instance.log.info("Conexao reestabelecida");
			try {
				channel.basicPublish("", QUEUE_NAME, null,
						SerializationUtils.serialize(data));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		// System.out.println("Teste de envio");

	}
}
