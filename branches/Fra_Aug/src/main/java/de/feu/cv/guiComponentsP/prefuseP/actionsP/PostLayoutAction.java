package de.feu.cv.guiComponentsP.prefuseP.actionsP;

import java.awt.geom.Point2D;

import de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse;

import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.Action;

/**
 * Action to run before layout action to hold the middle item.
 * @author Verena Kunz
 *
 */
public class PostLayoutAction extends Action{

	/**
	 * Pans that the middle item remains in center.
	 * @see prefuse.action.Action#run(double)
	 */
	@Override
	public void run(double arg0) {
		Visualization vis = getVisualization();
		Display display = vis.getDisplay(0);
		ChatVisualizationPanePrefuse cvp = (ChatVisualizationPanePrefuse)display.getParent();
	    Point2D.Double oldposition = cvp.getFix();
	    Point2D.Double newposition = new Point2D.Double(cvp.getMiddle_item().getX(),cvp.getMiddle_item().getY());
	    display.panAbs(oldposition.x-newposition.x, oldposition.y-newposition.y);
	}

}
