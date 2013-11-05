package de.feu.cv.guiComponentsP.pluginsP.simpleTree;

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
 * The panel to configure the properties of visualization SequenceByLayoutTree.
 * @author Verena Kunz
 *
 */
public class PropertyContentPane extends PropertyContentPanePrefuse {
	
	private static final long serialVersionUID = 5150292460258060422L;
	private JLabel breadthspacingLabel;
	private JLabel depthspacingLabel;
	private JLabel subtreespacingLabel;
	private JTextField breadthspacingTextField;
	private JTextField depthspacingTextField;
	private JTextField subtreespacingTextField;
	protected JPanel spacingPanel;

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
		gridBagConstraints22.gridy = 3;	
		gridBagConstraints22.fill = GridBagConstraints.HORIZONTAL;
		this.add(getSpacingPanel(), gridBagConstraints22);
	}

	/**
	 * This method initializes spacingPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	protected JPanel getSpacingPanel() {
		if (spacingPanel == null) {
			subtreespacingLabel = new JLabel();
			subtreespacingLabel.setText(Resources.getString("lab_subtreespacing"));
			subtreespacingLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			depthspacingLabel = new JLabel();
			depthspacingLabel.setText(Resources.getString("lab_depthspacing"));
			depthspacingLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			breadthspacingLabel = new JLabel();
			breadthspacingLabel.setText(Resources.getString("lab_breadthspacing"));
			breadthspacingLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.gridy = 2;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.gridx = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.NONE;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.insets = new Insets(2, 5, 2, 5);
			gridBagConstraints6.gridy = 2;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.insets = new Insets(2, 5, 2, 5);
			gridBagConstraints4.gridy = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.insets = new Insets(2, 5, 2, 5);
			gridBagConstraints3.gridy = 1;
			spacingPanel = new JPanel();
			spacingPanel.setLayout(new GridBagLayout());
			spacingPanel.setBorder(BorderFactory.createTitledBorder(null, Resources.getString("lab_spacing_between"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			spacingPanel.add(depthspacingLabel, gridBagConstraints4);
			spacingPanel.add(getDepthspacingTextField(), gridBagConstraints1);
			spacingPanel.add(breadthspacingLabel, gridBagConstraints3);
			spacingPanel.add(subtreespacingLabel, gridBagConstraints6);
			spacingPanel.add(getBreadthspacingTextField(), gridBagConstraints);
			spacingPanel.add(getSubtreespacingTextField(), gridBagConstraints2);
		}
		return spacingPanel;
	}
	

	/**
	 * This method initializes breadthspacingTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getBreadthspacingTextField() {
		if (breadthspacingTextField == null) {
			breadthspacingTextField = new JTextField();
			breadthspacingTextField.setName("schnaff");
			breadthspacingTextField.setPreferredSize(new Dimension(30, 20));
			breadthspacingTextField.setText(visualProperties.getProperty("breadthspacing"));
			breadthspacingTextField.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println(e.getActionCommand());
					//System.out.println(((JComponent)e.getSource()).getName());
					String numberstring = breadthspacingTextField.getText();
					if (isValidNumber(numberstring)){
						visualProperties.setProperty("breadthspacing", numberstring);
						setFocusToTextInput();
					}
				}
			});
		}
		return breadthspacingTextField;
	}


	/**
	 * This method initializes depthspacingTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getDepthspacingTextField() {
		if (depthspacingTextField == null) {
			depthspacingTextField = new JTextField();
			depthspacingTextField.setPreferredSize(new Dimension(30, 20));
			depthspacingTextField.setText(visualProperties.getProperty("depthspacing"));
			depthspacingTextField.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String numberstring = depthspacingTextField.getText();
					if (isValidNumber(numberstring)){
						visualProperties.setProperty("depthspacing", numberstring);
						setFocusToTextInput();
					}
				}
			});
			depthspacingTextField.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					//System.out.println("focusLost()"); //
					String numberstring = depthspacingTextField.getText();
					//System.out.println(numberstring);
					if (isValidNumber(numberstring)){
						visualProperties.setProperty("depthspacing", numberstring);
						setFocusToTextInput();
					}
				}
			});
		}
		return depthspacingTextField;
	}


	/**
	 * This method initializes subtreespacingTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSubtreespacingTextField() {
		if (subtreespacingTextField == null) {
			subtreespacingTextField = new JTextField();
			subtreespacingTextField.setPreferredSize(new Dimension(30, 20));
			subtreespacingTextField.setText(visualProperties.getProperty("subtreespacing"));
			subtreespacingTextField.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String numberstring = subtreespacingTextField.getText();
					if (isValidNumber(numberstring)){
						visualProperties.setProperty("subtreespacing", numberstring);
						setFocusToTextInput();
					}
				}
			});
		}
		return subtreespacingTextField;
	}
	
	/**
	 * @see de.feu.cv.guiComponentsP.prefuseP.PropertyContentPanePrefuse#resetProperties()
	 */
	public void resetProperties() {
		super.resetProperties();
		depthspacingTextField.setText(visualProperties.getProperty("depthspacing"));
		breadthspacingTextField.setText(visualProperties.getProperty("breadthspacing"));
		subtreespacingTextField.setText(visualProperties.getProperty("subtreespacing"));
		
		
	}

}
