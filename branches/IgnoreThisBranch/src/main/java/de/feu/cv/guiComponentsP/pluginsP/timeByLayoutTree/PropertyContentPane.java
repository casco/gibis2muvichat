package de.feu.cv.guiComponentsP.pluginsP.timeByLayoutTree;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import de.feu.cv.applicationLogicP.Resources;
import de.feu.cv.guiComponentsP.prefuseP.PropertyContentPanePrefuse;

/**
 * The panel to configure the properties of visualization timeByLayoutTree.
 * @author Verena Kunz
 *
 */
public class PropertyContentPane extends PropertyContentPanePrefuse {

	private static final long serialVersionUID = 1L;
	private JPanel layoutPanel;
	private JTextField pixelPerSecondTextField;
	private JLabel pixelPerSecondLabel;
	private JLabel nodespacingLabel;
	private JTextField nodeSpacingTextField;
	private JPanel spacingPanel;

	/**
	 * This is the default constructor.
	 */
	public PropertyContentPane(VisualizationProperties visualProperties) {
		super(visualProperties);
	}


	/**
	 * This method initializes this.
	 */
	protected void initialize(){ 	
		super.initialize();
		GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
		gridBagConstraints22.gridx = 0;
		gridBagConstraints22.gridy = 2;	
		gridBagConstraints22.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
		gridBagConstraints23.gridx = 0;
		gridBagConstraints23.gridy = 3;	
		gridBagConstraints23.fill = GridBagConstraints.HORIZONTAL;
		this.add(getLayoutPanel(), gridBagConstraints22);
		this.add(getSpacingPanel(),gridBagConstraints23);
	}

	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.PropertyContentPanel#resetProperties()
	 */
	public void resetProperties() {
		super.resetProperties();
		pixelPerSecondTextField.setText(visualProperties.getProperty("pixelpersecond"));
		nodeSpacingTextField.setText(visualProperties.getProperty("nodespacing"));
		
	}

	/**
	 * This method initializes layoutPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getLayoutPanel() {
		if (layoutPanel == null) {
			pixelPerSecondLabel = new JLabel();
			pixelPerSecondLabel.setText(Resources.getString("lab_pixelpersecond"));
			pixelPerSecondLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;	
			gridBagConstraints1.anchor = GridBagConstraints.EAST;
			gridBagConstraints1.insets = new Insets(0, 0, 0, 5);
			layoutPanel = new JPanel();
			layoutPanel.setLayout(new GridBagLayout());
			layoutPanel.setBorder(BorderFactory.createTitledBorder(null, "Layout", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			layoutPanel.add(getPixelPerSecondTextField(), gridBagConstraints);
			layoutPanel.add(pixelPerSecondLabel, gridBagConstraints1);
		}
		return layoutPanel;
	}


	/**
	 * This method initializes pixelPerSecondTextField.	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getPixelPerSecondTextField() {
		if (pixelPerSecondTextField == null) {
			pixelPerSecondTextField = new JTextField();
			pixelPerSecondTextField.setPreferredSize(new Dimension(30, 20));
			pixelPerSecondTextField.setText(visualProperties.getProperty("pixelpersecond"));
			pixelPerSecondTextField.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String numberstring = pixelPerSecondTextField.getText();
					if (isValidNumber(numberstring)){
						visualProperties.setProperty("pixelpersecond", numberstring);
						setFocusToTextInput();
					}
				}
			});
		}
		return pixelPerSecondTextField;
	}
	
	/**
	 * This method initializes spacingPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	protected JPanel getSpacingPanel() {
		if (spacingPanel == null) {
			nodespacingLabel = new JLabel();
			nodespacingLabel.setText(Resources.getString("lab_nodespacing"));
			nodespacingLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 0;	
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			//gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 0;	
			gridBagConstraints4.anchor = GridBagConstraints.EAST;
			gridBagConstraints4.insets = new Insets(0, 0, 0, 5);

			spacingPanel = new JPanel();
			spacingPanel.setLayout(new GridBagLayout());
			spacingPanel.setBorder(BorderFactory.createTitledBorder(null, Resources.getString("lab_spacing_between"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			spacingPanel.add(nodespacingLabel, gridBagConstraints3);
			spacingPanel.add(getNodeSpacingTextField(), gridBagConstraints4);

		}
		return spacingPanel;
	}


	/**
	 * This method initializes nodeSpacingTextField.	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNodeSpacingTextField() {
		if (nodeSpacingTextField == null) {
			nodeSpacingTextField = new JTextField();
			nodeSpacingTextField.setPreferredSize(new Dimension(30, 20));
			nodeSpacingTextField.setText(visualProperties.getProperty("nodespacing"));
			nodeSpacingTextField.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println(nodeSpacingTextField);
					String numberstring = nodeSpacingTextField.getText();
					if (isValidNumber(numberstring)){
						visualProperties.setProperty("nodespacing", numberstring);
						setFocusToTextInput();
					}
				}
			});
		}
		return nodeSpacingTextField;
	}




}
