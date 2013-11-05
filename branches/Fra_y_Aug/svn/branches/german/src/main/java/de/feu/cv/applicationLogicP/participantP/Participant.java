package de.feu.cv.applicationLogicP.participantP;

import de.feu.cv.applicationLogicP.MuViChatConstants;
import de.feu.cv.applicationLogicP.Resources;

/**
 * Represents a participant of a chat room.
 * @author Verena Kunz
 *
 */
public class Participant {
	/**
	 * The nickname.
	 */
	private String nick;
	/**
	 * The resource string.
	 */
	private String resource;

	/**
	 * Creates a new participant with nickname and resource string.
	 * @param nick the nickname of the participant
	 * @param resource the resource string of the participant, "muvi" if the participant users the MuVi-Client 
	 *  
	 */
	public Participant(String nick, String resource) {
		super();
		this.nick = nick;
		this.resource = resource;
	}
	/**
	 * Returns the resource.
	 * @return the resource string
	 */
	public String getResource() {
		return resource;
	}
	/**
	 * Sets the resource string.
	 * @param client
	 */
	public void setResource(String client) {
		this.resource = client;
	}
	/**
	 * Returns the nickname .
	 * @return the nickname
	 */
	public String getNick() {
		return nick;
	}
	/**
	 * Sets the nickname.
	 * @param nick the nickname
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	/**
	 * Checks if the nickname are equal.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Participant p = (Participant) obj;
		boolean equal = this.nick.equals(p.nick);
		return equal;
	}

	/**
	 * Returns a string representing a participant.
	 * Shows a message, if a participant uses the wrong client.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String client_noMuvi = Resources.getString("text_no_muvi");
		String client_unknown = Resources.getString("text_client_unknown");
		String suffix = "";

		if (resource == null)
			suffix = " (" + client_unknown +  ")";
		
		else {
		
			if (!resource.equals(MuViChatConstants.resource))
				suffix =  " ("+ client_noMuvi + ")";
		}
		
		return nick + suffix;
	}
	
}
