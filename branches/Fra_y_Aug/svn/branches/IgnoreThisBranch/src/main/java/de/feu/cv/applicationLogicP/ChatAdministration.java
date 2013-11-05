package de.feu.cv.applicationLogicP;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import prefuse.data.io.DataIOException;

import de.feu.cv.applicationLogicP.chatFileP.ChatFile;
import de.feu.cv.applicationLogicP.chatFileP.ChatFileTableModel;
import de.feu.cv.applicationLogicP.chatRoomP.ChatRoom;
import de.feu.cv.applicationLogicP.chatRoomP.ChatRoomTableModel;
import de.feu.cv.guiComponentsP.MainWindow;
import de.feu.cv.transportP.ChatConnection;
import de.feu.cv.transportP.ConnectionConfiguration;


/**
 * The class starts the application and coordinates the communication.
 * Uses the singleton pattern.
 * @author Verena Kunz
 */

public class ChatAdministration {

	/**
	 * The single Instance of ChatAdministration.
	 */
	private static ChatAdministration singleInstance = new ChatAdministration();
	/**
	 * The connection to the chat server.
	 */
	private ChatConnection chatConnection;
	
	/**
	 * The configuration data used for the connection to chat server.
	 */
	private ConnectionConfiguration connectionconfig;
	
	/**
	 * The filename to safe the configuration data for the connection to chat server.
	 */
	private String filename = "cconf.txt";
	
	/**
	 * The main window of the GUI.
	 */
	private static MainWindow mainWindow;
	
	/**
	 * The list of connected chatrooms.
	 */
	private ChatRoomTableModel roomlist;
	
	/**
	 * The list of displayed files.
	 */
	private ChatFileTableModel filelist;
	
	/**
	 * Status of configuration. 
	 */
	private boolean config_ok = false;
	
	/**
	 * The private constructor for the application, which also 
	 * prepares the connection to the chat server.
	 **/
	private ChatAdministration(){
		roomlist = new ChatRoomTableModel();
		filelist = new ChatFileTableModel();
		chatConnection = ChatConnection.getInstance();
		connectionconfig = loadConnectionConfiguration();
	}
	


	/**
	 * Returns the single instance of the class ChatAdministration.
	 * @return the single instance of ChatAdministration
	 */
	public static synchronized ChatAdministration getInstance(){
		return singleInstance;
	}
	
	/**
	 * This method starts the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		ChatAdministration ca =ChatAdministration.getInstance();

		mainWindow = MainWindow.getInstance();
		mainWindow.setVisible(true);
		if (args.length == 3){
			ConnectionConfiguration args_config = new ConnectionConfiguration();
		    args_config.setServer(args[0]);
		    args_config.setUser(args[1]);
		    args_config.setPasswd(args[2]);
		    ca.setConnectionConfiguration(args_config, false);
		}
	}

	/**
	 * Creates a dialog to read the connection configuration data.
	 * The dialog window can also display a text (error) message.
	 * @param message the message that ist shown in the dialog window
	 */
	private void createConnectionConfigurationDialog(String message) {
		mainWindow.readConnectionConfigurationFromDialog(message);
	}
	
	/**
	 * Creates a dialog to read the connection configuration data.
	 * The dialog window can also display a text (error) message.
	 * @param message the message that ist shown in the dialog window
	 */
	private void createChatConnectDialog(String message) {
		mainWindow.readChatRoomDataFromDialog(message);
	}
	
	/**
	 * Creates a new connection to a chatroom. If a connection to the room 
	 * already exists, only the nickname ist changed. If roomname and nickname 
	 * are empty strings, then the roomname ist set to "default" and the 
	 * nickname is set to the userid of the connetion to the server.
	 * The method also updates the list of connected rooms in the main window. 
	 * @param roomname the name of the chatroom
	 * @param nickname the nickname used in the chatroom
	 */
	public void createChat(String roomname, String nickname) {
		
		boolean successful = false;
		String errormessage = "";
		
		// set default value for roomname
		if (roomname.equals("")){
			roomname = "default";
		}
		
		// set default value for nickname
		if (nickname.equals("")){
			nickname = chatConnection.getConnectedUserID();
		}
		// if connection to room already exists, only change the nickname
		if (roomlist.containsKey(roomname)){ // room already exists
			ChatRoom room = roomlist.get(roomname);
			if (!room.getNickname().equals(nickname)){
				try {
					room.changeNickname(nickname);
					successful = true;
					roomlist.fireTableDataChanged();
				} catch (Exception e) {
					errormessage = e.getMessage();
				}
			}		
		}
		else { // make new connection
			ChatRoom groupchat;
			try {
				groupchat = new ChatRoom(roomname, nickname);
				roomlist.put(roomname, groupchat);
				roomlist.fireTableDataChanged();
				successful = true;
			} catch (Exception e) {
				errormessage = e.getMessage();
				e.printStackTrace();
			}

		}
		if (!successful){
			createChatConnectDialog(errormessage);
		}
	}
	
