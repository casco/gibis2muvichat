package de.feu.cv.applicationLogicP.participantP;

/**
 * Class for notifications about paricipants availability.
 * @author Verena Kunz
 *
 */
public class ParticipantsChange {
	/**
	 * The nickname of the participant.
	 */
	private String nick;
	/**
	 * The availability of the participant.
	 */
	private boolean available;
	/**
	 * The resource (name of client application) of the participant.
	 */
	private String resource;
			
	
	/**
	 * Creates a new availability notification. 
	 * @param nick the nickname of the participant
	 * @param available <code>true</code> if available; <code>false</code> if unavailable
	 */
	public ParticipantsChange(String nick, boolean available, String resource) {
		super();
		this.nick = nick;
		this.available = available;
		this.resource = resource;
	}
	/**
	 * Gives type of notification (available or not).
	 * @return <code>true</code> if available; <code>false</code> if unavailable
	 */
	public boolean isAvailable() {
		return available;
	}
	/**
	 * Gives the nickname of the participants which availability changes.
	 * @return the nickname
	 */
	public String getNick() {
		return nick;
	}
	/**
	 * Returns the resource component of the participant change.
	 * @return the resource string
	 */
	public String getResource() {
		return resource;
	}

}
