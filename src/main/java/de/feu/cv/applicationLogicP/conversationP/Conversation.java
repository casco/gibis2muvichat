package de.feu.cv.applicationLogicP.conversationP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeSet;

import prefuse.data.Node;
import prefuse.data.Tree;
import prefuse.data.tuple.TableNode;
import de.feu.cv.transportP.RoomConnection;

/**
 * The model for the threaded conversation.
 * Uses the observer pattern to get messages from the roomconnection an to 
 * send messages to the GUI
 *
 * @author Verena Kunz
 *
 */
/**
 * @author Verena Kunz
 *
 */
public class Conversation extends Observable implements Observer, Serializable {


	private static final long serialVersionUID = 1161865393649225575L;

	/**
	 * The observable to listen to.
	 */
	private transient Observable roomConnection;
	/**
	 * The selected message.
	 */
	private ThreadedMessage selection = null;
	

	// model of all messages
	/**
	 * The background model (list submodel).
	 */
	private ChatListModel backgroundmessagelist;
	/**
	 * The background model (tree submodel).
	 */
	private MessageTree backgroundmessagetree;
	/**
	 * The background model (plaindocument submodel).
	 */
	private Transscript backgroundmessagetransscript;
	
	// model of actually displayed messages
	/**
	 * The displayed model (list submodel).
	 */
	private ChatListModel displayedmessagelist;
	/**
	 * The displayed model (tree submodel).
	 */
	private MessageTree displayedmessagetree;	
	/**
	 * The displayed model (plaindocument submodel).
	 */
	private Transscript displayedmessagetransscript;
	/**
	 * Replay modus running flag. 
	 */
	private boolean replay_on;
	/**
	 * Starttime of conversation.
	 */
	private long starttime;
	
	
	/**
	 * Creates the new conversation which listening to the observable.
	 * @param roomConnection
	 */
	public Conversation(Observable roomConnection){
		this();
        //TODO leer el siguiente comentario
        //LA conversación se hace observador del RoomCOnnection para enterarse cada vez que llega un mensaje
        //Ver el método update()
		this.roomConnection = roomConnection;
		this.roomConnection.addObserver(this);
	}

	/**
	 * Creates a new conversation (not listenting to an observable).
	 */
	public Conversation(){
		backgroundmessagetree = new MessageTree();
		backgroundmessagelist = new ChatListModel();
		backgroundmessagetransscript = new Transscript();
		
		//display all messages at start time
		displayedmessagelist = new ChatListModel();
		displayedmessagetree = new MessageTree();
		displayedmessagetransscript = new Transscript();

	}

	/**
	 * Creates a conversation from a saved tree.
	 * @param savedTree
	 */
	public Conversation(Tree savedTree) {
		this();
				
		Iterator it = getChronologicList(savedTree);
		while (it.hasNext()){
						
			ThreadedMessage message = (ThreadedMessage) it.next();
			addBackgroundElement(message);
			addDisplayedElement(message);

		}

	}

	/** 
	 * Update the model when new messages arrive.
	 * @param obs	the Observable (RoomConnection)
	 * @param arg	the incoming message (ThreadedMessage)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable obs, final Object arg) {
		if (obs instanceof RoomConnection && arg instanceof ThreadedMessage){
	    	  //change model when new message comes in
			  addBackgroundElement((ThreadedMessage) arg);
			  
			  //only update visualizations when not in replay mode
			  if (!replay_on){
				  addDisplayedElement((ThreadedMessage) arg);

			  }
		}	
	}

	/**
	 * Adds a message to the conversation.
	 * @param message
	 */
	private void addBackgroundElement(ThreadedMessage message) {
				
		//add to transscript
		backgroundmessagetransscript.addMessage(message);
		// add to list
		backgroundmessagelist.addMessage(message);
		// add to tree
		backgroundmessagetree.addMessage(message);
	}

	/**
	 * Returns the selected ThreadedMessage.
	 * @return the selected ThreadedMessage; <code>null</code>if no message is selected
	 */
	public ThreadedMessage getSelection() {
		return selection;
	}

	/**
	 * Sets the selection to one of the messages.
	 * @param selection the selected message
	 */
	public void setSelection(ThreadedMessage selection) {
		this.selection = selection;
        setChanged();
        //TODO Es posible que esto rompa algo. Veremos.
        //The message was the argument
        // Now I say that null means something else changes, possible it selection.
        notifyObservers(null);

	}
	
	/**
	 * Sets the selection to one of the messages.
	 * @param selectednode the node of the selected message
	 */
	public void setSelection(Node selectednode) {

		setSelection(getThreadedMessage(selectednode));
	}
	
	/**
	 * Resets the selection.
	 */
	public void clearSelection() {
		this.selection = null;
	}
	
	
	
