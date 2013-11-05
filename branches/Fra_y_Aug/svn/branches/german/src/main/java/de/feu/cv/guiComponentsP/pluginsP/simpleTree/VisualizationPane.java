package de.feu.cv.guiComponentsP.pluginsP.simpleTree;
import javax.swing.JComponent;

import de.feu.cv.applicationLogicP.conversationP.Conversation;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties;
import de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse;

/**
 *  Visualization implementation SimpleTree
 * @author Verena Kunz
 *
 */
public class VisualizationPane extends ChatVisualizationPanePrefuse  {

	private static final long serialVersionUID = -5180648595070676873L;
	
	/**
	 * Creates a new visualization pane 
	 * @param conversation the displayed conversation
	 */
	public VisualizationPane(Conversation conversation) {
		super(conversation);
	}


	
	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#getProperties()
	 */
	public VisualProperties getProperties() {
		if (visualProperties == null){
			visualProperties = new VisualizationProperties();	
			visualProperties.addObserver(this);
		}
			
		return visualProperties;
	}
	
	/**
	 * @see de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse#getConfigurationPanel()
	 */
	public JComponent getConfigurationPanel() {
		PropertyContentPane cp = new PropertyContentPane((VisualizationProperties) visualProperties);
		return cp;
	}
	

}
