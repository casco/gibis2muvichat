package de.feu.cv.guiComponentsP.pluginsP.timeByLayoutTree;
import java.awt.geom.Point2D;

import javax.swing.JComponent;
import javax.swing.JPanel;

import prefuse.action.layout.Layout;
import de.feu.cv.applicationLogicP.conversationP.Conversation;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties;
import de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse;


/**
 * Visualization implementation timeByLayoutTree.
 * @author Verena Kunz
 *
 */
public class VisualizationPane extends ChatVisualizationPanePrefuse  {

	/**
	 * The layout action.
	 */
	private TimeTreeLayout layout;  //  @jve:decl-index=0:
	/**
	 * The starttime of the conversation.
	 */
	private long starttime;


	/**
	 * Creates a new visualization pane. 
	 * @param conversation the displayed conversation
	 */
	public VisualizationPane(Conversation conversation) {
		super(conversation);
		doDuringUpdate();
	}

	private static final long serialVersionUID = 1131564292724646239L;

	/**
	 * Creates a special tree layout to see the time differences.
	 * @see de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse#getTreeLayout()
	 */
	public Layout getTreeLayout() {
		if (layout == null){
	        // tree layout top to bottom
	        layout = new TimeTreeLayout("tree");
	        //layout.setOrientation(Constants.ORIENT_TOP_BOTTOM);
	        //nltl.setOrientation(Constants.ORIENT_LEFT_RIGHT);
	        //move up to use space of unvisible root node
	        //nltl.setRootNodeOffset(-50);
	        layout.setLayoutAnchor(new Point2D.Double(0,0));
	        //layout.setFactor(5);
	        changeProperty("orientation");
	        changeProperty("nodespacing");
	        changeProperty("pixelpersecond");
		}
        return layout;
	}
	/**
	 * @see de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse#doDuringUpdate()
	 */
	public void doDuringUpdate(){
		
		// use the timestamp of first message as starttime of the conversation
		if (starttime == 0){
			starttime = conversation.getStarttime();
			layout.setStarttime(starttime);
		}
	}
	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#getConfigurationPanel()
	 */
	public JComponent getConfigurationPanel() {
		JPanel cp = new PropertyContentPane((VisualizationProperties)visualProperties);
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
	
	/**
	 * Changes the property key in this visualization.
	 * @param key	the property
	 */
	protected void changeProperty(String key) {
		String value = visualProperties.getProperty(key);
		super.changeProperty(key,value);
		if (key.equals("pixelpersecond")){
			int pixelPerSecond = Integer.parseInt(value);
			changePixelPerSecond(pixelPerSecond);
		}
		if (key.equals("nodespacing")){
			int nodespacing = Integer.parseInt(value);
			changeNodeSpacing(nodespacing);
		}
	}	

	
	/**
	 * Changes the space between nodes.
	 * @param nodespacing the space between nodes
	 */
	private void changeNodeSpacing(int nodespacing) {
		layout.setDistance(nodespacing);
		
	}
	/**
	 * Changes the ratio pixel per second.
	 * @param pixelPerSecond the pixels per second
	 */
	private void changePixelPerSecond(int pixelPerSecond) {
		layout.setFactor(pixelPerSecond);
		
	}
	
	/**
	 * Changes the orientation of the tree.
	 * @see de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse#changeOrientation(int)
	 */
	protected void changeOrientation(int orientation) {
		layout.setOrientation(orientation);
		
	}
}
