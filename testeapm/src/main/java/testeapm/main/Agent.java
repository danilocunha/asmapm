package testeapm.main;

import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;


public class Agent {

	

		private  Logger log = Logger.getLogger(this.getClass().getName());

		private static long lowThreshold = 200;

		public static void premain(String agentArgs, Instrumentation inst) {
			//System.out.println("===== TESTEEEEEEE =====");
			//dumpVars(System.getenv());		
					
			
			inst.addTransformer(new MyTestTransformer());

		}
}
