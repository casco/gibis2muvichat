package de.feu.cv.applicationLogicP.conversationP;

/**
 * Interface for chat data models.
 * @author Verena Kunz
 *
 */
public interface ChatDataModel {
	
	/**
	 * Adds a new message to the data model.
	 * @param message
	 */
	public void addMessage(ThreadedMessage message);
	
	/**
	 * Clear all data from the data model.
	 */
	public void reset();

}
