package de.feu.cv.applicationLogicP.conversationP;

import javax.swing.DefaultListModel;




/**
 * Model for list based visualizations.
 * @author Verena Kunz
 *
 */
public class ChatListModel extends DefaultListModel implements ChatDataModel{


	private static final long serialVersionUID = 8455687937046547877L;

	/**
	 * Adds a message to the model.
	 * @param message
	 */
	public void addMessage(ThreadedMessage message) {
		addElement(message);
		
	}

	/**
	 * Removes all list content.
	 */
	public void reset() {
		clear();
		
	}

}
