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
public class PreLayoutAction extends Action{

	/**
	 * Notes the middle item.
	 * @see prefuse.action.Action#run(double)
	 */
	@Override
	public void run(double arg0) {
		Visualization vis = getVisualization();
		Display display = vis.getDisplay(0);
		ChatVisualizationPanePrefuse cvp = (ChatVisualizationPanePrefuse)display.getParent();
	    cvp.setFix(new Point2D.Double(cvp.getMiddle_item().getX(),cvp.getMiddle_item().getY()));

		
	}

}
