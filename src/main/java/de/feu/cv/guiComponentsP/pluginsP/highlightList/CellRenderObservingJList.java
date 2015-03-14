package de.feu.cv.guiComponentsP.pluginsP.highlightList;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by alejandrofernandez on 3/14/15.
 */
public class CellRenderObservingJList extends JList implements Observer {

    @Override
    public void setCellRenderer(ListCellRenderer listCellRenderer) {
        super.setCellRenderer(listCellRenderer);
        ((VisualizationPaneRenderer)listCellRenderer).addObserver(this);
    }

    public void update(Observable observable, Object o) {
        this.repaint(this.getBounds());
    }
}
