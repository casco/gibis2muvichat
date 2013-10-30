package de.feu.cv.guiComponentsP.pluginsP.minimal;

import java.util.Observable;

import javax.swing.JComponent;

import de.feu.cv.applicationLogicP.conversationP.Conversation;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties;

/**
 * Simple example for visualization. 
 * @author Verena Kunz
 *
 */
public class VisualizationPane extends ChatVisualizationPane {

	

	private static final long serialVersionUID = -9222047995454656623L;
	
	/**
	 * Creates a new visualization pane. 
	 * @param conversation the displayed conversation
	 */
	public VisualizationPane(Conversation conversation) {
		super(conversation);

	}


	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#getConfigurationPanel()
	 */
	@Override
	public JComponent getConfigurationPanel() {

		return null;
	}

	@Override
	public JComponent getContentForRightSlot() {

		return null;
	}


	@Override
	public VisualProperties getProperties() {

		return null;
	}

	@Override
	public void resetSelection() {

		
	}

	@Override
	public void update(Observable obs, Object arg) {
		System.out.println("Nachricht eingetroffen !");
		
	}

}
