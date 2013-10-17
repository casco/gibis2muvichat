package de.feu.cv.guiComponentsP;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import de.feu.cv.applicationLogicP.ChatAdministration;
import de.feu.cv.applicationLogicP.MuViChatConstants;
import de.feu.cv.applicationLogicP.Resources;
import de.feu.cv.applicationLogicP.chatFileP.ChatFileTableModel;
import de.feu.cv.applicationLogicP.chatRoomP.ChatRoomTableModel;
import de.feu.cv.guiComponentsP.dialogP.ChatConnectDialog;
import de.feu.cv.guiComponentsP.dialogP.ConnectionConfigurationDialog;
import de.feu.cv.guiComponentsP.dialogP.PasswordChangeDialog;

/**
 * The main window of the chat application
 * @author Verena Kunz
 *
 */
public class MainWindow extends JFrame{ 


	private static final long serialVersionUID = 1L;
	/**
	 * The single instance of main window.
	 */
	private static MainWindow singleInstance;
	/**
	 * The single ChatAdministration instance.
	 */
	private ChatAdministration chatAdministration;
	private JPanel jPanel = null;
	private JLabel connectedRoomsLabel = null;
	private JScrollPane connectedRoomsScrollPane = null;
	private JTable connectedRoomsTable = null;
	private JLabel openFilesLabel = null;
	private JScrollPane openFilesScrollPane = null;
	private JTable openFilesTable = null;
	
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu helpMenu = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem aboutMenuItem = null;
	private JMenuItem groupchatMenuItem = null;
	private JMenuItem fileloadMenuItem = null;
	private JMenuItem accountMenuItem = null;
	private JButton onoffButton = null;
	private JLabel jLabel = null;
	private JMenuItem changePasswordMenuItem = null;
	private JMenuItem deleteAccountMenuItem = null;



	/**
	 * Creates the main application window.
	 * @param ca
	 */
	private MainWindow(ChatAdministration ca) {
		super();
		chatAdministration = ca;
		initialize();
		
	}
	
	/**
	 * Returns the single instance of the class ChatAdministration.
	 * @return the single instance of ChatAdministration
	 */
	public static synchronized MainWindow getInstance(){
		if (singleInstance == null){
			ChatAdministration ca = ChatAdministration.getInstance();
			singleInstance = new MainWindow(ca);
		}
		return singleInstance;
	}
	
	/**
	 * This method initializes this.
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(406, 283));
        this.setContentPane(getJPanel());
        updateTitle();
        this.setJMenuBar(getJJMenuBar());
        this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				exitProgram();
			}
		});
			
	}
	/**
	 * Exits the application.
	 */
	private void exitProgram() {
		chatAdministration.exitProgram();
		
	}
	
