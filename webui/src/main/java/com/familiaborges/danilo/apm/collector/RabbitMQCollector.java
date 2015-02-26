package com.familiaborges.danilo.apm.collector;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.hibernate.Hibernate;

import asmapm.Agent;
import asmapm.config.APMConfig;
import asmapm.model.CallStackTrace;
import asmapm.model.CallStackTraceBuilder;

import com.familiaborges.danilo.apm.dao.ExecutionDAO;
import com.familiaborges.danilo.apm.dto.Execution;
import com.familiaborges.danilo.apm.util.WebUIConfig;
import com.google.gwt.user.rebind.rpc.SerializationUtils;
import com.mchange.v2.sql.filter.RecreatePackage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class RabbitMQCollector implements Runnable {

	private final static String QUEUE_NAME = "asmapmEvents";

	private Logger log = Logger.getLogger(getClass().getName());

	private static final AtomicLong LAST_TIME_MS = new AtomicLong();

	private ConnectionFactory factory;
	Connection connection;
	Channel channel = null;
	QueueingConsumer consumer = null;

	private boolean createBrokerConnection() {
		if (factory == null) {
			factory = new ConnectionFactory();
		}
		factory.setHost(WebUIConfig.getInstance().getValue("rabbitmq.host"));
		factory.setUsername(WebUIConfig.getInstance().getValue("rabbitmq.user"));
		factory.setPassword(WebUIConfig.getInstance().getValue(
				"rabbitmq.password"));
		factory.setVirtualHost(WebUIConfig.getInstance().getValue(
				"rabbitmq.vhost"));

		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);

			// String message = "Hello World!";
			consumer = new QueueingConsumer(channel);
			channel.basicConsume(QUEUE_NAME, true, consumer);
			System.out
			.println(" [*] Conexao efetuada.");
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	private boolean reconnectBrokerConnection() {
		while (!createBrokerConnection()) {
			log.info("Problemas com a conexao com o broker. Nova tentativa em 5s.");
			// log.info("Tamanho da fila em memória: " + queueSize);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public void run() {
		WebUIConfig.getInstance().getValue("rabbitmq.host");

		reconnectBrokerConnection();
		QueueingConsumer.Delivery delivery = null;

		while (true) {

			try {
				delivery = consumer.nextDelivery();
				saveExecution(delivery);
			} catch (Exception e) {
				if (e instanceof SocketException
						|| e instanceof ShutdownSignalException) {
					reconnectBrokerConnection();
				} else {
					e.printStackTrace();
				}
			}

		}

	}

	private void saveExecution(QueueingConsumer.Delivery delivery) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		CallStackTrace state = org.apache.commons.lang3.SerializationUtils
				.deserialize(delivery.getBody());
		System.out.println(dateFormat.format(new Date(state.getStopTimestamp())));
		System.out
				.println(dateFormat.format(new Date(state.getStartTimestamp())));
		//System.out.println(state.getStartTimestamp());
		try {
			System.out.println(sizeof(state) + " Bytes");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long duration = state.getStopTimestamp() - state.getStartTimestamp();
		System.out.println("URL: "
				+ state.getExtraData().get("serverUrl") + " - Time Spent: "+ duration);
		/*System.out.println("serverName: "
				+ state.getExtraData().get("serverName"));
		System.out.println("serverPort: "
				+ state.getExtraData().get("serverPort"));
		System.out.println("serverInfo: "
				+ state.getExtraData().get("serverInfo"));
		System.out.println("size: " + state.getExtraData().size());
		System.out.println("duration: " + (state.getStopTimestamp() - state.getStartTimestamp()));*/

		state.printOnTextFormat();
		ExecutionDAO dao = new ExecutionDAO();
		Execution dto = new Execution();
		dto.setIdExecution(uniqueCurrentTimeMS(state.getStartTimestamp()));
		dto.setStartTimeMillis(state.getStartTimestamp());
		dto.setDuration(state.getStopTimestamp() - state.getStartTimestamp());
		/*
		 * ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		 * ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
		 * objOut.writeObject(state); objOut.close(); byteOut.close();
		 * 
		 * byte[] bytes = byteOut.toByteArray();
		 */
		dto.setCallStackTrace(state);
		dao.save(dto);
	}

	public static long uniqueCurrentTimeMS(long timeMillis) {
		long now = timeMillis;
		while (true) {
			long lastTime = LAST_TIME_MS.get();
			if (lastTime >= now)
				now = lastTime + 1;
			if (LAST_TIME_MS.compareAndSet(lastTime, now))
				return now;
		}
	}

	public static int sizeof(Object obj) throws IOException {

		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteOutputStream);

		objectOutputStream.writeObject(obj);
		objectOutputStream.flush();
		objectOutputStream.close();

		return byteOutputStream.toByteArray().length;
	}

}
