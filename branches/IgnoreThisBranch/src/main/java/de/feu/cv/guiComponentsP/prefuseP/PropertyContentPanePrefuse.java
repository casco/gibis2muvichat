package de.feu.cv.guiComponentsP.prefuseP;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import de.feu.cv.applicationLogicP.Resources;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.PropertyContentPanel;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties;

/**
 * The panel to configure the properties of prefuse visualizations.
 * @author Verena Kunz
 *
 */
public class PropertyContentPanePrefuse extends PropertyContentPanel {

	private static final long serialVersionUID = 1L;
	protected VisualProperties visualProperties;
	private JPanel layouthelppanel;
	private JPanel orientationPanel;
	private JComboBox orientationComboBox;
	private JPanel nodePanel = null;
	private JPanel standardPanel = null;
	private JLabel linebreakwidthLabel = null;
	private JTextField linebreakwidthTextField = null;

	/**
	 * This is the default constructor.
	 */
	public PropertyContentPanePrefuse(VisualPropertiesPrefuse visualProperties) {
		super();
		this.visualProperties = visualProperties;
		initialize();
	}


	/**
	 * This method initializes this.
	 */
	protected void initialize(){ 	
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 0;
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.gridx = 0;
		gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints7.gridy = 1;
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 0;
		gridBagConstraints12.weighty = 1.0D;
		gridBagConstraints12.gridy = 4;
		gridBagConstraints12.fill = GridBagConstraints.VERTICAL;
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.add(getLayoutHelpPanel(), gridBagConstraints12);
		this.add(getStandardPanel(), gridBagConstraints2);
	}

	/**
	 * This panel helps to set vertical alignment top.
	 * @return an empty panel
	 */
	private JComponent getLayoutHelpPanel() {
		layouthelppanel = new JPanel();
		return layouthelppanel;
	}


	/**
	 * Returns a dialog to choose a color.
	 * @return the color chooser dialog
	 */
	protected Color getColorFromChooser() {
    	Color newColor = JColorChooser.showDialog(
            this,"Choose Background Color",Color.BLACK);
    	return newColor;

	}


	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.PropertyContentPanel#resetProperties()
	 */
	public void resetProperties() {
		visualProperties.setDefaultValues();
		orientationComboBox.setSelectedIndex(Integer.parseInt(visualProperties.getProperty("orientation")));
		linebreakwidthTextField.setText(visualProperties.getProperty("linebreakwidth"));

		
	}



	/**
	 * This method initializes orientationPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getOrientationPanel() {
		if (orientationPanel == null) {
//			subtreespacingLabel = new JLabel();
//			subtreespacingLabel.setText(Resources.getString("lab_subtreespacing"));
//			subtreespacingLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
//			depthspacingLabel = new JLabel();
//			depthspacingLabel.setText(Resources.getString("lab_depthspacing"));
//			depthspacingLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
//			breadthspacingLabel = new JLabel();
//			breadthspacingLabel.setText(Resources.getString("lab_breadthspacing"));
//			breadthspacingLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.anchor = GridBagConstraints.EAST;
			gridBagConstraints5.insets = new Insets(2, 5, 2, 5);
			gridBagConstraints5.gridx = 0;
			orientationPanel = new JPanel();
			orientationPanel.setLayout(new GridBagLayout());
			orientationPanel.setBorder(BorderFactory.createTitledBorder(null, Resources.getString("lab_orientation"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			orientationPanel.add(getOrientationComboBox(), gridBagConstraints5);
		}
		return orientationPanel;
	}


	/**
	 * This method initializes orientationComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getOrientationComboBox() {
		if (orientationComboBox == null) {
			String[] values = {Resources.getString("element_orient_left_right")
							  ,Resources.getString("element_orient_right_left")
							  ,Resources.getString("element_orient_top_bottom")
							  ,Resources.getString("element_orient_bottom_top")};
			orientationComboBox = new JComboBox(values);
			orientationComboBox.setSelectedIndex(Integer.parseInt(visualProperties.getProperty("orientation")));
			orientationComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int orientation = orientationComboBox.getSelectedIndex();
					visualProperties.setProperty("orientation", Integer.toString(orientation));
					setFocusToTextInput();
				}
			});
		}
		return orientationComboBox;
	}


//	/**
//	 * This method initializes breadthspacingTextField	
//	 * 	
//	 * @return javax.swing.JTextField	
//	 */
//	private JTextField getBreadthspacingTextField() {
//		if (breadthspacingTextField == null) {
//			breadthspacingTextField = new JTextField();
//			breadthspacingTextField.setPreferredSize(new Dimension(30, 20));
//			breadthspacingTextField.setText(visualProperties.getProperty("breadthspacing"));
//			breadthspacingTextField.addActionListener(new java.awt.event.ActionListener() {
//				public void actionPerformed(java.awt.event.ActionEvent e) {
//					String numberstring = breadthspacingTextField.getText();
//					if (isValidNumber(numberstring)){
//						visualProperties.setProperty("breadthspacing", numberstring);
//						setFocusToTextInput();
//					}
//				}
//			});
//		}
//		return breadthspacingTextField;
//	}


//	/**
//	 * This method initializes depthspacingTextField	
//	 * 	
//	 * @return javax.swing.JTextField	
//	 */
//	private JTextField getDepthspacingTextField() {
//		if (depthspacingTextField == null) {
//			depthspacingTextField = new JTextField();
//			depthspacingTextField.setPreferredSize(new Dimension(30, 20));
//			depthspacingTextField.setText(visualProperties.getProperty("depthspacing"));
//			depthspacingTextField.addActionListener(new java.awt.event.ActionListener() {
//				public void actionPerformed(java.awt.event.ActionEvent e) {
//					String numberstring = depthspacingTextField.getText();
//					if (isValidNumber(numberstring)){
//						visualProperties.setProperty("depthspacing", numberstring);
//						setFocusToTextInput();
//					}
//				}
//			});
//		}
//		return depthspacingTextField;
//	}


//	/**
//	 * This method initializes subtreespacingTextField	
//	 * 	
//	 * @return javax.swing.JTextField	
//	 */
//	private JTextField getSubtreespacingTextField() {
//		if (subtreespacingTextField == null) {
//			subtreespacingTextField = new JTextField();
//			subtreespacingTextField.setPreferredSize(new Dimension(30, 20));
//			subtreespacingTextField.setText(visualProperties.getProperty("subtreespacing"));
//			subtreespacingTextField.addActionListener(new java.awt.event.ActionListener() {
//				public void actionPerformed(java.awt.event.ActionEvent e) {
//					String numberstring = subtreespacingTextField.getText();
//					if (isValidNumber(numberstring)){
//						visualProperties.setProperty("subtreespacing", numberstring);
//						setFocusToTextInput();
//					}
//				}
//			});
//		}
//		return subtreespacingTextField;
//	}
	
