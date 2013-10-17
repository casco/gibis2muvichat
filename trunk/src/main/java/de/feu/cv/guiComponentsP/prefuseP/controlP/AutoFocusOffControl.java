package de.feu.cv.guiComponentsP.prefuseP.controlP;

import java.awt.event.MouseEvent;

import de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse;

import prefuse.Display;
import prefuse.controls.ControlAdapter;


/**
 * Control to set autofocus off, when mouse is dragged.
 * @author Verena Kunz
 *
 */
public class AutoFocusOffControl extends ControlAdapter {


	/**
	 * Sets the autofocus off.
	 * @see prefuse.controls.ControlAdapter#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		Display display = (Display)e.getComponent();
		ChatVisualizationPanePrefuse pane = (ChatVisualizationPanePrefuse) display.getParent();
		pane.setAutofocus(false);
	}

}
