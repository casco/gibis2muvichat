package de.feu.cv.guiComponentsP.prefuseP;

import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import prefuse.render.LabelRenderer;
import prefuse.visual.VisualItem;

/**
 * Label renderer for chat messages.
 * @author Verena Kunz
 *
 */
public class ChatLabelRenderer extends LabelRenderer {

	/**
	 * Width in pixel until line breaks.
	 */
	int linebreakwidth = 80;
	/**
	 * Defines the text content for the labels.
	 * nickname and message text
	 * @see prefuse.render.LabelRenderer#getText(prefuse.visual.VisualItem)
	 */
	@Override
	protected String getText(VisualItem item) {
        String s = "";

        if ((item.canGetString("mType")) && (item.getString("mType") != null)){
            s = s + "-" +item.getString("mType");
            if ((item.canGetString("rType")) && (item.getString("rType") != null)){
                s = s + " (" +item.getString("rType") + ")";
            }
            s = s + "-\n";
        }




        if (( item.canGetString("nick") && (item.getString("nick") != null)) ) {
            s = s + item.getString("nick") + ":\n";   
        }

        if ( item.canGetString("text") ) {
        	String text = item.getString("text");

    	    m_font = item.getFont();   	    
    	    AttributedString astr = new AttributedString(text);
    	    astr.addAttribute(TextAttribute.FONT, m_font);
    	    AttributedCharacterIterator iter = astr.getIterator();
    	    FontRenderContext frc = new FontRenderContext(null, true, true);
    	    LineBreakMeasurer measurer = new LineBreakMeasurer(iter, frc);
    	    TextLayout layout = null;
    	    String textwithlinebreaks = "";
    	    int startposition = 0;
    	    
    	    while (measurer.getPosition() < iter.getEndIndex())
    	    {
    	    	//get the next line which fits into linebreakwidth
    	    	layout = measurer.nextLayout(linebreakwidth);
    	    	int charcount = layout.getCharacterCount();
    	    	textwithlinebreaks = textwithlinebreaks + text.substring(startposition, startposition + charcount) + "\n";
    	    	startposition = startposition + charcount;
    	    }
    	    textwithlinebreaks = textwithlinebreaks.substring(0, textwithlinebreaks.length()-1);
        	// whole content for the node
            s = s + textwithlinebreaks;            
        }

        return s;
	}
	/**
	 * Sets the linebreakwidth.
	 * @param linebreakwidth the linebreakwidth in pixel
	 */
	public void setLinebreakwidth(int linebreakwidth) {
		this.linebreakwidth = linebreakwidth;
	}

}