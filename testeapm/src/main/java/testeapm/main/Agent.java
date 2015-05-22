package testeapm.main;

import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;


public class Agent {

	

		private static Logger log = Logger.getLogger("Agent");

		private static long lowThreshold = 200;
		
		public static boolean startprofile(String cName, String mName) {
			
			log.info("START MONITORING: " + cName + "::" + mName);
				//log.log(Level.INFO, "PROFILE JA INICIOU EM: " + state.getClassName() + "::" + state.getMethodName());
			
			return true;		

		}
		
		public static void endprofile(String cName, String mName,
				long executionTime) {
			
			log.info("END MONITORING: " + cName + "::" + mName);

		}
		
		public static void premain(String agentArgs, Instrumentation inst) {
			//System.out.println("===== TESTEEEEEEE =====");
			//dumpVars(System.getenv());		
					
			
			inst.addTransformer(new MyTestTransformer());

		}
}
