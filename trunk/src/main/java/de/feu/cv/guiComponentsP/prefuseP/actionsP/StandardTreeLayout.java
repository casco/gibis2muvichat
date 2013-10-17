package de.feu.cv.guiComponentsP.prefuseP.actionsP;

import prefuse.action.layout.graph.NodeLinkTreeLayout;

/**
 * Subclass of NodeLinkTreeLayout which implements OrientationTreeLayout.
 * @author Verena Kunz
 *
 */
public class StandardTreeLayout extends NodeLinkTreeLayout implements OrientationTreeLayout {

	
	/**
	 * This ist the default constructor.
	 */
	public StandardTreeLayout(String group) {
		super(group);
	}

}
