package de.feu.cv.applicationLogicP.chatFileP;

import java.io.File;

import prefuse.data.Tree;
import prefuse.data.io.DataIOException;
import prefuse.data.io.TreeMLReader;
import de.feu.cv.applicationLogicP.ChatAdministration;
import de.feu.cv.applicationLogicP.conversationP.Conversation;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatReplayWindow;

/**
 * Represents a file with a saved conversation.
 * 
 * @author Verena Kunz
 *
 */
public class ChatFile {
	/**
	 * The file object.
	 */
	private File file;
	/**
	 * The window where the file is displayed.
	 */
	private ChatReplayWindow savedchatwindow;
	/**
	 * Data model of saved conversation.
	 */
	private Conversation conversation;


	
	/**
	 * Creates a new ChatFile. Throws a DataIOException, if the file contains no
	 * data in TreeML-Format.
	 * @param file a file object
	 * @throws DataIOException 
	 */
	public ChatFile(File file) throws DataIOException {
		
		this.file = file;
				
		TreeMLReader tmlr = new TreeMLReader();
		Tree savedTree = (Tree) tmlr.readGraph(file);
		this.conversation = new Conversation(savedTree);
		savedchatwindow = new ChatReplayWindow(this);
		savedchatwindow.setVisible(true);


	}
	/**
	 * Returns the file of the ChatFile.
	 * @return the file
	 */
	public File getFile() {
		return file;
	}
	/**
	 * Brings the corresponding savedchatwindow to the front.
	 */
	public void setActiveChat() {
		savedchatwindow.bringToFront();
		
	}
	/**
	 * Returns the saved conversation.
	 * @return the conversation
	 */
	public Conversation getConversation() {
		return conversation;
	}
	
	
	
	/**
	 * Removes the file from the filelist.
	 */
	public void leave() {
		ChatAdministration.getInstance().removeFileFromList(this);
		
	} 

}
