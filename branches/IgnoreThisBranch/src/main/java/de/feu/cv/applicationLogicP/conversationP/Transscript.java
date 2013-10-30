package de.feu.cv.applicationLogicP.conversationP;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


/**
 * Model for classic chat visualization.
 * @author Verena Kunz
 *
 */
public class Transscript extends PlainDocument implements ChatDataModel{


	private static final long serialVersionUID = -2665238239459573611L;

	/**
	 * Adds am message to the model.
	 * @param message the ThreadedMessage
	 */
	public void addMessage(ThreadedMessage message) {
		try {
			insertString(getLength(), message.toString()+"\n", null);
		} catch (BadLocationException e) {

			e.printStackTrace();
		}
		
	}

	/**
	 * Removes all content in the model.
	 */
	public void reset(){
		try {
			remove(0,getLength());
		} catch (BadLocationException e) {

			e.printStackTrace();
		}
		
	}

}
