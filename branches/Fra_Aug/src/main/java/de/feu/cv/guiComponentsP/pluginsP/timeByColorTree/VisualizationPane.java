package de.feu.cv.guiComponentsP.pluginsP.timeByColorTree;
import javax.swing.JComponent;

import prefuse.Constants;
import prefuse.action.Action;
import prefuse.action.ActionList;
import prefuse.action.assignment.ColorAction;
import prefuse.visual.VisualItem;
import de.feu.cv.applicationLogicP.conversationP.Conversation;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties;
import de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse;

/**
 *  Visualization implementation TimeByColorTree.
 * @author Verena Kunz
 *
 */
public class VisualizationPane extends ChatVisualizationPanePrefuse  {

	private static final long serialVersionUID = -5180648595070676873L;
	/**
	 * The time of the recent item.
	 */
	private long timeOfRecentItem;

	/**
	 * Creates a new visualization pane. 
	 * @param conversation the displayed conversation
	 */
	public VisualizationPane(Conversation conversation) {
		super(conversation);
		changeProperty("seconds2white");
		VisualItem newvi = getNewVi();
		if (newvi != null)
			timeOfRecentItem = newvi.getLong("date");
	}



	/**
	 * @see de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse#createfillColorAction()
	 */
	protected ColorAction createfillColorAction() {
		return new TimeChatDataColorAction("tree.nodes", "nick",
                Constants.NOMINAL, VisualItem.FILLCOLOR, this);
	}
	/**
	 * @see de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse#doDuringUpdate()
	 */
	protected void doDuringUpdate(){
		if (getNewVi()!=null)
			timeOfRecentItem = getNewVi().getLong("date");

		}
	
	/**
	 * Returns the time of the recent item.
	 * @return the time of the recent item
	 */
	public long getTimeOfRecentItem(){
		return timeOfRecentItem;
	}
	
	/**
	 * Returns the fill color action.
	 * @return the fill color action object
	 */
	private Action getFillColorAction(){
		ActionList actionlist = getColorAction();
		//System.out.println(actionlist.get(0).getClass());
		return actionlist.get(0);
	}
	
	
	/**
	 * Changes the property key in this visualization.
	 * @param key	the property
	 */
	protected void changeProperty(String key) {
		String value = visualProperties.getProperty(key);
		super.changeProperty(key,value);
		if (key.equals("seconds2white")){
			int seconds2white = Integer.parseInt(value);
			changeSeconds2white(seconds2white);
		}
	}
	/**
	 * Sets the parameter seconds2white.
	 * @param seconds2white
	 */
	private void changeSeconds2white(int seconds2white) {
		TimeChatDataColorAction coloraction = (TimeChatDataColorAction) getFillColorAction();
		coloraction.setSeconds2white(seconds2white);
		
	}
	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#getConfigurationPanel()
	 */
	public JComponent getConfigurationPanel() {
		PropertyContentPane cp = new PropertyContentPane((VisualizationProperties) visualProperties);
		return cp;
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
	
}
