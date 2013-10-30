package de.feu.cv.guiComponentsP.pluginsP.classicChat;

import java.awt.Color;

import de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties;

/**
  * The visual properties of viualization classicChat.
 * @author Verena Kunz
 *
 */
public class VisualizationProperties extends VisualProperties{
	
	
	private static final String name = "visualization_classicChat.prop";



	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties#setDefaultValues()
	 */
	public void setDefaultValues(){
		setProperty("bgcolor", Integer.toString(Color.white.getRGB()));
		setProperty("fgcolor", Integer.toString(Color.black.getRGB()));
		setProperty("size", "12");
	}
	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties#setFile()
	 */
	@Override
	public void setFile() {
		filename = name;
	}


}