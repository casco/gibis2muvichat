package de.feu.cv.guiComponentsP.pluginsP.sequenceByLayoutTree;

import de.feu.cv.guiComponentsP.prefuseP.VisualPropertiesPrefuse;

/**
  * The visual properties of viualization sequenceByLayoutTree
 * @author Verena Kunz
 *
 */
public class VisualizationProperties extends VisualPropertiesPrefuse{
	
	
	private static final String name = "visualization_sequenceByLayoutTree.prop";


	/**
	 * Sets default properties.
	 * @see de.feu.cv.guiComponentsP.prefuseP.VisualPropertiesPrefuse#setDefaultValues()
	 */
	public void setDefaultValues(){
		super.setDefaultValues();
		setProperty("sequenzialspacing","5");
		setProperty("nodespacing","70");

	}
	
	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties#setFile()
	 */
	@Override
	public void setFile() {
		filename = name;
	}


}