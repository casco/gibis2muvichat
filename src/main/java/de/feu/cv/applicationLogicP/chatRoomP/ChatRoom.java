package de.feu.cv.applicationLogicP.chatRoomP;

import java.io.File;
import java.util.HashMap;

import prefuse.data.io.DataIOException;
import prefuse.data.io.TreeMLWriter;
import de.feu.cv.applicationLogicP.ChatAdministration;
import de.feu.cv.applicationLogicP.conversationP.Conversation;
import de.feu.cv.applicationLogicP.conversationP.ThreadedMessage;
import de.feu.cv.applicationLogicP.participantP.Participants;
import de.feu.cv.guiComponentsP.MainWindow;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatLiveWindow;
import de.feu.cv.transportP.ChatConnection;
import de.feu.cv.transportP.RoomConnection;

/**
 * The class to coordinate the activities in a single chatroom.
 * @author Verena Kunz
 *
 */
public class ChatRoom {

	/**
	 * The corresponding window.
	 */
	private ChatLiveWindow chatwindow; 
	
	/**
	 * The corresponding connection to the chatroom.
	 */
	private RoomConnection roomconnection;
	/**
	 * The list of participants in the room.
	 */
	private Participants participants; 
	/**
	 * The list of messages in the room.
	 */
	private Conversation conversation;
	/**
	 * The name of the room.
	 */
	private String roomname;
	/**
	 * The nickname of the current user.
	 */
	private String nickname;
	
	/**
	 * Creates a Chatroom or joins an existing one.
	 * Displays the chatwindow.
	 * 
	 * @param roomname the name of the room 
	 * @param nickname the nickname 
	 * @throws Exception 
	 */
	public ChatRoom(String roomname, String nickname) throws Exception  {
		
		this.roomname = roomname;
		this.nickname = nickname;

		// connect to room
		ChatAdministration ca = ChatAdministration.getInstance();
		ChatConnection connection = ca.getChatConnection();
		roomconnection = connection.createChat(roomname,nickname);
		
		// create the models
		participants = new Participants(roomconnection);
		conversation = new Conversation(roomconnection);
			
		// create chatwindow
		chatwindow = new ChatLiveWindow(this);


		// login to room (must be after model creation to ensure that
		// the observer pattern between roomconnection and participants
		// works correctly / must be also after chat window creation
		//
		try {
		
			roomconnection.createOrJoinRoom(nickname);
			chatwindow.setVisible(true);
			chatwindow.toFront();
		} catch (Exception e) {
			// dispose chat window when joining room failed
			chatwindow.dispose();
			throw e;
		}
	}

	/**
	 * Returns the current nickname.
	 * @return the current nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Changes the nickname in the current room.
	 * @param nickname the new nickname
	 * @throws Exception 
	 */
	public void changeNickname(String nickname) throws Exception {
		this.nickname = nickname;
		roomconnection.createOrJoinRoom(nickname);
	}


	/**
	 * Returns the roomname of this room.
	 * @return the roomname of this room
	 */
	public String getRoomname() {
		return roomname;
	}


	/**
	 * Returns the model of the participants in the room.
	 * @return the participants of the room
	 */
	public Participants getParticipants() {
		return participants;
	}



	/**
	 * Returns the model of the conversation in the room.
	 * @return the conversation
	 */
	public Conversation getConversation() {
		return conversation;
	}



	/**
	 * Sends a message. If a parent message is selected,
	 * it also sends the reference to that parent message.
	 * @param text the text to send
     * @param properties is a map of key/value pairs of Strings to add as properties to the message
	 */
	public void sendThreadedMessage(String text, HashMap<String, String> properties) {
		ThreadedMessage parent = conversation.getSelection();
		if (parent==null)
			try {
				//{-*-}
				//properties.put("ibis-type", "Issue");
				//{-*-}
				roomconnection.sendMessage(text, properties);
			} catch (Exception e) {
				MainWindow.getInstance().displayMessage(e.getMessage());
			}
		else {
			try {
				roomconnection.sendThreadedMessage(text, parent, properties);
			} catch (Exception e) {
				MainWindow.getInstance().displayMessage(e.getMessage());
			}
			resetSelection();
		}
			
		
	}

	/**
	 * Resets the selection in the chat window.
	 */
	private void resetSelection() {
		chatwindow.resetSelection();
		
	}

	/**
	 * Leaves the chatroom and removes the room from the roomlist.
	 */
	public void leave() {
		roomconnection.leave();
		ChatAdministration.getInstance().removeRoomFromList(roomname);
		
	}
	/**
	 * Brings the corresponding chatwindow to the front.
	 */
	public void setActiveChat(){
		chatwindow.bringToFront();
	}

	/**
	 * Saves the conversation in a file using the TreeML format.
	 * @param selectedFile
	 */
	public void saveToFile(File selectedFile) {

		TreeMLWriter tmlw = new TreeMLWriter();
		try {
			tmlw.writeGraph(conversation.getMessageTree(), selectedFile);
		} catch (DataIOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Closes the corresponding window.
	 */
	public void closeWindow() {
		chatwindow.dispose();
	}

}
