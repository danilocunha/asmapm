package asmapm;

public class JavaLogEvent {

	/**
	 * Contents of the event message
	 */
	private StringBuffer eventMessage;

	/**
	 * Whether or not to put quotes around values
	 */
	private boolean quoteValues = true;

	/**
	 * Whether or not to add a date to the event string
	 */
	private boolean useInternalDate = true;

	/**
	 * default key value delimiter
	 */
	private static final String KVDELIM = "=";
	/**
	 * default pair delimiter
	 */
	private static final String PAIRDELIM = " ";
	/**
	 * default quote char
	 */
	private static final char QUOTE = '"';

	/**
	 * Event prefix fields
	 */
	private static final String PREFIX_NAME = "name";
	private static final String PREFIX_EVENT_ID = "event_id";

	public JavaLogEvent(String eventName, String eventID) {
		// TODO Auto-generated constructor stub
	}

	public void addPair(String key, long value) {
		addPair(key, String.valueOf(value));
	}
	
	public void addPair(String key, String value) {

		if (quoteValues)
			this.eventMessage.append(key).append(KVDELIM).append(QUOTE)
					.append(value).append(QUOTE).append(PAIRDELIM);
		else
			this.eventMessage.append(key).append(KVDELIM).append(value)
					.append(PAIRDELIM);

	}
	
	public String toString() {

		String event = "";

		if (useInternalDate) {

			StringBuffer clonedMessage = new StringBuffer();
			clonedMessage.append(System.currentTimeMillis()).append(PAIRDELIM)
					.append(this.eventMessage);
			event = clonedMessage.toString();
		} else
			event = eventMessage.toString();
		// trim off trailing pair delim char(s)
		return event.substring(0, event.length() - PAIRDELIM.length());
	}

}
