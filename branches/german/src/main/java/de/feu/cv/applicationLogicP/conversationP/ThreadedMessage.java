package de.feu.cv.applicationLogicP.conversationP;

import org.jivesoftware.smack.packet.Message;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;


/**
 * The class for threaded messages
 * @author Verena Kunz
 *
 */
public class ThreadedMessage implements Serializable, Comparable {

    private Message originalJabberMessaje;

	private static final long serialVersionUID = -364952901157857808L;
	/**
	 * The arrival date and time of the message.
	 */
	private Date date;
	/**
	 * The text of the message.
	 */
	private String text;
	/**
	 * The nickname of the author of the message.
	 */
	private String nick;
	/**
	 * The id of the message.
	 */
	private String id;
	/**
	 * The nickname of the author of the parent message.
	 */
	private String parent_nick;
	/**
	 * The id of the parent message.
	 */
	private String parent_id;

	//{-*-}
	private String messageType;
	private String relationType;	
	
	private String configurationMessage; // indica si es un mensaje de configuración
	private String config_file;
	
	public String getConfig_file() {
		return config_file;
	}
	public void setConfig_file(String config_file) {
		this.config_file = config_file;
	}
	public String getConfigurationMessage() {
		return configurationMessage;
	}
	public void setConfigurationMessage(String conversationMessage) {
		this.configurationMessage = conversationMessage;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String mType) {
		this.messageType = mType;
	}
	
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String rType) {
		this.relationType = rType;    
	}
	
	//{-*-}

	
	
	
	/**
	 * Create a new message with the given paramenters.
	 * @param date the arrival date
	 * @param text the message text
	 * @param nick the authors nickname
	 * @param id the id
	 * @param parent_nick the nickname of the author of the parent message
	 * @param parent_id the id of the parent message
	 */
	public ThreadedMessage(Date date, String text, String nick,  String id, String parent_nick, String parent_id, Message originalJabberMessaje) {
		super();
		this.date = date;
		this.text = text;
		this.nick = nick;
		this.id = id;
		this.parent_nick = parent_nick;
		this.parent_id = parent_id;
        this.originalJabberMessaje = originalJabberMessaje;
	}
	/**
	 * Gives the date/time of message.
	 * @return the date/time of message
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Gives the the authors nickname.
	 * @return the authors nickname
	 */
	public String getNick() {
		return nick;
	}
	/**
	 * Gives the message text.
	 * @return  the message text
	 */
	//{-*-}	
	//evil hack for printing the IbisType and IbisRelation	 
	//{-*-}
	public String getText() {
		//really evil hack for printing the text of a configuration message
		if (this.configurationMessage.equals("false")){
			String type = this.getMessageType();
			String relation = this.getRelationType();
			if (relation != null){
				type += " (" + relation + ")";
			}
			type += "\r\n"; 
			return type + text;	
		}
		else{
			return text;
		}
		
	}

	/**
	 * Gives the id of the message.
	 * @return the id of the message
	 */
	public String getID() {
		return id;
	}
	/**
	 * Gives the id of the parent message.
	 * @return the id of the parent message
	 */
	public String getParent_id() {
		return parent_id;
	}
	/**
	 * Gives the nickname of the author of the parent message.
	 * @return the nickname of the author of the parent message
	 */
	public String getParent_nick() {
		return parent_nick;
	}
	
	/** 
	 * The method toString ist overwritten to give a 
	 * String representation of a threaded message.
	 * @return the formatted ThreadedMessage to display in list
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM);
		return "[" + df.format(date) + "] " + nick + ": " + text;
	}

	/**
	 * Compare ThreadedMessages chronological.
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {	
		return this.getDate().compareTo(((ThreadedMessage) o).getDate());
	}

    public Message getOriginalJabberMessaje() {
        return  originalJabberMessaje;
    }
	
}