	/**
	 * Displays an online symbol.
	 */
	public void displayOnlineSymbol() {
		jLabel.setText("Online");
		jLabel.setForeground(Color.green);
		onoffButton.setText(Resources.getString("go_offline"));
		
	}
	/**
	 * Displays an offline symbol.
	 */
	public void displayOfflineSymbol() {
		jLabel.setText("Offline");
		jLabel.setForeground(Color.red);
		onoffButton.setText(Resources.getString("go_online"));
	}
	/**
	 * This method initializes jPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			gridBagConstraints12.gridy = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 6;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.weighty = 1.0;
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 5;
			gridBagConstraints3.weightx = 1.0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 4;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 3;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 2;
			connectedRoomsLabel = new JLabel();
			connectedRoomsLabel.setText(Resources.getString("lab_connected_rooms"));
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getOpenFilesScrollPane(), gridBagConstraints3);
			jPanel.add(connectedRoomsLabel, gridBagConstraints);
			jPanel.add(getConnectedRoomsScrollPane(), gridBagConstraints1);
			jPanel.add(getOpenFilesLabel(), gridBagConstraints2);
			jPanel.add(getJLabel(), gridBagConstraints11);
			jPanel.add(getOnoffButton(), gridBagConstraints12);
		}
		return jPanel;
	}
	/**
	 * This method initializes connectedRoomsScrollPane.
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getConnectedRoomsScrollPane() {
		if (connectedRoomsScrollPane == null) {
			connectedRoomsScrollPane = new JScrollPane();
			connectedRoomsScrollPane.setViewportView(getConnectedRoomsTable());
		}
		return connectedRoomsScrollPane;
	}
	/**
	 * This method initializes connecteRoomsTable.	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getConnectedRoomsTable() {
		if (connectedRoomsTable == null) {
			ChatRoomTableModel tablemodel = chatAdministration.getRoomlist();
			connectedRoomsTable = new JTable(tablemodel);
			connectedRoomsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			connectedRoomsTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
			        int row = connectedRoomsTable.getSelectedRow();
			        if (row>-1) 
			        	((ChatRoomTableModel) connectedRoomsTable.getModel()).setActiveChat(row);
				}
			});			

		}
		return connectedRoomsTable;
	}
	/**
	 * This method initializes openFilesLabel.	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getOpenFilesLabel() {
		if (openFilesLabel == null) {
			openFilesLabel = new JLabel();
			openFilesLabel.setText(Resources.getString("lab_open_files"));
		}
		return openFilesLabel;
	}
	/**
	 * This method initializes openFilesScrollPane.	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getOpenFilesScrollPane() {
		if (openFilesScrollPane == null) {
			openFilesScrollPane = new JScrollPane();
			openFilesScrollPane.setViewportView(getOpenFilesTable());
		}
		return openFilesScrollPane;
	}
	/**
	 * This method initializes openFilesTable.	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getOpenFilesTable() {
		if (openFilesTable == null) {
						
			ChatFileTableModel tablemodel = chatAdministration.getFilelist();
			openFilesTable = new JTable(tablemodel);
			openFilesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			openFilesTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {					
			        int row = openFilesTable.getSelectedRow();
			        if (row>-1) 
			        	((ChatFileTableModel) openFilesTable.getModel()).setActiveChat(row);	
				}
			});
		}
		return openFilesTable;
	}
	/**
	 * This method initailizes jJMenuBar.
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu.	
	 * @return  javax.swing.JMenu
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText(Resources.getString("menu_file"));
			fileMenu.add(getGroupchatMenuItem());
			fileMenu.add(getFileloadMenuItem());
			fileMenu.addSeparator();
			fileMenu.add(getAccountMenuItem());
			fileMenu.add(getChangePasswordMenuItem());
			fileMenu.add(getDeleteAccountMenuItem());
			fileMenu.addSeparator();
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu.	
	 * @return  javax.swing.JMenu
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText(Resources.getString("menu_help"));
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenuItem.	
	 * @return  javax.swing.JMenuItem
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText(Resources.getString("menu_exit"));
			exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					exitProgram();
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes jMenuItem.	
	 * @return  javax.swing.JMenuItem
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText(Resources.getString("menu_about"));
			aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					displayAboutMessage();
				}
			});
		}
		return aboutMenuItem;
	}
	/**
	 * This method initializes groupchatMenuItem.	
	 * @return  javax.swing.JMenuItem
	 */
	private JMenuItem getGroupchatMenuItem() {
		if (groupchatMenuItem == null) {
			groupchatMenuItem = new JMenuItem();
			groupchatMenuItem.setText(Resources.getString("menu_joinroom"));
			groupchatMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					readChatRoomDataFromDialog(Resources.getString("msg_joinroom"));
				}
			});
		}
		return groupchatMenuItem;
	}

	/**
	 * This method initializes fileloadMenuItem.	
	 * @return  javax.swing.JMenuItem
	 */
	private JMenuItem getFileloadMenuItem() {
		if (fileloadMenuItem == null) {
			fileloadMenuItem = new JMenuItem();
			fileloadMenuItem.setText(Resources.getString("menu_loadfile"));
			fileloadMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					readSavedChat();
				}
			});
		}
		return fileloadMenuItem;
	}


	/**
	 * This method initializes accountMenuItem.	
	 * @return  javax.swing.JMenuItem
	 */
	private JMenuItem getAccountMenuItem() {
		if (accountMenuItem == null) {
			accountMenuItem = new JMenuItem();
			accountMenuItem.setText(Resources.getString("menu_change_account"));
			accountMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					readConnectionConfigurationFromDialog(Resources.getString("msg_new_account_dates"));
				}
			});
		}
		return accountMenuItem;
	}
	/**
	 * This method initializes onoffButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOnoffButton() {
		if (onoffButton == null) {
			onoffButton = new JButton();
			onoffButton.setText(Resources.getString("go_online"));
			onoffButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (chatAdministration.isOnline())
						chatAdministration.goOffline();
					else
						chatAdministration.goOnline();
				}
			});
		}
		return onoffButton;
	}
	/**
	 * This method initializes jLabel.	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new JLabel();
			jLabel.setText("Offline");
			jLabel.setForeground(Color.red);
		}
		return jLabel;
	}

	/**
	 * Opens a new dialog to read connection parameters. Displays the
	 * message string.
	 * @param message the message to display
	 */
	public void readConnectionConfigurationFromDialog(String message){
		ConnectionConfigurationDialog cocodialog = new ConnectionConfigurationDialog(this);
		cocodialog.setMessageText(message);
		cocodialog.setVisible(true);
	}
	
	/**
	 * Opens a new dialog to read the parameters to connect to a chat
	 * room. Tries to go online if there is no connection.
	 */
	public void readChatRoomDataFromDialog(String message){
		// first check connection
		ChatAdministration ca = ChatAdministration.getInstance();
		if (!ca.isOnline())
			ca.goOnline();
		
		// raise ChatConnectDialog only if online
		if (ca.isOnline()){
			ChatConnectDialog chacodialog = new ChatConnectDialog(this,message);
			chacodialog.setVisible(true);
		}
	}
	
	/**
	 * Opens a new dialog to read new password data.
	 * Tries to go online if there is no connection.
	 */
	public void readNewPasswordFromDialog(String message){
		// first check connection
		ChatAdministration ca = ChatAdministration.getInstance();
		if (!ca.isOnline()){
			ca.goOnline();
		}
		
		
		// raise PasswordDialog only if online
		if (ca.isOnline()){
			PasswordChangeDialog pachadialog = new PasswordChangeDialog(this);
			pachadialog.setMessageText(message);
			pachadialog.setVisible(true);
		}
	}
	
	/**
	 * Opens a dialog to delete a user account.
	 */
	public void deleteDialog(){
		// first check connection
		ChatAdministration ca = ChatAdministration.getInstance();
		if (!ca.isOnline()){
			
			displayMessage(Resources.getString("msg_connection_required_to_delete_account"));
			ca.goOnline();
		}
		
		// raise PasswordDialog only if online
		if (ca.isOnline()){
			
			int n = JOptionPane.showConfirmDialog(
				    this,
				    Resources.getString("msg_really_delete"),
				    "???",
				    JOptionPane.YES_NO_OPTION);
			if (n == 0){
				ca.deleteAccount();
			}
		}
	}
	
	/**
	 * Displays a dialog with an error message.
	 * @param message the error message
	 */
	public void displayMessage(String message){
		 JOptionPane.showMessageDialog(this, message,Resources.getString("error"), JOptionPane.ERROR_MESSAGE);
	}
	
	
	/**
	 * Displays the about dialog.
	 */
	public void displayAboutMessage(){
		 String aboutstring = "MuVi-Chat Version " +MuViChatConstants.version+ "\nContact: "+ MuViChatConstants.contact;
		 JOptionPane.showMessageDialog(this,aboutstring ,Resources.getString("info"), JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Returns the position to place a ChatWindow.
	 * @return the position point
	 */
	public Point getLocationForChatWindow(){
		
		// position of MainWindow
		Point mw_loc =  this.getLocation();
		Dimension mw_size = this.getSize();
		
		// sreensize
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		//System.out.println(screensize);
		
		//window count
		int windowcount = openFilesTable.getRowCount()+connectedRoomsTable.getRowCount();
		
		int dist = 40;
		
		int xpos = (int) (mw_loc.getX() + mw_size.getWidth()+ windowcount*dist);
		int ypos = dist*windowcount;
		
		// no position out of the screen
		xpos = (int) Math.min(xpos, screensize.getWidth()-100);
		ypos = (int) Math.min(ypos, screensize.getHeight()-100);
		
		return new Point(xpos,ypos);
	}
	/**
	 * Opens a file dialog to read a filename to view.
	 */
	protected void readSavedChat() {
		JFileChooser chooser = new JFileChooser();
		if (chooser.showOpenDialog(this) ==  JFileChooser.APPROVE_OPTION){
			chatAdministration.viewSavedChat(chooser.getSelectedFile());
		}
	}

	/**
	 * This method initializes changePasswordMenuItem.	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getChangePasswordMenuItem() {
		if (changePasswordMenuItem == null) {
			changePasswordMenuItem = new JMenuItem();
			changePasswordMenuItem.setText(Resources.getString("menu_change_password"));
			changePasswordMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					readNewPasswordFromDialog("");
				}
			});
		}
		return changePasswordMenuItem;
	}

	/**
	 * This method initializes deleteAccountMenuItem.	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getDeleteAccountMenuItem() {
		if (deleteAccountMenuItem == null) {
			deleteAccountMenuItem = new JMenuItem();
			deleteAccountMenuItem.setText(Resources.getString("menu_delete_account"));
			deleteAccountMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					deleteDialog();
				}
			});
		}
		return deleteAccountMenuItem;
	}
	
	public void updateTitle(){
		String useraccount = "";
		if (chatAdministration.getConnectionConfiguration() != null){
			
			useraccount = " - "+chatAdministration.getConnectionConfiguration().getUser();
		}
        this.setTitle(Resources.getString("ti_mainwindow")+useraccount);
	}

	
}  
