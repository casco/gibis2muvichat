package de.feu.cv.guiComponentsP.chatWindowComponentsP;

import java.awt.Window;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.feu.cv.guiComponentsP.dialogP.ChatWindowPropertyDialog;

/**
 * Abstract class for the property content panel.
 * @author Verena Kunz
 *
 */
public abstract class PropertyContentPanel extends JPanel {
	
	/**
	 * Resets the properties to default.
	 */
	public abstract void resetProperties();
	
	
	/**
	 * Sets the focus to text field, if this is a visualization of a
	 * live chat.
	 */
	public void setFocusToTextInput(){
		Window window = SwingUtilities.getWindowAncestor(this);
		if (window instanceof ChatWindowPropertyDialog)
			((ChatWindowPropertyDialog)window).setFocusToTextInput();
	}
	
	

}
