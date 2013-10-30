package de.feu.cv.guiComponentsP.dialogP;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import de.feu.cv.applicationLogicP.ChatAdministration;
import de.feu.cv.applicationLogicP.Resources;
import de.feu.cv.transportP.ConnectionConfiguration;


/**
 * The dialog to read the connection parameters.
 * 
 * @author Verena Kunz
 *
 */
public class ConnectionConfigurationDialog extends JDialog {

	private static final long serialVersionUID = 1L;


	/**
	 * Listed servers.
	 */
	private String[] servers = {"127.0.0.1", "jabber.ccc.de", "jabber.com"};
	/**
	 * New account flag.
	 */
	private boolean newAccount;
	
	private JPanel jContentPane = null;

	private JLabel userLabel = null;

	private JTextField loginField = null;

	private JLabel serverLabel = null;

	private JLabel passwordLabel = null;

	private JButton okButton = null;

	private JPasswordField passwordField = null;

	private JTextArea messageTextArea = null;

	private JLabel newAccountLabel = null;

	private JCheckBox newAccountCheckBox = null;

	private JComboBox serverComboBox = null;

	private JButton cancelButton = null;  

	/**
	 * Creates an new modal dialog to read the connection parameters.
	 * @param owner the owner frame
	 */
	public ConnectionConfigurationDialog(Frame owner)  {
		super(owner,true);
		initialize();
	}

	/**
	 * This method initializes this.
	 */
	private void initialize() {
		this.setSize(330, 220);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle(Resources.getString("ti_ccdialog"));
		this.setContentPane(getJContentPane());
		displayActiveConfiguration();

	}

	/**
	 * Gets the actual configuration from ChatAdministration and
	 * displays it in the dialog.
	 */
	private void displayActiveConfiguration() {
		ChatAdministration ca = ChatAdministration.getInstance();
		ConnectionConfiguration connectionconfig = ca.getConnectionConfiguration();
		String servername = "";
		
		if (connectionconfig != null){
			servername = connectionconfig.getServer();
			// put the actual servername at the first position
			if (servername !=null) 
				serverComboBox.addItem(servername);
			String username = connectionconfig.getUser();
			loginField.setText(username);
			String password = connectionconfig.getPasswd();
			passwordField.setText(password);
		}
		// append servernames from array
		for (int i=0; i<servers.length;i++){
			if (!servers[i].equals(servername))
				serverComboBox.addItem(servers[i]);
		}	
	}

	/**
	 * This method initializes jContentPane.
	 * @return  javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 2;
			gridBagConstraints13.fill = GridBagConstraints.BOTH;
			gridBagConstraints13.gridy = 7;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = GridBagConstraints.BOTH;
			gridBagConstraints21.gridy = 1;
			gridBagConstraints21.weightx = 1.0;
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.gridx = 1;
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.gridx = 1;
			gridBagConstraints51.anchor = GridBagConstraints.WEST;
			gridBagConstraints51.gridy = 4;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints4.gridy = 4;
			newAccountLabel = new JLabel();
			newAccountLabel.setText(Resources.getString("lab_create_new_account"));
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.fill = GridBagConstraints.BOTH;
			gridBagConstraints31.gridy = 0;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.weighty = 1.0;
			gridBagConstraints31.gridwidth = 3;
			gridBagConstraints31.gridheight = 1;
			gridBagConstraints31.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints31.ipady = 0;
			gridBagConstraints31.gridx = 0;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.BOTH;
			gridBagConstraints12.gridy = 3;
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
			gridBagConstraints.gridy = 3;
			passwordLabel = new JLabel();
			passwordLabel.setText(Resources.getString("lab_password"));
			passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
			passwordLabel.setHorizontalTextPosition(SwingConstants.LEFT);
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints11.gridy = 1;
			serverLabel = new JLabel();
			serverLabel.setText(Resources.getString("lab_server"));
			serverLabel.setHorizontalAlignment(SwingConstants.LEFT);
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridy = 2;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.gridwidth = 2;
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints1.gridy = 2;
			userLabel = new JLabel();
			userLabel.setText(Resources.getString("lab_user"));
			userLabel.setHorizontalTextPosition(SwingConstants.LEFT);
			userLabel.setHorizontalAlignment(SwingConstants.LEFT);
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(userLabel, gridBagConstraints1);
			jContentPane.add(getLoginField(), gridBagConstraints3);
			jContentPane.add(serverLabel, gridBagConstraints11);
			jContentPane.add(passwordLabel, gridBagConstraints);
			jContentPane.add(getOkButton(), gridBagConstraints5);
			jContentPane.add(getPasswordField(), gridBagConstraints12);
			jContentPane.add(getMessageTextArea(), gridBagConstraints31);
			jContentPane.add(newAccountLabel, gridBagConstraints4);
			jContentPane.add(getNewAccountCheckBox(), gridBagConstraints51);
			jContentPane.add(getCancelButton(), gridBagConstraints13);
			jContentPane.add(getServerComboBox(), gridBagConstraints21);
		}
		return jContentPane;
	}

	/**
	 * This method initializes loginField.	
	 * @return  javax.swing.JTextField
	 */
	private JTextField getLoginField() {
		if (loginField == null) {
			loginField = new JTextField();
			//loginField.setText("verena");
		}
		return loginField;
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
					ConnectionConfiguration cc = new ConnectionConfiguration();
					cc.setServer((String) serverComboBox.getSelectedItem());
					cc.setUser(loginField.getText());
					cc.setPasswd(new String(passwordField.getPassword()));
					newAccount = newAccountCheckBox.isSelected();
					ChatAdministration.getInstance().setConnectionConfiguration(cc, newAccount);
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
			//passwordField.setText("verena,,");
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
			// besser nur die Fehlermeldungen rot
			messageTextArea.setForeground(Color.red);
			messageTextArea.setWrapStyleWord(true);
			messageTextArea.setOpaque(false);
			messageTextArea.setEditable(false);
		}
		return messageTextArea;
	}

	/**
	 * This method initializes newAccountCheckBox.	
	 * @return  javax.swing.JCheckBox
	 */
	private JCheckBox getNewAccountCheckBox() {
		if (newAccountCheckBox == null) {
			newAccountCheckBox = new JCheckBox();
		}
		return newAccountCheckBox;
	}

	/**
	 * Returns if the account should be created.
	 * @return  <code>true</code> if the account should be created, <code>false</code> otherwise
	 */
	public boolean isNewAccount() {
		return newAccount;
	}

	/**
	 * This method initializes serverComboBox.	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getServerComboBox() {
		if (serverComboBox == null) {
			serverComboBox = new JComboBox();
			serverComboBox.setEditable(true);
		}
		return serverComboBox;
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
