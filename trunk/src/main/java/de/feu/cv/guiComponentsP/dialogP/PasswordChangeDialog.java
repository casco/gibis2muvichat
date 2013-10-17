package de.feu.cv.guiComponentsP.dialogP;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import de.feu.cv.applicationLogicP.ChatAdministration;
import de.feu.cv.applicationLogicP.Resources;


/**
 * The dialog to read the connection parameters.
 * 
 * @author Verena Kunz
 *
 */
public class PasswordChangeDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JLabel passwordLabel = null;

	private JButton okButton = null;

	private JPasswordField passwordField = null;
	
	private JTextArea messageTextArea = null;

	private JButton cancelButton = null;


	/**
	 * Creates an new modal dialog to read the new password.
	 * @param owner the owner frame
	 */
	public PasswordChangeDialog(Frame owner)  {
		super(owner,true);
		initialize();
	}

	/**
	 * This method initializes this.
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle(Resources.getString("ti_password_change"));
		this.setContentPane(getJContentPane());

	}



	/**
	 * This method initializes jContentPane.
	 * @return  javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.weightx = 1.0;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 2;
			gridBagConstraints13.fill = GridBagConstraints.BOTH;
			gridBagConstraints13.gridy = 7;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.fill = GridBagConstraints.BOTH;
			gridBagConstraints31.gridy = 0;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.weighty = 1.0;
			gridBagConstraints31.gridwidth = 3;
			gridBagConstraints31.gridheight = 1;
			gridBagConstraints31.gridx = 0;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.BOTH;
			gridBagConstraints12.gridy = 2;
			gridBagConstraints12.weightx = 1.0;
			gridBagConstraints12.gridwidth = 2;
			gridBagConstraints12.gridx = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.anchor = GridBagConstraints.SOUTHEAST;
			gridBagConstraints5.gridy = 7;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.ipadx = 0;
			gridBagConstraints.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints.gridy = 2;
			passwordLabel = new JLabel();
			passwordLabel.setText(Resources.getString("lab_newpassword"));
			passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
			passwordLabel.setHorizontalTextPosition(SwingConstants.LEFT);
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(passwordLabel, gridBagConstraints);
			jContentPane.add(getOkButton(), gridBagConstraints5);
			jContentPane.add(getPasswordField(), gridBagConstraints12);
			jContentPane.add(getMessageTextArea(), gridBagConstraints31);
			jContentPane.add(getCancelButton(), gridBagConstraints13);
		}
		return jContentPane;
	}

	/**
	 * This method initializes okButton.	
	 * @return  javax.swing.JButton
	 */
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText(Resources.getString("bu_ok"));
			okButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					ChatAdministration.getInstance().changePasswort(new String(passwordField.getPassword()));
					setVisible(false);
					dispose();
				}
			});

		}
		return okButton;
	}

	/**
	 * This method initializes passwordField.	
	 * @return  javax.swing.JPasswordField
	 */
	private JPasswordField getPasswordField() {
		if (passwordField == null) {
			passwordField = new JPasswordField();
		}
		return passwordField;
	}

	
	/**
	 * Sets the text in the message area (info or error messages).
	 * @param text the text to display
	 */
	public void setMessageText(String text){
		messageTextArea.setText(text);
		
	}

	/**
	 * This method initializes messageTextArea.	
	 * @return  javax.swing.JTextArea
	 */
	private JTextArea getMessageTextArea() {
		if (messageTextArea == null) {
			messageTextArea = new JTextArea();
			messageTextArea.setLineWrap(true);

			messageTextArea.setForeground(Color.red);
			messageTextArea.setWrapStyleWord(true);
			messageTextArea.setOpaque(false);
			messageTextArea.setEditable(false);
		}
		return messageTextArea;
	}


	/**
	 * This method initializes cancelButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText(Resources.getString("bu_cancel"));
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
					dispose();
				}
			});
		}
		return cancelButton;
	}


}
