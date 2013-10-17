package de.feu.cv.guiComponentsP.prefuseP;

import prefuse.Constants;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties;

/**
  * The visual properties of viualization prefuse.
 * @author Verena Kunz
 *
 */
public abstract class VisualPropertiesPrefuse extends VisualProperties{
	
	
	/**
	 * Sets default properties.
	 */
	public void setDefaultValues(){
		setProperty("orientation", Integer.toString(Constants.ORIENT_TOP_BOTTOM));
		setProperty("linebreakwidth", Integer.toString(90));
	}


}