package de.feu.cv.guiComponentsP.pluginsP.highlightList;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
/**
 * The cell renderer for the list HighlightList
 * @author Verena Kunz
 *
 */
public class VisualizationPaneRenderer extends DefaultListCellRenderer  {

	private static final long serialVersionUID = 1L;
	private ArrayList parents ;
	private JLabel label;
	private Font standardfont;
	private Font ancestorsfont;
	private Color selectionbgcolor;
	private Color selectionfontcolor;

    private Observable observableHelper;
	
	  
	/**
	 * Creates the CellRenderer
	 */
	public VisualizationPaneRenderer() {
			parents = new ArrayList();
            observableHelper = new Observable();
	}


	/** 
	 * 
	 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	public Component getListCellRendererComponent(JList list,
	                                                Object value,
	                                                int index,
	                                                boolean isSelected,
	                                                boolean hasFocus ) {
		
	    label = (JLabel)super.getListCellRendererComponent(list,
	                                                 value,
	                                                 index,
	                                                 isSelected,
	                                                 hasFocus);
	    // set the standard font
	    label.setFont(standardfont);

	    
	    // highlight the parent messages
	    Iterator it = parents.iterator();
	    while (it.hasNext()) {
	    	if (value.equals(it.next())){
	    		label.setFont(ancestorsfont);
	    	}
	    }
	    
	    // define color for selected entry
	    if (isSelected){

	    	label.setBackground(selectionbgcolor);
	    	label.setForeground(selectionfontcolor);
	    }
	    	
	      
	    return(label);
	  }
	/**
	 * Sets the parents to highlight.
	 * @param parents
	 */
	public void setParents(ArrayList parents){
		this.parents = parents;
		
	}

	/**
	 * Sets the standard font. 
	 * @param standardfont
	 */
	public void setStandardFont(Font standardfont) {
		this.standardfont = standardfont;
	}

	/**
	 * Sets the font for the ancestor elements.
	 * @param ancestorsfont
	 */
	public void setAncestorsFont(Font ancestorsfont) {
		this.ancestorsfont = ancestorsfont;
	}


	/**
	 * Sets the background color for the selected elements.
	 * @param selectionbgcolor
	 */
	public void setSelectionBgColor(Color selectionbgcolor) {
		this.selectionbgcolor = selectionbgcolor;
	}

	/**
	 * Sets the font color for the selected elements
	 * @param selectionfontcolor
	 */
	public void setSelectionFontColor(Color selectionfontcolor) {
		this.selectionfontcolor = selectionfontcolor;
	}

    public void addObserver(CellRenderObservingJList cellRenderObservingJList) {
        observableHelper.addObserver(cellRenderObservingJList);
    }

}