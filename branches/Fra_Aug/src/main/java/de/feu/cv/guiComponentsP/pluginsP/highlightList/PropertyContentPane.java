package de.feu.cv.guiComponentsP.pluginsP.highlightList;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import de.feu.cv.applicationLogicP.Resources;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.PropertyContentPanel;

/**
 * The panel to configure the properties of visualization highlightList.
 * @author Verena Kunz
 *
 */
public class PropertyContentPane extends PropertyContentPanel {

	private static final long serialVersionUID = 1L;
	private JLabel fgcolorLabel = null;
	private JLabel bgcolorLabel = null;
	private JLabel fontsizeLabel = null;
	private JButton fgcolorButton = null;
	private JButton bgcolorButton = null;
	private JComboBox fontsizeComboBox = null;
	private VisualizationProperties visualProperties;
	private JPanel layouthelppanel;
	private JPanel standardPanel = null;
	private JPanel selectionPanel = null;
	private JPanel ancestorsPanel = null;
	private JLabel fgcolorSelectionLabel = null;
	private JLabel bgcolorSelectionLabel = null;
	private JButton fgcolorSelectionButton = null;
	private JButton bgcolorSelectionButton = null;
	private JLabel formatAncestersLabel = null;
	private JComboBox formatAncestorsComboBox = null;
	
	/**
	 * This is the default constructor.
	 */
	public PropertyContentPane(VisualizationProperties visualProperties) {
		super();
		this.visualProperties = visualProperties;
		initialize();
	}


