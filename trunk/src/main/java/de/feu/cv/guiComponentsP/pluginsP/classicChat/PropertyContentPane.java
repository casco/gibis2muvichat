package de.feu.cv.guiComponentsP.pluginsP.classicChat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.feu.cv.applicationLogicP.Resources;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.PropertyContentPanel;

/**
 * The panel to configure the properties of classicChat.
 * @author Verena Kunz
 *
 */
public class PropertyContentPane extends PropertyContentPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The visual properties of the dialog.
	 */
	private VisualizationProperties visualProperties;
	
	private JLabel fgcolorLabel = null;
	private JLabel bgcolorLabel = null;
	private JLabel fontsizeLabel = null;
	private JButton fgcolorButton = null;
	private JButton bgcolorButton = null;
	private JComboBox fontsizeComboBox = null;
	private JPanel layouthelppanel;

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
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.fill = GridBagConstraints.NONE;
		gridBagConstraints3.insets = new Insets(0, 0, 0, 5);
		gridBagConstraints3.anchor = GridBagConstraints.WEST;
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.anchor = GridBagConstraints.WEST;
		gridBagConstraints21.ipady = 0;
		gridBagConstraints21.insets = new Insets(2, 5, 2, 5);
		gridBagConstraints21.ipadx = 0;
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 0;
		gridBagConstraints12.weighty = 1.0D;
		gridBagConstraints12.fill = GridBagConstraints.VERTICAL;
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints11.gridy = 2;
		gridBagConstraints11.weightx = 1.0;
		gridBagConstraints11.anchor = GridBagConstraints.WEST;
		gridBagConstraints11.insets = new Insets(0, 0, 0, 5);
		gridBagConstraints11.gridx = 1;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 1;
		gridBagConstraints2.fill = GridBagConstraints.NONE;
		gridBagConstraints2.anchor = GridBagConstraints.WEST;
		gridBagConstraints2.insets = new Insets(0, 0, 0, 5);
		gridBagConstraints2.gridy = 1;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.anchor = GridBagConstraints.WEST;
		gridBagConstraints1.insets = new Insets(2, 5, 2, 5);
		gridBagConstraints1.gridy = 2;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.ipadx = 0;
		gridBagConstraints.insets = new Insets(2, 5, 2, 5);
		gridBagConstraints.gridy = 1;
		fontsizeLabel = new JLabel();
		fontsizeLabel.setText(Resources.getString("lab_fontsize"));
		bgcolorLabel = new JLabel();
		bgcolorLabel.setText(Resources.getString("lab_bgcolor"));
		fgcolorLabel = new JLabel();
		fgcolorLabel.setText(Resources.getString("lab_fontcolor"));
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.add(fgcolorLabel, gridBagConstraints21);
		this.add(bgcolorLabel, gridBagConstraints);
		this.add(fontsizeLabel, gridBagConstraints1);
		this.add(getFgcolorButton(), gridBagConstraints3);
		this.add(getBgcolorButton(), gridBagConstraints2);
		this.add(getFontsizeComboBox(), gridBagConstraints11);
		this.add(getLayoutHelpPanel(), gridBagConstraints12);
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
			fgcolorButton.setPreferredSize(new Dimension(30,12));
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
			bgcolorButton.setPreferredSize(new Dimension(30,12));
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
	 * Reset the properties in property panel and in property model.
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.PropertyContentPanel#resetProperties()
	 */
	public void resetProperties() {
		visualProperties.setDefaultValues();
		fontsizeComboBox.setSelectedItem(visualProperties.getProperty("size"));
		Color newcolor = new Color(Integer.parseInt(visualProperties.getProperty("fgcolor")));
		fgcolorButton.setBackground(newcolor);
		newcolor = new Color(Integer.parseInt(visualProperties.getProperty("bgcolor")));
		bgcolorButton.setBackground(newcolor);
		
	}





}
