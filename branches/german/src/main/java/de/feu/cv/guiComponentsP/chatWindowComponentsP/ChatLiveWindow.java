package de.feu.cv.guiComponentsP.chatWindowComponentsP;

import javax.swing.*;

import de.feu.cv.ConversationModelP.ConversationModel;
import de.feu.cv.applicationLogicP.ChatAdministration;
import de.feu.cv.applicationLogicP.Resources;
import de.feu.cv.applicationLogicP.chatRoomP.ChatRoom;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Window to display online chats.
 * @author Verena Kunz
 *
 */
public class ChatLiveWindow extends ChatWindow {

	private static final long serialVersionUID = 1336690126798576745L;

	
	/**
	 * The chatroom displayed in the window.
	 */
	private ChatRoom chatroom;
	
	private JScrollPane participantPane = null;
	
	private JPanel textInputPane = null;

	private JMenuBar jJMenuBar = null;

	private JMenu fileMenu = null;

	private JMenuItem saveMenuItem = null;
	


	/**
	 * Creates a chat window for a chatroom.
	 * @param chatroom the chatroom displayed in the window
	 */
	public ChatLiveWindow(ChatRoom chatroom) {
		super();
		this.chatroom = chatroom;
		initialize();
		
	}

	/**
	 * This method initializes this.
	 * 
	 */
	private void initialize() {
		this.createVisualizationPanes(chatroom.getConversation());
		this.setJMenuBar(getJJMenuBar());
		getJSplitPaneRight().setTopComponent(getParticipantPane());
		jContentPane.add("South",getTextInputPane());

		
		updateTitle();
		this.setVisible(true);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				chatroom.leave();
				dispose();
			}
		});
	}

	
	/**
	 * This method initializes participantPane.
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getParticipantPane() {
		if (participantPane == null) {
			participantPane = new ParticipantsPane(chatroom.getParticipants());
			participantPane.setPreferredSize(new Dimension(100, 100));	
			participantPane.setMinimumSize(new Dimension(100, 100));	
		}
		return participantPane;
	}
	
	/**
	 * This method initializes textInputPane.	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JPanel getTextInputPane() {
		if (textInputPane == null) {
			textInputPane = new ChatTextInputPane(chatroom);
		}
		return textInputPane;
	}
	

	/**
	 * set the focus to the textinput.
	 */
	public void setFocusToTextInput(){
		((ChatTextInputPane) textInputPane).setFocusToTextInput();
	}

	/**
	 * This method initializes jJMenuBar.	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			// view menu is inherited from ChatWindow
			jJMenuBar.add(getViewMenu());
			jJMenuBar.add(getExtrasMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes fileMenu.	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText(Resources.getString("menu_file"));
			fileMenu.add(getSaveMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes saveMenuItem.	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText(Resources.getString("menu_saveas"));
			saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveConversation();

				}
			});
		}
		return saveMenuItem;
	}

	/**
	 * Opens dialog to select a file name and saves the conversation to file.
	 */
	protected void saveConversation() {
		JFileChooser chooser = new JFileChooser();
		if (chooser.showSaveDialog(this) ==  JFileChooser.APPROVE_OPTION)
			chatroom.saveToFile(chooser.getSelectedFile());
	}

	/**
	 * Resets the selection in the panel.
	 */
	public void resetSelection() {
		((ChatVisualizationPane) getJSplitPaneAll().getLeftComponent()).resetSelection();
	}
	
	public void updateTitle(){
		String useraccount = "";
		if (ChatAdministration.getInstance().getConnectionConfiguration() != null){
			
			useraccount = " - "+ChatAdministration.getInstance().getConnectionConfiguration().getUser();
		}
		
		this.setTitle(Resources.getString("ti_roomname")+ " " + chatroom.getRoomname()+ useraccount);
	}


	// actualiza el modelo de conversación en textInputPane a partir del archivo"file" 
	protected void updateConversationModel(File file) {
	    // leer contenido del archivo
	    try {   // actualizar modelo de conversación de textInputPane		         
		        String fileDataString = ConversationModel.fileToString(file);
		        //conversationModel.createConversationModelFromString(fileDataString);
		        //TODO broadcast hacia el chat, para que todos tengan el nuevo modelo de conversación
		        
		        // Crear un mensaje nuevo con el string del modelo
		        HashMap<String, String> properties= new HashMap<String, String>();
		        properties.put("configurationMessage", "true");
		        properties.put("config_file", file.getName());
		        this.chatroom.sendThreadedMessage(fileDataString, properties);
				
			} catch (FileNotFoundException e) {				
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
	

}  
