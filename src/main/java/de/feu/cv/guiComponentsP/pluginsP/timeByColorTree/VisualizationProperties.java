package de.feu.cv.guiComponentsP.pluginsP.timeByColorTree;

import de.feu.cv.guiComponentsP.prefuseP.VisualPropertiesPrefuse;

/**
 * The visual properties of viualization TimeByColorTree.
 * @author Verena Kunz
 *
 */
public class VisualizationProperties extends VisualPropertiesPrefuse{
	
	
	private static final String name = "visualization_timeByColorTree.prop";


	/**
	 * Sets default properties.
	 * @see de.feu.cv.guiComponentsP.prefuseP.VisualPropertiesPrefuse#setDefaultValues()
	 */
	public void setDefaultValues(){
		super.setDefaultValues();
		setProperty("breadthspacing",Integer.toString(5));
		setProperty("depthspacing",Integer.toString(50));
		setProperty("subtreespacing",Integer.toString(25));
		setProperty("seconds2white","100");

	}
	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties#setFile()
	 */
	@Override
	public void setFile() {
		filename = name;
	}


}