	/**
	 * This method initializes this.
	 */
	private void initialize(){ 	
		GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBagConstraints9.gridx = 0;
		gridBagConstraints9.anchor = GridBagConstraints.WEST;
		gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints9.gridy = 2;
		GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
		gridBagConstraints8.gridx = 0;
		gridBagConstraints8.anchor = GridBagConstraints.WEST;
		gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints8.gridy = 1;
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.gridx = 0;
		gridBagConstraints7.anchor = GridBagConstraints.WEST;
		gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints7.gridy = 0;
		GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
		gridBagConstraints22.gridx = -1;
		gridBagConstraints22.gridy = -1;
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 0;
		gridBagConstraints12.weighty = 1.0D;
		gridBagConstraints12.gridy = 3;
		gridBagConstraints12.fill = GridBagConstraints.VERTICAL;
		fontsizeLabel = new JLabel();
		fontsizeLabel.setText(Resources.getString("lab_fontsize"));
		fontsizeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		bgcolorLabel = new JLabel();
		bgcolorLabel.setText(Resources.getString("lab_bgcolor"));
		bgcolorLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		fgcolorLabel = new JLabel();
		fgcolorLabel.setText(Resources.getString("lab_fontcolor"));
		fgcolorLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		this.setSize(300, 338);
		this.setLayout(new GridBagLayout());
		this.add(getLayoutHelpPanel(), gridBagConstraints12);
		this.add(getStandardPanel(), gridBagConstraints7);
		this.add(getSelectionPanel(), gridBagConstraints8);
		this.add(getAncestorsPanel(), gridBagConstraints9);
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
	 * This method initializes fgcolorButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getFgcolorButton() {
		if (fgcolorButton == null) {
			fgcolorButton = new JButton();
			Color newcolor = new Color(Integer.parseInt(visualProperties.getProperty("fgcolor")));
			fgcolorButton.setBackground(newcolor);
			fgcolorButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Color newcolor = getColorFromChooser();
					fgcolorButton.setBackground(newcolor);
					visualProperties.setProperty("fgcolor", Integer.toString(newcolor.getRGB()));
					setFocusToTextInput();
				}
			});
		}
		return fgcolorButton;
	}

	/**
	 * This method initializes bgcolorButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBgcolorButton() {
		if (bgcolorButton == null) {
			bgcolorButton = new JButton();
			Color newcolor = new Color(Integer.parseInt(visualProperties.getProperty("bgcolor")));
			bgcolorButton.setBackground(newcolor);
			bgcolorButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Color newcolor = getColorFromChooser();
					bgcolorButton.setBackground(newcolor);
					visualProperties.setProperty("bgcolor", Integer.toString(newcolor.getRGB()));
					setFocusToTextInput();
				}
			});
		}
		return bgcolorButton;
	}

	/**
	 * This method initializes fontsizeComboBox.	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getFontsizeComboBox() {
		if (fontsizeComboBox == null) {
			String[] values = {"8","10","12","14","16","18","20","22"};
			fontsizeComboBox = new JComboBox(values);
			fontsizeComboBox.setSelectedItem(visualProperties.getProperty("size"));
			fontsizeComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String size = (String)fontsizeComboBox.getSelectedItem();
					visualProperties.setProperty("size", size);
					setFocusToTextInput();
				}
			});
		}
		return fontsizeComboBox;
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
		fontsizeComboBox.setSelectedItem(visualProperties.getProperty("size"));
		Color newcolor = new Color(Integer.parseInt(visualProperties.getProperty("fgcolor")));
		fgcolorButton.setBackground(newcolor);
		newcolor = new Color(Integer.parseInt(visualProperties.getProperty("bgcolor")));
		bgcolorButton.setBackground(newcolor);
		newcolor = new Color(Integer.parseInt(visualProperties.getProperty("bgcolor_selection")));
		bgcolorSelectionButton.setBackground(newcolor);
		newcolor = new Color(Integer.parseInt(visualProperties.getProperty("fgcolor_selection")));
		fgcolorSelectionButton.setBackground(newcolor);
		formatAncestorsComboBox.setSelectedIndex(Integer.parseInt(visualProperties.getProperty("format_ancestors")));
	}


	/**
	 * This method initializes standardPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getStandardPanel() {
		if (standardPanel == null) {
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.anchor = GridBagConstraints.EAST;
			gridBagConstraints5.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.anchor = GridBagConstraints.EAST;
			gridBagConstraints2.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.anchor = GridBagConstraints.EAST;
			gridBagConstraints3.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 2;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.insets = new Insets(2, 5, 2, 5);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.ipadx = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.insets = new Insets(2, 5, 2, 5);
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.ipadx = 0;
			gridBagConstraints4.ipady = 0;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.insets = new Insets(2, 5, 2, 5);
			standardPanel = new JPanel();
			standardPanel.setLayout(new GridBagLayout());
			standardPanel.setBorder(BorderFactory.createTitledBorder(null, Resources.getString("lab_standard"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			standardPanel.add(fgcolorLabel, gridBagConstraints4);
			standardPanel.add(bgcolorLabel, gridBagConstraints);
			standardPanel.add(fontsizeLabel, gridBagConstraints1);
			standardPanel.add(getFgcolorButton(), gridBagConstraints3);
			standardPanel.add(getBgcolorButton(), gridBagConstraints2);
			standardPanel.add(getFontsizeComboBox(), gridBagConstraints5);
		}
		return standardPanel;
	}


	/**
	 * This method initializes selectionPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSelectionPanel() {
		if (selectionPanel == null) {
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints20.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints20.anchor = GridBagConstraints.EAST;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.anchor = GridBagConstraints.WEST;
			gridBagConstraints16.weightx = 1.0;
			gridBagConstraints16.insets = new Insets(2, 5, 2, 5);
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints13.gridy = 2;
			gridBagConstraints13.weightx = 1.0;
			gridBagConstraints13.anchor = GridBagConstraints.EAST;
			gridBagConstraints13.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints13.gridx = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.insets = new Insets(2, 5, 2, 5);
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.gridy = 2;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 1;
			gridBagConstraints10.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints10.anchor = GridBagConstraints.EAST;
			gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.gridy = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.insets = new Insets(2, 5, 2, 5);
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.gridy = 1;
			bgcolorSelectionLabel = new JLabel();
			bgcolorSelectionLabel.setText(Resources.getString("lab_bgcolor"));
			bgcolorSelectionLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			fgcolorSelectionLabel = new JLabel();
			fgcolorSelectionLabel.setText(Resources.getString("lab_fontcolor"));
			fgcolorSelectionLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			selectionPanel = new JPanel();
			selectionPanel.setLayout(new GridBagLayout());
			selectionPanel.setBorder(BorderFactory.createTitledBorder(null, Resources.getString("lab_selection"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			selectionPanel.add(fgcolorSelectionLabel, gridBagConstraints16);
			selectionPanel.add(bgcolorSelectionLabel, gridBagConstraints6);
			selectionPanel.add(getFgcolorSelectionButton(), gridBagConstraints20);
			selectionPanel.add(getBgcolorSelectionButton(), gridBagConstraints10);

		}
		return selectionPanel;
	}


	/**
	 * This method initializes ancestorsPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getAncestorsPanel() {
		if (ancestorsPanel == null) {
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints23.weightx = 1.0;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints21.weightx = 1.0;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.anchor = GridBagConstraints.EAST;
			gridBagConstraints19.insets = new Insets(0, 0, 0, 5);
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridx = 0;
			gridBagConstraints18.anchor = GridBagConstraints.WEST;
			gridBagConstraints18.insets = new Insets(2, 5, 2, 5);
			gridBagConstraints18.weightx = 1.0;
			gridBagConstraints18.gridy = 2;
			formatAncestersLabel = new JLabel();
			formatAncestersLabel.setText(Resources.getString("lab_format_ancestors"));
			formatAncestersLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.insets = new Insets(2, 5, 2, 5);
			gridBagConstraints17.weightx = 1.0;
			gridBagConstraints17.anchor = GridBagConstraints.WEST;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.gridx = 1;
			gridBagConstraints15.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints15.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints15.anchor = GridBagConstraints.EAST;
			gridBagConstraints15.gridy = 1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 0;
			gridBagConstraints14.insets = new Insets(2, 5, 2, 5);
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			gridBagConstraints14.weightx = 1.0;
			gridBagConstraints14.gridy = 1;

			ancestorsPanel = new JPanel();
			ancestorsPanel.setLayout(new GridBagLayout());
			ancestorsPanel.setBorder(BorderFactory.createTitledBorder(null,Resources.getString("lab_ancestors"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));

			ancestorsPanel.add(formatAncestersLabel, gridBagConstraints18);
			gridBagConstraints23.gridx = 1;
			gridBagConstraints23.gridy = 2;
			gridBagConstraints19.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints23.insets = new Insets(0, 0, 0, 5);
			ancestorsPanel.add(getFormatAncestorsComboBox(), gridBagConstraints23);

		}
		return ancestorsPanel;
	}


	/**
	 * This method initializes fgcolorSelectionButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getFgcolorSelectionButton() {
		if (fgcolorSelectionButton == null) {
			fgcolorSelectionButton = new JButton();
			Color newcolor = new Color(Integer.parseInt(visualProperties.getProperty("fgcolor_selection")));
			fgcolorSelectionButton.setBackground(newcolor);
			fgcolorSelectionButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Color newcolor = getColorFromChooser();
					fgcolorSelectionButton.setBackground(newcolor);
					visualProperties.setProperty("fgcolor_selection", Integer.toString(newcolor.getRGB()));
					setFocusToTextInput();
				}
			});
		}
		return fgcolorSelectionButton;
	}

	/**
	 * This method initializes bgcolorSelectionButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBgcolorSelectionButton() {
		if (bgcolorSelectionButton == null) {
			bgcolorSelectionButton = new JButton();
			Color newcolor = new Color(Integer.parseInt(visualProperties.getProperty("bgcolor_selection")));
			bgcolorSelectionButton.setBackground(newcolor);
			bgcolorSelectionButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Color newcolor = getColorFromChooser();
					bgcolorSelectionButton.setBackground(newcolor);
					visualProperties.setProperty("bgcolor_selection", Integer.toString(newcolor.getRGB()));
					setFocusToTextInput();
				}
			});
		}
		return bgcolorSelectionButton;
	}

	/**
	 * This method initializes formatAncestorsComboBox.	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getFormatAncestorsComboBox() {
		if (formatAncestorsComboBox == null) {
			String[] values = {Resources.getString("element_plain"),Resources.getString("element_bold"),Resources.getString("element_italic")};
			formatAncestorsComboBox = new JComboBox(values);
			formatAncestorsComboBox.setSelectedIndex(Integer.parseInt(visualProperties.getProperty("format_ancestors")));
			formatAncestorsComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String format = String.valueOf(formatAncestorsComboBox.getSelectedIndex());
					visualProperties.setProperty("format_ancestors", format);
					setFocusToTextInput();
				}
			});
		}
		return formatAncestorsComboBox;

	}




}  //  @jve:decl-index=0:visual-constraint="10,10"
