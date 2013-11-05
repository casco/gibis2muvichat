package de.feu.cv.guiComponentsP.pluginsP.timeByColorTree;

import java.awt.Color;

import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;
import de.feu.cv.guiComponentsP.prefuseP.actionsP.ChatDataColorAction;

/**
 * ColorAction to fade out old items.
 * @author Verena Kunz
 *
 */
public class TimeChatDataColorAction extends ChatDataColorAction {
	

	private int seconds2white = 100;
	private VisualizationPane cvp;

	/**
	 * Creates new color action.
	 * @param group the datagroup to process
	 * @param dataField the datafield for same colors
	 * @param dataType the data type to use for the data field
	 * @param colorField the color field to assign
	 * @param cvp the visualiyation pane
	 */
	public TimeChatDataColorAction(String group, String dataField, 
            int dataType, String colorField, VisualizationPane cvp) {
		super(group	, dataField, dataType, colorField);
		this.cvp = cvp;
	
	}
    /**
     * @see de.feu.cv.guiComponentsP.prefuseP.actionsP.ChatDataColorAction#getColor(prefuse.visual.VisualItem)
     */
    public int getColor(VisualItem item) {

    	int colorvalue = super.getColor(item);
    	
    	// calculate time offset and transparency
    	long now = cvp.getTimeOfRecentItem();
    	long itemtime = item.getLong("date");
    	// age of item in seconds
    	long timeoffset = (now - itemtime)/1000;  	
    	

    	// make colorvalue brighter
    	if (timeoffset < seconds2white)
    		colorvalue = getBrighterColor(colorvalue,(float)timeoffset/seconds2white);
    	else
    		colorvalue = Color.white.getRGB();
    	
    	return colorvalue;	
        
    }

    
    /**
     * Returns a brighter color for a color with full
     * saturation and brightness
     * @param c the start color
     * @param scale a value between 0.0f (same color) and 1.0f (white)
     * @return the rgb color value
     */
    public int getBrighterColor(int c, float scale){
    	int r = ColorLib.red(c);
    	int g = ColorLib.green(c);
    	int b = ColorLib.blue(c);
    	int newr;
    	int newg;
    	int newb;
    	
//    	System.out.println("R:"+ r + " G:"+ g+" B:"+b);
//    	System.out.println("Scale:"+scale);
    	
    	// set new red
        if (r == 0){
        	newr = (int) (255*scale);     	
        }
        else {
        	if (r == 255){
        		newr = 255;
        	}
        	else
        		newr = (int) (r+(255-r)*scale);
        }
        // set new green	
        if (g == 0){
        	newg = (int) (255*scale);     	
        }
        else {
        	if (g == 255){
        		newg = 255;
        	}
        	else
        		newg = (int) (g+(255-g)*scale);
        }        	

        // set new blue
        if (b == 0){
        	newb = (int) (255*scale);     	
        }
        else {
        	if (b == 255){
        		newb = 255;
        	}
        	else
        		newb = (int) (b+(255-b)*scale);
        }          
        //System.out.println("NewR:"+ newr + " NewG:"+ newg+" NewB:"+newb);
        int returnvalue = ColorLib.rgba(newr,newg,newb,ColorLib.alpha(c));
        return returnvalue;
    }
    
	/**
	 * Set the timediffrence between color and white.
	 * @param seconds2white the time to fade out in seconds
	 */
	public void setSeconds2white(int seconds2white) {
		this.seconds2white=seconds2white;
		
	}
    
    
}
