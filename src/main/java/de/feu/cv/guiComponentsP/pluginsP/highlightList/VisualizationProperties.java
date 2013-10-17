package de.feu.cv.guiComponentsP.pluginsP.highlightList;

import java.awt.Color;

import de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties;

/**
 * The visual properties of viualization hightlightList.
 * @author Verena Kunz
 *
 */
public class VisualizationProperties extends VisualProperties{
	
	
	private static final  String name = "visualization_hightlightList.prop";



	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties#setDefaultValues()
	 */
	public void setDefaultValues(){
		setProperty("bgcolor", Integer.toString(Color.white.getRGB()));
		setProperty("fgcolor", Integer.toString(Color.black.getRGB()));
		setProperty("size", "12");
		setProperty("bgcolor_selection",Integer.toString(Color.red.getRGB()));
		setProperty("fgcolor_selection",Integer.toString(Color.black.getRGB()));
		setProperty("format_ancestors","1");
	}

	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties#setFile()
	 */
	@Override
	public void setFile() {
		filename = name;
	}
}