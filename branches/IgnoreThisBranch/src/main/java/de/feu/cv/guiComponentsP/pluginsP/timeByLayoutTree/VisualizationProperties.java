package de.feu.cv.guiComponentsP.pluginsP.timeByLayoutTree;

import de.feu.cv.guiComponentsP.prefuseP.VisualPropertiesPrefuse;

/**
  * The visual properties of viualization timeByLayoutTree.
 * @author Verena Kunz
 *
 */
public class VisualizationProperties extends VisualPropertiesPrefuse{
	
	
	private static final String name = "visualization_timeByLayoutTree.prop";


	/**
	 * Sets default properties.
	 * @see de.feu.cv.guiComponentsP.prefuseP.VisualPropertiesPrefuse#setDefaultValues()
	 */
	public void setDefaultValues(){
		super.setDefaultValues();
		setProperty("pixelpersecond","5");
		setProperty("nodespacing","70");

	}
	
	@Override
	public void setFile() {
		filename = name;
	}


}