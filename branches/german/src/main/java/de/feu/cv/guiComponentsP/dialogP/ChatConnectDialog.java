package de.feu.cv.guiComponentsP.dialogP;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.feu.cv.applicationLogicP.ChatAdministration;
import de.feu.cv.applicationLogicP.Resources;
import de.feu.cv.transportP.ChatConnection;

import javax.swing.WindowConstants;
import javax.swing.JTextArea;
import java.awt.Insets;


/**
 * The dialog to read the room paramenters (roomname and nickname).
 * @author Verena Kunz
 *
 */
public class ChatConnectDialog extends JDialog {


	private static final long serialVersionUID = 5801789399694193498L;

	private JPanel jContentPane = null;

	private JLabel roomnameLabel = null;

	private JTextField roomnameField = null;

	private JLabel nicknameLabel = null;

	private JTextField nicknameField = null;

	private JButton joinButton = null;

	private JLabel existingroomsLabel = null;
	
	private DefaultListModel serverroomlistmodel;

	private JScrollPane jScrollPane = null;

	private JList serverroomList = null;

	private JButton cancelButton = null;

	private JTextArea messageTextArea = null;
	
	private String messagestring = null;

	/**
	 * Creates a modal dialog to read roomname and nickname.
	 * @param owner	the owner frame 
	 * @param message 
	 */
	public ChatConnectDialog(Frame owner, String message) {
		super(owner,true);
		messagestring = message;
		initialize();
	}

	/**
	 * This method initializes this.
	 */
	private void initialize() {
		this.setSize(330, 220);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle(Resources.getString("ti_gcconnectdialog"));
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane	.
	 * @return  javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.fill = GridBagConstraints.BOTH;
			gridBagConstraints14.gridy = 0;
			gridBagConstraints14.weightx = 1.0;
			gridBagConstraints14.weighty = 1.0;
			gridBagConstraints14.gridwidth = 3;
			gridBagConstraints14.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints14.gridx = 0;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 2;
			gridBagConstraints13.fill = GridBagConstraints.BOTH;
			gridBagConstraints13.gridy = 5;
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.fill = GridBagConstraints.BOTH;
			gridBagConstraints22.gridy = 2;
			gridBagConstraints22.weightx = 1.0;
			gridBagConstraints22.weighty = 1.0;
			gridBagConstraints22.gridwidth = 2;
			gridBagConstraints22.gridx = 1;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints21.gridy = 2;
			existingroomsLabel = new JLabel();
			existingroomsLabel.setText(Resources.getString("lab_existing_rooms"));
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.BOTH;
			gridBagConstraints12.gridy = 1;
			gridBagConstraints12.weightx = 1.0;
			gridBagConstraints12.weighty = 1.0;
			gridBagConstraints12.gridx = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridy = 5;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridy = 3;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.gridwidth = 2;
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints2.gridy = 3;
			nicknameLabel = new JLabel();
			nicknameLabel.setPreferredSize(new Dimension(68, 16));
			nicknameLabel.setHorizontalTextPosition(SwingConstants.LEFT);
			nicknameLabel.setText(Resources.getString("lab_nickname"));
			nicknameLabel.setHorizontalAlignment(SwingConstants.LEFT);
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridwidth = 2;
			gridBagConstraints1.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints.gridy = 1;
			roomnameLabel = new JLabel();
			roomnameLabel.setHorizontalAlignment(SwingConstants.LEFT);
			roomnameLabel.setText(Resources.getString("lab_chatroom"));
			roomnameLabel.setHorizontalTextPosition(SwingConstants.LEFT);
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getMessageTextArea(), gridBagConstraints14);
			jContentPane.add(roomnameLabel, gridBagConstraints);
			jContentPane.add(getRoomnameField(), gridBagConstraints1);
			jContentPane.add(nicknameLabel, gridBagConstraints2);
			jContentPane.add(getNicknameField(), gridBagConstraints3);
			jContentPane.add(getJoinButton(), gridBagConstraints11);
			jContentPane.add(existingroomsLabel, gridBagConstraints21);
			jContentPane.add(getJScrollPane(), gridBagConstraints22);
			jContentPane.add(getCancelButton(), gridBagConstraints13);
			
		}
		return jContentPane;
	}

	/**
	 * This method initializes roomnameField.	
	 * @return  javax.swing.JTextField
	 */
	private JTextField getRoomnameField() {
		if (roomnameField == null) {
			roomnameField = new JTextField();
			roomnameField.setPreferredSize(new Dimension(100, 20));

		}
		return roomnameField;
	}

	/**
	 * This method initializes nicknameField.	
	 * @return  javax.swing.JTextField
	 */
	private JTextField getNicknameField() {
		if (nicknameField == null) {
			nicknameField = new JTextField();
			nicknameField.setPreferredSize(new Dimension(100, 20));
			nicknameField.setHorizontalAlignment(SwingConstants.LEADING);
		}
		return nicknameField;
	}

	/**
	 * This method initializes joinButton.	
	 * @return  javax.swing.JButton
	 */
	private JButton getJoinButton() {
		if (joinButton == null) {
			joinButton = new JButton();
			joinButton.setHorizontalAlignment(SwingConstants.RIGHT);
			joinButton.setText(Resources.getString("bu_join"));
			joinButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//roomname = roomnameField.getText();
					//nickname = nicknameField.getText();
					setVisible(false);
					dispose();
					ChatAdministration.getInstance().createChat(roomnameField.getText(),nicknameField.getText());


				}
				
			});
		}
		return joinButton;
	}



	/**
	 * This method initializes jScrollPane.	
	 * @return  javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			//jScrollPane.setViewportView(getServerroomList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes serverroomList	
	 * @return  javax.swing.JList
	 */
	private JList getServerroomList() {
		if (serverroomList == null) {
			serverroomlistmodel = new DefaultListModel();
			Iterator roomiterator;
			try {
				roomiterator = ChatConnection.getInstance().getRoomList();
				while (roomiterator.hasNext()){
					serverroomlistmodel.addElement(roomiterator.next());
				}
			} catch (Exception e1) {
				setMessageText(e1.getMessage());
				e1.printStackTrace();
			}
		
			
			serverroomList = new JList(serverroomlistmodel);
			serverroomList
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
						public void valueChanged(javax.swing.event.ListSelectionEvent e) {
							String roomname = (String)serverroomList.getSelectedValue();
							roomnameField.setText(roomname);
						}
					});
		}
		return serverroomList;
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

	/**
	 * This method initializes messageTextArea.	
	 * 	
	 * @return javax.swing.JTextArea	
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
			messageTextArea.setText(messagestring);
		}
		return messageTextArea;
	}

	/**
	 * Sets the text in the message area (info or error messages).
	 * @param text the text to display
	 */
	public void setMessageText(String text){
		messageTextArea.setText(text);
		
	}

} 
