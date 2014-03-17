package local.teste.monitor;

import java.util.HashMap;

public class TaskMonitor {

	private static TaskMonitor taskMonitor; 
	private HashMap map;
	
	public synchronized static TaskMonitor getInstance() {  
        if (taskMonitor == null) {  
        	taskMonitor = new TaskMonitor();  
        }  
        return taskMonitor;  
    }  
	
	public HashMap getHashMap() {
		if(map==null) {
			map = new HashMap<Integer, String>();
		}
		return map;
	}
	
	public void addCall(Long i,String s) {
		getHashMap().put(i, s);
	}
	
	public void removeCall(Long i) {
		getHashMap().remove(i);
	}
}