	public boolean isValidNumber(String string){
		boolean valid = false;
		try {
			int number = Integer.parseInt(string);
			if (number >= 0 && number < 500){
				valid = true;
			}
		}
		catch (NumberFormatException e){
	
		}
		return valid;
	}


	/**
	 * This method initializes nodePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getNodePanel() {
		if (nodePanel == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints3.weightx = 1.0;
			linebreakwidthLabel = new JLabel();
			linebreakwidthLabel.setText(Resources.getString("lab_linebreakwidth"));
			linebreakwidthLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			nodePanel = new JPanel();
			nodePanel.setLayout(new GridBagLayout());
			nodePanel.setBorder(BorderFactory.createTitledBorder(null, Resources.getString("lab_linebreakwidth"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			nodePanel.add(linebreakwidthLabel, new GridBagConstraints());
			nodePanel.add(getLinebreakwidthTextField(), gridBagConstraints3);
		}
		return nodePanel;
	}


	/**
	 * This method initializes standardPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getStandardPanel() {
		if (standardPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 0;
			standardPanel = new JPanel();
			standardPanel.setLayout(new GridBagLayout());
			standardPanel.add(getOrientationPanel(), gridBagConstraints);
			standardPanel.add(getNodePanel(), gridBagConstraints1);
		}
		return standardPanel;
	}


	/**
	 * This method initializes linebreakwidthTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getLinebreakwidthTextField() {
		if (linebreakwidthTextField == null) {
			linebreakwidthTextField = new JTextField();
			linebreakwidthTextField.setPreferredSize(new Dimension(30, 20));
			linebreakwidthTextField.setText(visualProperties.getProperty("linebreakwidth"));
 			linebreakwidthTextField.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String numberstring = linebreakwidthTextField.getText();
					if (isValidNumber(numberstring)){
						visualProperties.setProperty("linebreakwidth", numberstring);
						setFocusToTextInput();
					}
				}
			});
		}
		return linebreakwidthTextField;
	}


//	/**
//	 * This method initializes spacingPanel	
//	 * 	
//	 * @return javax.swing.JPanel	
//	 */
//	protected JPanel getSpacingPanel() {
//		if (spacingPanel == null) {
//			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints2.gridy = 2;
//			gridBagConstraints2.weightx = 1.0;
//			gridBagConstraints2.gridx = 1;
//			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.fill = GridBagConstraints.NONE;
//			gridBagConstraints1.gridy = 0;
//			gridBagConstraints1.weightx = 1.0;
//			gridBagConstraints1.gridx = 1;
//			GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints.gridy = 1;
//			gridBagConstraints.weightx = 1.0;
//			gridBagConstraints.gridx = 1;
//			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
//			gridBagConstraints6.gridx = 0;
//			gridBagConstraints6.anchor = GridBagConstraints.WEST;
//			gridBagConstraints6.insets = new Insets(2, 5, 2, 5);
//			gridBagConstraints6.gridy = 2;
//			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
//			gridBagConstraints4.gridx = 0;
//			gridBagConstraints4.anchor = GridBagConstraints.WEST;
//			gridBagConstraints4.insets = new Insets(2, 5, 2, 5);
//			gridBagConstraints4.gridy = 0;
//			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints3.gridx = 0;
//			gridBagConstraints3.anchor = GridBagConstraints.WEST;
//			gridBagConstraints3.insets = new Insets(2, 5, 2, 5);
//			gridBagConstraints3.gridy = 1;
//			spacingPanel = new JPanel();
//			spacingPanel.setLayout(new GridBagLayout());
//			spacingPanel.setBorder(BorderFactory.createTitledBorder(null, Resources.getString("lab_spacing"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
//			spacingPanel.add(depthspacingLabel, gridBagConstraints4);
//			spacingPanel.add(getDepthspacingTextField(), gridBagConstraints1);
//			spacingPanel.add(breadthspacingLabel, gridBagConstraints3);
//			spacingPanel.add(subtreespacingLabel, gridBagConstraints6);
//			spacingPanel.add(getBreadthspacingTextField(), gridBagConstraints);
//			spacingPanel.add(getSubtreespacingTextField(), gridBagConstraints2);
//		}
//		return spacingPanel;
//	}
//	




}
