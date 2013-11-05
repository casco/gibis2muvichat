package de.feu.cv.guiComponentsP.pluginsP.sequenceByLayoutTree;
import java.awt.geom.Point2D;

import javax.swing.JComponent;
import javax.swing.JPanel;

import prefuse.Constants;
import prefuse.action.layout.Layout;
import de.feu.cv.applicationLogicP.conversationP.Conversation;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties;
import de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse;


/**
 * Visualization implementation SequenceByLayout.
 * @author Verena Kunz
 *
 */
public class VisualizationPane extends ChatVisualizationPanePrefuse  {

	private static final long serialVersionUID = 1131564292724646239L;
	/**
	 * The sequnzial tree layout action.
	 */
	private SequenzialTreeLayout layout;  //  @jve:decl-index=0:



	/**
	 * Creates a new visualization pane. 
	 * @param conversation the displayed conversation
	 */
	public VisualizationPane(Conversation conversation) {
		super(conversation);
		doDuringUpdate();
	}



	/**
	 * Creates a special tree layout to see the time differences
	 * @see de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse#getTreeLayout()
	 */
	public Layout getTreeLayout() {
		if (layout == null){
	        // tree layout top to bottom
	        layout = new SequenzialTreeLayout("tree");
	        layout.setOrientation(Constants.ORIENT_TOP_BOTTOM);
	        layout.setLayoutAnchor(new Point2D.Double(0,0));
	        changeProperty("orientation");
	        changeProperty("nodespacing");
	        changeProperty("sequenzialspacing");
		}
        return layout;
	}

	/**
	 * @see de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse#doDuringUpdate()
	 */
	public void doDuringUpdate(){
		// no special update actions
	}
	
	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#getConfigurationPanel()
	 */
	public JComponent getConfigurationPanel() {
		JPanel cp = new PropertyContentPane((VisualizationProperties) visualProperties);
		return cp;
	}
	
	/**
	 * Changes the property key in this visualization.
	 * @param key	the property
	 */
	protected void changeProperty(String key) {
		String value = visualProperties.getProperty(key);
		super.changeProperty(key,value);
		if (key.equals("sequenzialspacing")){
			int depthspacing = Integer.parseInt(value);
			changeSequenzialSpacing(depthspacing);
		}
		if (key.equals("nodespacing")){
			int nodespacing = Integer.parseInt(value);
			changeNodeSpacing(nodespacing);
		}
	}	
	
	/**
	 * Changes the breadth space between nodes.
	 * @param nodespacing the breadth space between nodes
	 */
	private void changeNodeSpacing(int nodespacing) {
		layout.setDistance(nodespacing);
		
	}

	/**
	 * Changes the depth space between nodes.
	 * @param depthspacing the depth space between nodes.
	 */
	private void changeSequenzialSpacing(int depthspacing) {
		layout.setDepthSpacing(depthspacing);
		
	}	

	
	/**
	 * @see de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse#changeOrientation(int)
	 */
	protected void changeOrientation(int orientation) {
		layout.setOrientation(orientation);
		
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
