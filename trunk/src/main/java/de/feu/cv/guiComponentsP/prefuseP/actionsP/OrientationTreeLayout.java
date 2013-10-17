package de.feu.cv.guiComponentsP.prefuseP.actionsP;

/**
 * Interface for tree layouts with orientation 
 * @author Verena Kunz
 *
 */
public interface OrientationTreeLayout {
		
    /**
     * Get the orientation of the tree layout.
     * @return the orientation value. One of
     * {@link prefuse.Constants#ORIENT_LEFT_RIGHT},
     * {@link prefuse.Constants#ORIENT_RIGHT_LEFT},
     * {@link prefuse.Constants#ORIENT_TOP_BOTTOM}, or
     * {@link prefuse.Constants#ORIENT_BOTTOM_TOP}.
     */
	  public int getOrientation();  
	  
	  
    /**
     * Set the orientation of the tree layout.
     * @param orientation the orientation value. One of
     * {@link prefuse.Constants#ORIENT_LEFT_RIGHT},
     * {@link prefuse.Constants#ORIENT_RIGHT_LEFT},
     * {@link prefuse.Constants#ORIENT_TOP_BOTTOM}, or
     * {@link prefuse.Constants#ORIENT_BOTTOM_TOP}.
     */
	  public void setOrientation(int orientation);
}
