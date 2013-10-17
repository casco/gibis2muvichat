package de.feu.cv.guiComponentsP.prefuseP.controlP;

import java.awt.event.MouseEvent;

import de.feu.cv.guiComponentsP.prefuseP.ChatVisualizationPanePrefuse;

import prefuse.Visualization;
import prefuse.controls.Control;
import prefuse.controls.ControlAdapter;
import prefuse.data.tuple.TupleSet;
import prefuse.util.ui.UILib;
import prefuse.visual.VisualItem;


/**
 * Focus listener for chat window. Selection of messages in display.
 * @author Verena Kunz
 *
 */
public class ChatControl extends ControlAdapter{
	
    private String group = Visualization.FOCUS_ITEMS;
    protected int button = Control.LEFT_MOUSE_BUTTON;
	private ChatVisualizationPanePrefuse pane;
	
	/**
	 * Creates a new chat control listener.
	 * @param pane
	 */
	public ChatControl(ChatVisualizationPanePrefuse pane) {
		this.pane = pane;
	}
	
    /**
     * @see prefuse.controls.Control#itemClicked(prefuse.visual.VisualItem, java.awt.event.MouseEvent)
     */
    public void itemClicked(VisualItem item, MouseEvent e) {

        if ( UILib.isButtonPressed(e, button))
        	
        {

        	Visualization vis = item.getVisualization();
        	TupleSet ts = vis.getFocusGroup(group);
            if ( !ts.containsTuple(item) ) {
            	// select
                ts.setTuple(item);               
            }
            else if ( e.isControlDown() ) {
            	// deselect
                ts.removeTuple(item);

            }
            vis.run("update_color");
            // set selection in model
            pane.setSelection();
        }
    }
	
}