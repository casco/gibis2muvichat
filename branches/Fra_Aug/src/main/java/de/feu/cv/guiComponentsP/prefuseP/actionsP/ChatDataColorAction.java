package de.feu.cv.guiComponentsP.prefuseP.actionsP;

import java.util.HashMap;

import prefuse.action.assignment.DataColorAction;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;


/**
 * ColorAction for ChatMessages.
 * @author Verena Kunz
 *
 */
public class ChatDataColorAction extends DataColorAction {
	
	/**
	 * Dictionary holding nicks and colors
	 */
	private HashMap<String, Integer> colormap;
    /**
     * Color palette
     */
    private int[] holmerpalette = new int[] {
            ColorLib.rgb(124,232,137),
            ColorLib.rgb(129,196,255),
            ColorLib.rgb(255,238,149),
            ColorLib.rgb(232, 151, 124),
            ColorLib.rgb(217, 136, 255),
            ColorLib.rgb(203, 232, 139),
            ColorLib.rgb(146,255,235),
            ColorLib.rgb(255,219,165),
            ColorLib.rgb(232,139,171),
            ColorLib.rgb(155,152,255),
            ColorLib.rgb(232,220,153),
            ColorLib.rgb(161,255,173),
            ColorLib.rgb(255,199,181),
            ColorLib.rgb(207,153,232),
            ColorLib.rgb(168,222,255),
            ColorLib.rgb(232,202,163),
            ColorLib.rgb(237,255,173),
            ColorLib.rgb(255,192,233),
            ColorLib.rgb(163,167,232),
        };

	/**
	 * @param group the data group to process
	 * @param dataField the data field to base size assignments on
	 * @param dataType the data type to use for the data field
	 * @param colorField the color field to assign
	 */
	public ChatDataColorAction(String group, String dataField, 
            int dataType, String colorField) {
		super(group	, dataField, dataType, colorField);
		this.colormap = new HashMap<String, Integer>();

	}
    /**
     * Returns a color for the item.
     * Extends the palette if necessary.
     * @see prefuse.action.assignment.DataColorAction#getColor(prefuse.visual.VisualItem)
     */
    public int getColor(VisualItem item) {
    	int colorvalue;
    	String datafieldvalue = item.getString(getDataField());
    	if (colormap.containsKey(datafieldvalue))
    		colorvalue = (int) colormap.get(datafieldvalue);
    	
    	else {	
    		//colorvalue = getNextColorValue(colormap.size()+1); 
    		colorvalue = getNextColorValueHolmer(colormap.size()+1);
    		colormap.put(datafieldvalue, colorvalue);
    	}
    	return colorvalue;	   
    }
    
    /**
     * Returns the next color value. 
     * (HSB with full saturation and brightness)
     * @param n the number of colors needed 
     * @return the new color
     */

    public int getNextColorValue(int n){
    	
    	float x=0;   	
    	
    	if (n<=1)
    		x = 0;
    	if (n == 2)
    		x = 0.5f;
    	
    	if (n > 2){
    		int counter = 2;
        	float u = 4;
            
        	while (counter < n){
        		int i=1;
        		while ((i < u) && (counter<n)){
        			x = i/u;
        			i = i+2;
        			counter++;
        		}
        	    u = u*2;
        	}
        }
    	int newcolor = ColorLib.hsb(x, 1, 1);
    	return newcolor;
    }
    
    /**
     * Returns a new color value from the palette.
     * @param i the map size
     * @return the color value
     */
    public int getNextColorValueHolmer(int i) {
    	int palettelength = holmerpalette.length;
    	int position = (i-1)%palettelength;
    	int newcolor = holmerpalette[position];
    	
    	
    	return newcolor;
    	
    	
    }

}