	/**
	 * Gives the list of the ancesters of a message (including the message itself).
	 * @param tm the message
	 * @return the list of ancesters
	 */
	public ArrayList getAncestors(ThreadedMessage tm){
		ArrayList<ThreadedMessage> list = new ArrayList<ThreadedMessage>();
	
		while (tm != null){
			list.add(tm);
			tm = getParent(tm);
			//tm = getThreadedMessage(getParentNode(tm));
		}
		return list;
	}
	/**
	 * Gives the parent message of a message.
	 * @param tm the message
	 * @return the parent message of the given message; 
	 * 		   <code>null</code>if there is no parent message
	 */
	public ThreadedMessage getParent(ThreadedMessage tm){
		String id = tm.getParent_id();
		String nick = tm.getParent_nick();
		ThreadedMessage parentmessage = null;
		//for ( Iterator i = messagelist.iterator(); i.hasNext(); ){
		for ( Enumeration i = backgroundmessagelist.elements(); i.hasMoreElements(); ){
			ThreadedMessage element = (ThreadedMessage)i.nextElement();
			if (element.getNick().equals(nick) && element.getID().equals(id))
					parentmessage = element;
		}
	    return parentmessage;	
	}



	
	/**
	 * Returns the corresponding TableNode of the ThreadedMessage.
	 * @param tm the ThreadedMessage
	 * @return the corresponding TableNode
	 */
	public TableNode getNode(ThreadedMessage tm){
		TableNode node = null;
		String id = tm.getID();
		String nick = tm.getNick();
		for (Iterator i = displayedmessagetree.nodes(); i.hasNext();){
			TableNode nextnode = (TableNode)i.next();
			
			if (nextnode.canGetString("nick") && nextnode.canGetString("id")){
				if (nextnode.getString("nick").equals(nick) && nextnode.getString("id").equals(id))
					node = nextnode;
			}

		}
		return node;
	}
	
	/**
	 * Returns the corresponding ThreadedMessage of a node in the tree.
	 * @param node the node in the tree
	 * @return the corresponding ThreadedMessage
	 */
	public ThreadedMessage getThreadedMessage(Node node){
		
		ThreadedMessage tm = null;
		if (node !=null){
		
			Date date = new Date(node.getLong("date"));
			String text = node.getString("text");
			String nick = node.getString("nick");
			String id = node.getString("id");
			// parents aus Baumstruktur
			String parent_nick = node.getString("parent_nick");
			String parent_id = node.getString("parent_id");
			
			tm = new ThreadedMessage(date, text, nick, id, parent_nick, parent_id, null);
		}
		return tm;
	}

	/**
	 * Sorts a prefuse tree.
	 * @param tree a prefuse tree
	 * @return an iterator of ThreadedMessages
	 */
	public Iterator getChronologicList(Tree tree){
		Iterator node_it = tree.nodes();
		// skip root node
		node_it.next();
		TreeSet<ThreadedMessage> chronologicMessages = new TreeSet<ThreadedMessage>();
		
		while (node_it.hasNext()){
			ThreadedMessage nextmessage = getThreadedMessage((Node) node_it.next());
			chronologicMessages.add(nextmessage);
		}
		
		return chronologicMessages.iterator();
		
	}
	
	
	/**
	 * Returns the messagetree of displayed conversation.
	 * @return the messagetree
	 */
	public Tree getMessageTree(){
		return displayedmessagetree;
	}
	
	/**
	 * Returns listmodel of displayed conversation.
	 * @return the displayed listmodel
	 */
	public ChatListModel getListModel(){
		return displayedmessagelist;
	}
	
	/**
	 * Returns chat transcript of displayed conversation.
	 * @return the displayed document
	 */
	public Transscript getTransscript(){
		return displayedmessagetransscript;
	}

    //TODO: Con este método obtengo todos los mensajes recibidos/enviados.
    //Lo podría usar para construir un inspector
	/**
	 * Returns listmodel of the whole conversation.
	 * @return the listmodel of the whole conversation
	 */
	public ChatListModel getBackgroundListModel(){
		return backgroundmessagelist;
	}
	
	/**
	 * Resets the displayed conversation.
	 */
	public void clearConversation(){
		displayedmessagetree.reset();
		displayedmessagelist.reset();
		displayedmessagetransscript.reset();
	}

	/**
	 * Adds a message to the model of displayed data.
	 * @param message the message to add
	 */
	public void addDisplayedElement(ThreadedMessage message) {
		//add to transscript
		displayedmessagetransscript.addMessage(message);
		// add to list
		displayedmessagelist.addMessage(message);
		// add to tree
		displayedmessagetree.addMessage(message);
		
		setChanged();
		notifyObservers(message);
		
	}

	/**
	 * Switches replay on and off.
	 * @param replay_on
	 */
	public void setReplay_on(boolean replay_on) {
		
		this.replay_on = replay_on;
		
		// restore backup conversation when replay is finished
		if (!replay_on){
			clearConversation();
			Enumeration enu = backgroundmessagelist.elements();
			while (enu.hasMoreElements()){
							
				ThreadedMessage message = (ThreadedMessage) enu.nextElement();
				addDisplayedElement(message);
			}
		}
	}

	/**
	 * Returns the starttime of the conversation.
	 * @return the starttime of the conversation
	 */
	public long getStarttime() {
		if (starttime == 0 && !backgroundmessagelist.isEmpty()){
			ThreadedMessage tm = (ThreadedMessage) backgroundmessagelist.firstElement();
			starttime = tm.getDate().getTime();
		}

		return starttime;
	}
	

	/**
	 * Returns the newest Node of the displayed conversation.
	 * @return tge newest Node
	 */
	public TableNode getNewestNode(){
		TableNode node = null;
		if (!displayedmessagelist.isEmpty())
			node = getNode((ThreadedMessage) displayedmessagelist.lastElement());
		
		return node;
	}

}
