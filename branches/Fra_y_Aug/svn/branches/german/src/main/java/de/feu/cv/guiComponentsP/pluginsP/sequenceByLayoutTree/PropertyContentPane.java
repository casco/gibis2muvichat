package de.feu.cv.guiComponentsP.pluginsP.sequenceByLayoutTree;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import de.feu.cv.applicationLogicP.Resources;
import de.feu.cv.guiComponentsP.prefuseP.PropertyContentPanePrefuse;

/**
 * The panel to configure the properties of visualization SequenceByLayoutTree.
 * @author Verena Kunz
 *
 */
public class PropertyContentPane extends PropertyContentPanePrefuse {

	private static final long serialVersionUID = 1L;
	//private JPanel spacingPanel;
	private JLabel nodespacingLabel;
	private JTextField nodeSpacingTextField;
	private JPanel spacingPanel;
	private JLabel sequenzialSpacingLabel;
	private JTextField sequezialSpacingTextField;

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
		GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
		gridBagConstraints23.gridx = 0;
		gridBagConstraints23.gridy = 3;	
		gridBagConstraints23.fill = GridBagConstraints.HORIZONTAL;
		this.add(getSpacingPanel(),gridBagConstraints23);
	}

	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.PropertyContentPanel#resetProperties()
	 */
	public void resetProperties() {
		super.resetProperties();
		sequezialSpacingTextField.setText(visualProperties.getProperty("sequenzialspacing"));
		nodeSpacingTextField.setText(visualProperties.getProperty("nodespacing"));
	}



	/**
	 * This method initializes spacingPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	protected JPanel getSpacingPanel() {
		if (spacingPanel == null) {
			sequenzialSpacingLabel = new JLabel();
			sequenzialSpacingLabel.setText(Resources.getString("lab_sequenzialspacing"));
			sequenzialSpacingLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			nodespacingLabel = new JLabel();
			nodespacingLabel.setText(Resources.getString("lab_nodespacing"));
			nodespacingLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 0;	
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 0;	

			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 1;	
			
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 1;	
			spacingPanel = new JPanel();
			spacingPanel.setLayout(new GridBagLayout());
			spacingPanel.setBorder(BorderFactory.createTitledBorder(null, Resources.getString("lab_spacing_between"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			spacingPanel.add(nodespacingLabel, gridBagConstraints3);
			spacingPanel.add(getNodeSpacingTextField(), gridBagConstraints4);

			spacingPanel.add(sequenzialSpacingLabel, gridBagConstraints5);
			spacingPanel.add(getSequezialSpacingTextField(), gridBagConstraints6);
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


	/**
	 * This method initializes sequezialSpacingTextField.	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSequezialSpacingTextField() {
		if (sequezialSpacingTextField == null) {
			sequezialSpacingTextField = new JTextField();
			sequezialSpacingTextField.setPreferredSize(new Dimension(30, 20));
			sequezialSpacingTextField.setText(visualProperties.getProperty("sequenzialspacing"));
			sequezialSpacingTextField.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String numberstring = sequezialSpacingTextField.getText();
					if (isValidNumber(numberstring)){
						visualProperties.setProperty("sequenzialspacing", numberstring);
						setFocusToTextInput();
					}
				}
			});
		}
		return sequezialSpacingTextField;
	}




}
