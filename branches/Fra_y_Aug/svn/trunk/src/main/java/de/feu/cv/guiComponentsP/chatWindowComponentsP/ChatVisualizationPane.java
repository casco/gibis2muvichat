package de.feu.cv.guiComponentsP.chatWindowComponentsP;

import java.awt.BorderLayout;
import java.awt.Window;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.feu.cv.applicationLogicP.conversationP.Conversation;

/**
 * Abstract base class for the visualization panes.
 * 
 * @author Verena Kunz
 *
 */
public abstract class ChatVisualizationPane extends JPanel implements Observer  {

	/**
	 * The data model of the displayed conversation.
	 */
	protected Conversation conversation;

	/**
	 * Creates the visualization pane and adds it as an observer to the conversation.
	 * @param conversation
	 */
	public ChatVisualizationPane(Conversation conversation) {
		this.conversation = conversation;
		conversation.addObserver(this);
		this.setLayout(new BorderLayout());
	}
	
	/**
	 * Sets the focus to text field, if this is a visualization of a
	 * live chat.
	 */
	public void setFocusToTextInput(){
		Window window = SwingUtilities.getWindowAncestor(this);
		if (window instanceof ChatLiveWindow)
			((ChatLiveWindow)window).setFocusToTextInput();
	}
	
	/**
	 * Running on visualization changes.
	 */
	public void visualizationChanged() {
		
		
	}
	/**
	 * Resets the selection of items in the pane.
	 */
	public abstract void resetSelection();
	
	
	/**
	 * Actions when new message arrive (must be implemented in subclass).
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public abstract void update(Observable obs, Object arg);

	/**
	 * Returns a configuration panel to set the visual properties.
	 * of the visualization pane.
	 * @return the configuration panel
	 */
	public abstract JComponent getConfigurationPanel();

	/**
	 * Returns content for the right slot (control buttons etc.).
	 * @return the content for the right slot
	 */
	public abstract JComponent getContentForRightSlot(); 
	

	/**
	 * Returns the visual properties of the visualization pane.
	 * @return the visual properties
	 */
	public abstract VisualProperties getProperties();

}
