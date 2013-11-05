package de.feu.cv.guiComponentsP.dialogP;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.feu.cv.applicationLogicP.Resources;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatLiveWindow;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatWindow;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.PropertyContentPanel;

/**
 * The dialog to read the visualization parameters.
 * @author Verena Kunz
 *
 */
public class ChatWindowPropertyDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JPanel buttonPanel = null;

	private JButton closeButton = null;

	private JButton resetToDefaultButton = null;


	/**
	 * Creates a non modal dialog to visualization parameters.
	 * @param owner
	 */
	public ChatWindowPropertyDialog(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this.
	 */
	private void initialize() {
		this.setSize(new Dimension(263, 154));
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
			jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
		}
		return jPanel;
	}

	/**
	 * This method initializes buttonPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			buttonPanel.add(getCloseButton(), gridBagConstraints2);
			buttonPanel.add(getResetToDefaultButton(), gridBagConstraints);
		}
		return buttonPanel;
	}

	/**
	 * This method initializes closeButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCloseButton() {
		if (closeButton == null) {
			closeButton = new JButton();
			closeButton.setText(Resources.getString("bu_hide"));
			closeButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					closeDialog();
				}
			});
		}
		return closeButton;
	}

	protected void closeDialog() {
		this.dispose();
		
	}

	/**
	 * This method initializes resetToDefaultButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getResetToDefaultButton() {
		if (resetToDefaultButton == null) {
			resetToDefaultButton = new JButton();
			resetToDefaultButton.setText(Resources.getString("bu_resetToDefault"));
			resetToDefaultButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// no reset if there is no configuration panel
					if (jPanel.getComponent(0) instanceof PropertyContentPanel){
						PropertyContentPanel contentpanel =(PropertyContentPanel) jPanel.getComponent(0);
						contentpanel.resetProperties();
					}
					setFocusToTextInput();
					
					
				}
			});
		}
		return resetToDefaultButton;
	}
	
	/**
	 * Sets the focus to the text input field in the chat window.
	 */
	public void setFocusToTextInput() {
		
		ChatWindow window = (ChatWindow) this.getOwner();
		// text input field exists only in live chats
		if (window instanceof ChatLiveWindow)
			((ChatLiveWindow)window).setFocusToTextInput();
		
	}

	/**
	 * Changes the content of the copnfiguration panel.
	 * @param panel the new content panel
	 */
	public void setConfigPanel(JComponent panel){
		// remove old panel
		JPanel oldpanel = null;
		if (jPanel.getComponentCount()>0)
			oldpanel = (JPanel) jPanel.getComponent(0);
		if (oldpanel != null)
			jPanel.remove(oldpanel);

		// add panel or substitution text
		if (panel != null)
			jPanel.add(panel,BorderLayout.CENTER);
		else{
			JTextArea area = new JTextArea(Resources.getString("msg_no_config_available"));
			jPanel.add(area);
		}
		validate();
		pack();
	}
	

}  
