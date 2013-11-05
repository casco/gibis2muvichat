package de.feu.cv.applicationLogicP.chatRoomP;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.table.AbstractTableModel;

import de.feu.cv.applicationLogicP.Resources;


/**
 * This class is the model for the list of connected chat rooms.
 * @author Verena Kunz
 *
 */
/**
 * @author Verena Kunz
 *
 */
public class ChatRoomTableModel extends AbstractTableModel{

	/**
	 * The generated serialVersionUID.
	 */
	private static final long serialVersionUID = -8548700044619064774L;
	
	/**
	 * Dictionary of chatrooms whith roomname as key.
	 */
	private Hashtable<String, ChatRoom> roomlist;
	
	/**
	 * Creates a new model for the chat room list.
	 */
	public ChatRoomTableModel() {
		super();
		roomlist = new Hashtable<String, ChatRoom>();
	}


	/** 
	 * Two columns.
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return 2;
	}


	/** 
	 * One row per room.
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		int rowcount = roomlist.size();
		return rowcount;
	}


	/** 
	 * The values for the cells.
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		String content = null;
		// sort the keys
		TreeSet<String> keyset = new TreeSet<String>(roomlist.keySet());
		String[] keylist = keyset.toArray(new String[keyset.size()]);
		String roomname = keylist[rowIndex];
		if (columnIndex == 0)
			content = roomname;
		if (columnIndex == 1){
			ChatRoom cr =roomlist.get(roomname);
			content = cr.getNickname();
		}
		return content;

	}

	/** 
	 * The column names.
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int columnIndex) {
		String columnname = null;
		if (columnIndex == 0)
			columnname = Resources.getString("ch_chatroom");
		if (columnIndex == 1)	
			columnname = Resources.getString("ch_nickname");
		return columnname;
	}

	/**
	 * Tests, if the given roomname is in the list.
	 * @param roomname
	 * @return <code>true</code> if the roomname is in the list; <code>false</code>otherwise
	 */
	public boolean containsKey(String roomname) {
		return roomlist.containsKey(roomname);
	}

	/**
	 * Returns the ChatRoom with the given roomname.
	 * @param roomname the name of the room
	 * @return the ChatRoom object with the roomname
	 */
	public ChatRoom get(String roomname) {
		return roomlist.get(roomname);
	}

	/**
	 * Add the chat room to the list.
	 * @param roomname the roomname
	 * @param chatroom the room object
	 */
	public void put(String roomname, ChatRoom chatroom) {
		roomlist.put(roomname, chatroom);
		fireTableDataChanged();
		
	}
	
	/**
	 * Removes the chat room from the list.
	 * @param roomname the roomname
	 */
	public void remove(String roomname){
		roomlist.remove(roomname);
		fireTableDataChanged();
	}
	
	/**
	 * Activates one chatroom.
	 * @param row the line number of the chatroom
	 */
	public void setActiveChat(int row){
		String roomname = (String)getValueAt(row,0);
		ChatRoom chatroom = get(roomname);
		chatroom.setActiveChat();
	}
	
	/**
	 * Returns an iterator over the list of open chatrooms.
	 * @return the iterator
	 */
	public Iterator getAllOpenChatRooms(){
		Set rooms = roomlist.keySet();		
		return rooms.iterator();
	}


}