	/**
	 * Closes connection to server and the mainwindow and
	 * saves the current connection configuration.
	 */
	public void exitProgram() {
		goOffline();
		mainWindow.dispose();
		if (config_ok)
			saveConnectionConfiguration();
		System.exit(0);
		
	}
	
	/**
	 * Returns the current connection to the chat server
	 * @return the connection to the chat server.
	 */
	public ChatConnection getChatConnection() {
		return chatConnection;
	}

	/**
	 * Returns the current connection configuration.
	 * @return the current connection configuration
	 */
	public ConnectionConfiguration getConnectionConfiguration() {
		return connectionconfig;
	}
	
	/**
	 * Returns the main window.
	 * @return the main window
	 */
	public MainWindow getMainWindow() {
		return mainWindow;
	}
	
	/**
	 * Returns the list of the connected rooms.
	 * @return the list of connected rooms
	 */
	public ChatRoomTableModel getRoomlist() {
		return roomlist;
	}
	
	/**
	 * Returns the list of the open files.
	 * @return the list of open files
	 */
	public ChatFileTableModel getFilelist() {
		return filelist;
	}
	
	/**
	 * Closes the connection to the chat server. 
	 * Display the offline symbol in main window.
	 */
	public void goOffline()
	{
		if (isOnline()){
			chatConnection.closeConnection();
		}
		getMainWindow().displayOfflineSymbol();
		closeAllRoomWindows();

	}
	
	/**
	 * Shows a message, closes all room windows.
	 * Displays offline symbol in main window.
	 */
	public void goneOffline()
	{
		getMainWindow().displayOfflineSymbol();
		mainWindow.displayMessage(Resources.getString("msg_disconnected"));
		closeAllRoomWindows();
	}
	
	/**
	 * Opens the connection to the chat server with the current
	 * connection configuration. Display the online symbol in main window
	 * If the connection fails it displays a dialog to read new configuration data.
	 */
	public void goOnline()
	{		
		try {
			chatConnection.openConnection(connectionconfig);
			getMainWindow().displayOnlineSymbol();
			config_ok = true;
		} catch (Exception e) {
			config_ok = false;
			createConnectionConfigurationDialog(e.getMessage());
			
		}
	}


	/**
	 * Returns true if a connection to the chat server is established.
	 * @return <code>true</code> if the connection is established;
	 * 		   <code>false</code> otherwise.
	 */
	public boolean isOnline()
	{		
		return chatConnection.isConnected();
	}

	/**
	 * Removes the room from the list of connected rooms.
	 * @param roomname the name of the room to remove from list
	 */
	public void removeRoomFromList(String roomname){
		roomlist.remove(roomname);			
	}
	
	/**
	 * Removes the file from the list of open files.
	 * @param file the ChatFile to remove from list
	 */
	public void removeFileFromList(ChatFile file){
		filelist.remove(file);			
	}

	/**
	 * Loads the connection configuration from file.
	 * @return the saved connection configuration;
	 * 		   <code>null</code> if no configuration is saved
	 */
	public ConnectionConfiguration loadConnectionConfiguration(){
		ConnectionConfiguration savedconfig = null;
		try {
		    FileInputStream propInFile = new FileInputStream( getConnConfigFile());
		    Properties p = new Properties();
		    p.load( propInFile );

		    String server = p.getProperty("server");
		    String user = p.getProperty("user");
		    String passwd = p.getProperty("passwd");
		    
		    if (server != null & user != null & passwd != null){
			    savedconfig = new ConnectionConfiguration();
			    savedconfig.setServer(server);
			    savedconfig.setUser(user);
			    savedconfig.setPasswd(passwd);
		    }
		    
		    }
		    catch ( FileNotFoundException e ) {
		       //createConnectionConfigurationDialog(Resources.getString("msg_no_config_file"));
		    }
		    catch ( IOException e ) {
		       //createConnectionConfigurationDialog(Resources.getString("msg_config_file_not_readable"));
	     }
		 return savedconfig;    
	}

