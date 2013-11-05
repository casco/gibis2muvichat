package de.feu.cv.guiComponentsP.prefuseP.actionsP;

import java.awt.geom.Point2D;

import de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.Action;

/**
 * Action to pan the display to the new item.
 * @author Verena Kunz
 *
 */
public class PanAction extends Action{
	

	int margin = 20;
	int duration;
	
	
	/**
	 * Creates a new pan action.
	 * @param duration the duration of the pan action.
	 */
	public PanAction(int duration) {
		super();
		this.duration = duration;
	}

	/**
	 * Pans display to new item.
	 * @see prefuse.action.Action#run(double)
	 */
	@Override
	public void run(double arg0) {
		
		Visualization vis = getVisualization();
		Display display = vis.getDisplay(0);
		ChatVisualizationPanePrefuse cvp = (ChatVisualizationPanePrefuse)display.getParent();
		// get position of newest item
		double x;
		double y;
		
		if (cvp.getNewVi()!=null){
			x = cvp.getNewVi().getX();
			y = cvp.getNewVi().getY();
			
			// get the orientation of the tree
			OrientationTreeLayout tl =  (OrientationTreeLayout) cvp.getTreeLayout();
			int orientation = tl.getOrientation();
			
			double zoom = display.getScale();
			double itemheight = cvp.getNewVi().getBounds().getHeight();	
			double itemwidth = cvp.getNewVi().getBounds().getWidth();
			
			double delta_x = 0;
			double delta_y = 0;
			
			if (orientation == Constants.ORIENT_LEFT_RIGHT){
				// new item middle right
				delta_x = (display.getWidth()/2)/zoom-itemwidth/2 - margin/zoom ;
			}
			
			if (orientation == Constants.ORIENT_RIGHT_LEFT){
				// new item middle left
				delta_x = -((display.getWidth()/2)/zoom-itemwidth/2 - margin/zoom) ;
			}
			
			if (orientation == Constants.ORIENT_TOP_BOTTOM){
				// new item middle bottom
				delta_y = (display.getHeight()/2)/zoom-itemheight/2- margin/zoom;
			}
			
			if (orientation == Constants.ORIENT_BOTTOM_TOP){
				// new item middle top
				delta_y = -((display.getHeight()/2)/zoom-itemheight/2- margin/zoom);
			}
			
					
			x = x-delta_x;
			y = y-delta_y;
			
			// pan display to this postion
			
			if (duration == 0)
				display.panToAbs(new Point2D.Double(x,y));
			else
				
			display.animatePanToAbs(new Point2D.Double(x,y),duration);
//			System.out.println(display.getTransform().getTranslateX());
//			System.out.println(display.getTransform().getTranslateY());
//			
//			System.out.println("DisplaySize: "+display.getSize());
		}

	}



}
