package de.feu.cv.guiComponentsP.prefuseP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import de.feu.cv.applicationLogicP.Resources;
import de.feu.cv.guiComponentsP.prefuseP.controlP.OverviewTravellingControl;

import prefuse.Display;
import prefuse.util.display.DisplayLib;
import prefuse.util.display.ItemBoundsListener;
import prefuse.util.display.PaintListener;

/**
 * The panel with overview and control components
 * @author Verena Kunz
 *
 */
public class OverviewAndControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JCheckBox autofocusCheckBox = null;
	private JButton resetButton = null;
	/**
	 * The corresponding panel with main display.
	 */
	private ChatVisualizationPanePrefuse pane;
	/**
	 * The overview display.
	 */
	private Display overview;
	private JPanel overviewPanel = null;
	private JPanel controlPanel = null;

	/**
	 * This is the default constructor.
	 */
	public OverviewAndControlPanel(ChatVisualizationPanePrefuse pane) {
		super();
		this.pane = pane;
		initialize();
	}

	/**
	 * This method initializes this.
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setLayout(new BorderLayout());
		this.add(getOverviewPanel(), BorderLayout.CENTER);
		this.add(getControlPanel(), BorderLayout.SOUTH);
	}

	/**
	 * This method initializes autofocusCheckBox.	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getAutofocusCheckBox() {
		if (autofocusCheckBox == null) {
			autofocusCheckBox = new JCheckBox();
			autofocusCheckBox.setSelected(true);
			autofocusCheckBox.setText(Resources.getString("lab_autofocus"));
			autofocusCheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (autofocusCheckBox.isSelected()){
						pane.setAutofocus(true);
					}
					else {
						pane.setAutofocus(false);
					}
					pane.setFocusToTextInput();
				}
			});
		}
		return autofocusCheckBox;
	}

	/**
	 * This method initializes resetButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getResetButton() {
		if (resetButton == null) {
			resetButton = new JButton(Resources.getString("bu_reset"));
			resetButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					pane.resetView();
					pane.setAutofocus(true);
				}
			});
		}
		return resetButton;
	}
	
	/**
	 * Set the selection status of autofocus checkbox.
	 * @param selected <code>true </code>if selected
	 */
	public void setAutofocusSelected(boolean selected){
		autofocusCheckBox.setSelected(selected);
	}
	
	/**
	 *  This method initializes the overview panel.
	 * @return the overview Panel
	 */
	private Display getOverview(){
		if (overview == null){
			overview = new Display(pane.getVisualization());
	        overview.setBackground(Color.gray);
	        addListenersToOverview();
	        
		}
		return overview;
	}
	
	/**
	 * Add listener to the overview.
	 */
	private void addListenersToOverview() {
		
		overview.addComponentListener(new ComponentAdapter(){

			public void componentResized(ComponentEvent arg0) {
				fitItemsInDisplay(overview);
			}       	
        });
		overview.addControlListener(new OverviewTravellingControl());
        overview.addItemBoundsListener(new ItemBoundsListener(){
			public void itemBoundsChanged(Display d) {
				fitItemsInDisplay(d);
			}       	
        });
        overview.addPaintListener(new PaintListener(){
			public void postPaint(Display d, Graphics2D g) {
				//System.out.println("postpaint");
				Display display = pane.getDisplay();
				//System.out.println("displaysize: "+ display.getSize());
				// get bounding rectangle of main display
				Rectangle2D itembounds = display.getItemBounds();
				//System.out.println("itembounds: " + itembounds);
				Rectangle2D.Double mainbounds = new Rectangle2D.Double();
				mainbounds.height = itembounds.getHeight()*display.getScale();
				mainbounds.width = itembounds.getWidth()*display.getScale();
				double itemboundsX = itembounds.getX();
				double itemboundsY = itembounds.getY();
			
				mainbounds.x = (itemboundsX*display.getScale() - display.getDisplayX());
				mainbounds.y = (itemboundsY*display.getScale() - display.getDisplayY());
				//System.out.println("mainbounds :" + mainbounds);
							
				// create border rectangle for overview display					
				double scale;
				double a = mainbounds.height/mainbounds.width;
				double b = (double)d.getHeight()/(double)d.getWidth();
				
				boolean fillvertical = (a > b);
				
				if (fillvertical){
					//System.out.println("fillvertical");
					scale = d.getHeight()/mainbounds.height;
				}			
				else{
					//System.out.println("fillhorizontal");
					scale = d.getWidth()/mainbounds.width;
				}
				
				//System.out.println("Scale: "+ scale);
	
				Rectangle2D.Double border_rect = new Rectangle2D.Double();
				
				if (fillvertical){
					//border_rect.x = -mainbounds.x * scale + (display.getWidth()/factor-mainbounds.width*scale)/2;
					border_rect.x = -mainbounds.x * scale + (d.getWidth()-mainbounds.width*scale)/2;
					border_rect.y = -mainbounds.y * scale;
				}
				else {
					border_rect.x = -mainbounds.x * scale;
					//border_rect.y = -mainbounds.y * scale + (display.getHeight()/factor-mainbounds.height*scale)/2;
					border_rect.y = -mainbounds.y * scale + (d.getHeight()-mainbounds.height*scale)/2;
				}			
				
				border_rect.width = display.getWidth() * scale;
				border_rect.height = display.getHeight() * scale;	
				
				//System.out.println("border_rect: "+border_rect);
				// draw border in overview
				g.setColor(new Color(0,0,0,50));			
				g.fill(border_rect);
				
			}

			public void prePaint(Display d, Graphics2D g) {
				
			}
        	
        });
		
	}
	
	/**
	 * Fit the display view to item bounds.
	 * @param d the display
	 */
	protected void fitItemsInDisplay(Display d) {

		Rectangle2D m_temp = new Rectangle2D.Double();
    	d.getItemBounds(m_temp);
    	
    	boolean displaynotnull = d.getWidth()>0 && d.getHeight()>0;
    	boolean itemboundsnotnull = m_temp.getWidth()>0 && m_temp.getHeight()>0;
    	
    	if (displaynotnull && itemboundsnotnull){
    		DisplayLib.fitViewToBounds(d, m_temp, 0);
    		d.repaint();
    	}
		
	}
	

	/**
	 * This method initializes overviewPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getOverviewPanel() {
		if (overviewPanel == null) {
			overviewPanel = new JPanel();
			overviewPanel.setLayout(new BorderLayout());
			overviewPanel.add(getOverview(), BorderLayout.CENTER);
		}
		return overviewPanel;
	}

	/**
	 * This method initializes controlPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getControlPanel() {
		if (controlPanel == null) {
			controlPanel = new JPanel();
			controlPanel.setLayout(new BorderLayout());
			controlPanel.add(getAutofocusCheckBox(), BorderLayout.NORTH);
			controlPanel.add(getResetButton(), BorderLayout.SOUTH);
			controlPanel.setBorder(BorderFactory.createTitledBorder("Steuerung"));
		}
		return controlPanel;
	}

}