	/**
	 * Saves the current connection configuration to file.
	 */
	public void saveConnectionConfiguration(){
		Properties props = new Properties();
		if (connectionconfig!=null){
			props.setProperty("server", connectionconfig.getServer());
			props.setProperty("user", connectionconfig.getUser());
			props.setProperty("passwd", connectionconfig.getPasswd());
		}
		try {
			FileOutputStream propOutFile = new FileOutputStream(getConnConfigFile());
			try {
				props.store(propOutFile, "Jabber connection parameters");
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (FileNotFoundException e2) {

			e2.printStackTrace();
		}
	}
	
	
	/**
	 * Sets the current connecton configuration data and reconnect
	 * to chatserver.
	 * If the connection fails it displays a dialog to read new configuration data.
	 * @param newcc the new connection configuration data
	 * @param newAccount <code>true</code> if the account is new,
	 *                   <code>false</code> if the account already exists
	 */
	public void setConnectionConfiguration(ConnectionConfiguration newcc, boolean newAccount){
		connectionconfig = newcc;
		config_ok = true;
		String errormessage = "";
		if (newAccount){
			try {
				chatConnection.createAccount(newcc);
			} catch (Exception e) {
				config_ok = false;
				errormessage = e.getMessage();
			}
		}
		if (config_ok){
			
			goOffline();
			goOnline();
		}
		else
			createConnectionConfigurationDialog(errormessage);
		mainWindow.updateTitle();
	}
	
	
	/**
	 * Changes the password of the account which is actually logged in.
	 * @param newpassword the new password
	 */
	public void changePasswort(String newpassword) {

		try {
			chatConnection.changePassword(newpassword);
			connectionconfig.setPasswd(newpassword);
			
		} catch (Exception e) {

			//System.out.println(e.getMessage());
			e.printStackTrace();
		}	
	}
	
	/**
	 * Deletes the account.
	 */
	public void deleteAccount() {
		try {
			chatConnection.deleteAccount();
			goOffline();
			connectionconfig = null;
			mainWindow.updateTitle();
			
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}
//	/**
//	 * Create a new ChatFile from saved chat and displays it
//	 * @param filename	the filename of the saved chat
//	 * @param replay <code>true</code> to see the progress of conversation
//	 * <code> false </code> to see all messages at once
//	 */
//	public void viewSavedChat(File filename, boolean replay){
//		
//		
//		
//		if (!filelist.containsFile(filename)){
//		
//			ChatFile cf;
//			try {
//				cf = new ChatFile(filename, replay);
//				filelist.put(cf);
//				filelist.fireTableDataChanged();
//			} catch (DataIOException e) {
//				e.printStackTrace();
//				MainWindow.getInstance().displayMessage(Resources.getString("msg_dataIOException"));
//			}
//		}
//		else{
//			filelist.get(filename.getAbsolutePath()).setActiveChat();
//		}
//

//	}
	
	/**
	 * Create a new ChatFile from saved chat and displays it.
	 * If the file contains no data in TreeML format a message is displayed.
	 * @param filename	the filename of the saved chat
	 */
	public void viewSavedChat(File filename){		
		if (!filelist.containsFile(filename)){
		
			ChatFile cf;
			try {
				cf = new ChatFile(filename);
				filelist.put(cf);
				filelist.fireTableDataChanged();
			} catch (DataIOException e) {
				MainWindow.getInstance().displayMessage(Resources.getString("msg_dataIOException"));
			}
		}
		else{
			filelist.get(filename.getAbsolutePath()).setActiveChat();
		}


	}
	
	/**
	 * Closes all chat room windows.
	 */
	public void closeAllRoomWindows(){
		Iterator it = roomlist.getAllOpenChatRooms();
		while (it.hasNext()){
			String roomname = (String) it.next();
			roomlist.get(roomname).closeWindow();
			roomlist.remove(roomname);
			
		}
	
	}

	/**
	 * Returns the home dir of MuVi-Chat. If it doesn't exists it will be created.
	 * @return the HomeDir
	 */
	public File getHomeDir() {
		File f = new File(System.getProperty("user.home"),MuViChatConstants.muviHomeDir);		
        if (!f.isDirectory()) {
	         f.mkdir();	    
	    }
        return f;
	}
	
	
	/**
	 * Returns the file where the connection configuration is saved.
	 * @return the file where the connection configuration is saved
	 */
	private File getConnConfigFile(){
		File f = new File(getHomeDir(),filename);
		return f;
	}



}
