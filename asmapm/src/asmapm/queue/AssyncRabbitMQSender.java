package asmapm.queue;

import java.util.logging.Logger;

import asmapm.Agent;
import asmapm.model.CallStackTrace;

public class AssyncRabbitMQSender implements Runnable {

	private Logger log = Logger.getLogger(this.getClass().getName());
	
	private volatile boolean terminate = false;

	@Override
	public void run() {
		log.info("Iniciou o while de transporte");
		while (!terminate) {
			try {
				CallStackTrace data = Agent.getInstance().getQueue().take();
				Send.getInstance().testSend(data);
				log.info("Enviou evento");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}		

}
