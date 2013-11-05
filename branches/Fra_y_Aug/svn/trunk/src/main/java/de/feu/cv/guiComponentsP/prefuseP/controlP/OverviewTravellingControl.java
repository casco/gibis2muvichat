package de.feu.cv.guiComponentsP.prefuseP.controlP;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse;

import prefuse.Display;
import prefuse.controls.ControlAdapter;
import prefuse.visual.VisualItem;

/**
 * This class changes the main display position when the overview is clicked.
 * @author Verena Kunz
 *
 */
public class OverviewTravellingControl extends ControlAdapter{

	/**
	 * @see prefuse.controls.ControlAdapter#itemClicked(prefuse.visual.VisualItem, java.awt.event.MouseEvent)
	 */
	@Override
	public void itemClicked(VisualItem item, MouseEvent e) {
		setMainDisplayToClickedPosition(e);
	}

	/**
	 * @see prefuse.controls.ControlAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		setMainDisplayToClickedPosition(e);

	}
	
	/**
	 * Sets the focus of main display to the point of the mouse event.
	 * @param e the mouse event
	 */
	private void setMainDisplayToClickedPosition(MouseEvent e){
		
		Display overview = (Display)e.getComponent();
		Point2D newposition = overview.getAbsoluteCoordinate(e.getPoint(), null);
		Display maindisplay = overview.getVisualization().getDisplay(0);

		maindisplay.panToAbs(newposition);
		maindisplay.repaint();
		
		// disable autofocus
		ChatVisualizationPanePrefuse pane = (ChatVisualizationPanePrefuse) maindisplay.getParent();
		pane.setAutofocus(false);
	}

}
