package de.feu.cv.transportP;

import java.util.Date;
import java.util.Iterator;
import java.util.Observable;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.DelayInformation;
import org.jivesoftware.smackx.packet.MUCUser;
import org.jivesoftware.smackx.packet.MUCUser.Item;

import de.feu.cv.applicationLogicP.ChatAdministration;
import de.feu.cv.applicationLogicP.conversationP.ThreadedMessage;
import de.feu.cv.applicationLogicP.participantP.ParticipantsChange;

/**
 * Mangages the connection to one chat room.
 * uses the observer pattern
 * @author Verena Kunz
 */
public class RoomConnection extends Observable{

	/**
	 * The connection to the chat room.
	 */
	private MultiUserChat muc;
	private static long auxid = 0;

	
	/**
	 * Creates a connection to the chat and adds the listeners.
	 * @param gc a smack MultiUserChat object (created by ChatConnection)
	 */
	
	
	/**
	 * Creates a connection to the chat and adds the listeners.
	 * @param muc a smack MultiUserChat object (created by ChatConnection)
	 * @param nickname the nickname to connect with the room
	 */
	public RoomConnection (MultiUserChat muc, String nickname) {
		this.muc = muc;

		// process incoming messages
		PacketListener meLi = new PacketListener(){
	        public void processPacket(Packet packet) {
	        	Message message = (Message)packet;
	        	Date date = getDate(message);
	        	String text = message.getBody();
	        	String nick = StringUtils.parseResource(message.getFrom());
	        	String id = message.getPacketID();
	        	String parent_nick = (String) message.getProperty("parent_nick");
	        	String parent_id = (String) message.getProperty("parent_id");

                //TODO: Obtener otras propiedades que hayamos seteado en el mensaje
	        	
	        	// messages from other clients may have no id
	        	// generate id for them
	        	if (id==null)
	        		id = geAuxID();
	        	// do not process system messages 
	        	if (!nick.equals("")){ 
	        		// generate ThreadedMessage
		        	ThreadedMessage threadedmessage = new ThreadedMessage(date,text,nick,id,parent_nick,parent_id, message);
	        		setChanged();
	        		notifyObservers(threadedmessage);	        		
	        	}
	        }
	    };
	    
	    // process incoming participation packets
		PacketListener paLi =  new PacketListener() {
	        public void processPacket(Packet packet) {
	        	
	        	Presence pres = (Presence)packet;
	        	boolean available=false;
	        	boolean processpresence = false;
	        	String resource = null;
	        	if (pres.getType()==Presence.Type.AVAILABLE){
	        		processpresence = true;
	        		available = true;
	        		
	        		// get the resource name of the extended jid
	        		MUCUser.Item mucuser = ((MUCUser) pres.getExtension("x", "http://jabber.org/protocol/muc#user")).getItem();
	        		String jid = mucuser.getJid();
	        		if (jid!=null)
	        			resource = StringUtils.parseResource(jid);       		
	        		
	        	}
	        	if (pres.getType()==Presence.Type.UNAVAILABLE){
	        		processpresence = true;
	        		available = false;
	        	}
	        	if (processpresence){	

	        		String nick = StringUtils.parseResource(pres.getFrom());
	        		ParticipantsChange pc = new ParticipantsChange(nick,available,resource);
	        		setChanged();
	        		notifyObservers(pc);
	        	}
	        }
	    };

		muc.addMessageListener(meLi);
		muc.addParticipantListener(paLi);
	}


	/**
	 * Returns date from message which contains delay information.
	 * The return date is adjusted to the client time.
	 * @param message 
	 * @return the date of the message, <code>null</code> if the message contains no date
	 */
	protected Date getDate(Message message) {
		Date date = new Date();
		DelayInformation di = (DelayInformation) message.getExtension("x", "jabber:x:delay");
		if (di !=null){
			Date stamp = di.getStamp();
			long timeoffset = ChatAdministration.getInstance().getChatConnection().getTimeOffset();
			date = new Date(stamp.getTime()-timeoffset);
		}
		return date;
	}
	/**
	 * Joins the room with the given nickname.
	 * Creates new room if it doesnt exist
	 * @param nickname the nickname to join the chatroom
	 * @throws Exception 
	 */
	public void createOrJoinRoom(String nickname) throws Exception {
		// check if room already exists		
		boolean exists = false;
				
		Iterator it = ChatConnection.getInstance().getRoomJidList();
		
		while (it.hasNext() && !exists){
			String nextjid = ((HostedRoom) it.next()).getJid();
			if ( nextjid.equals(muc.getRoom())){
				exists = true;
			}
		}
		
		if (exists){
			// join
			try {
				muc.join(nickname);
			} catch (XMPPException e) {
				throw new Exception(ErrorTextGenerator.getErrorText(e));
			}
		}
		else {
			// create
			try {
				muc.create(nickname);
				muc.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));
			} catch (XMPPException e) {
				throw new Exception(ErrorTextGenerator.getErrorText(e));
			}
		}		
	}
	/**
	 * Sends the given text to chat room.
	 * @param text
	 * @throws Exception 
	 */
	public void sendMessage(String text) throws Exception {
		sendThreadedMessage(text,null);
	}
	/**
	 * Sends the given text to chatroom and references.
	 * a previous message
	 * @param text the text so send
	 * @param tm the referenced message
	 * @throws Exception 
	 */
	public void sendThreadedMessage(String text,ThreadedMessage tm) throws Exception{
		Message message = new Message(muc.getRoom(),Message.Type.GROUP_CHAT);
		message.setBody(text);
		// generate ID with timestamp
		// uncommended because it needs iq packages to get server time
		// message.setPacketID(getUniqueID());
		if (tm != null){
			String parent_nick = tm.getNick();
			String parent_id = tm.getID();

			
			if (parent_id != null){
				
				message.setProperty("parent_nick", parent_nick);
				message.setProperty("parent_id", parent_id);

                //TODO setear otras propiedades que nos interese setear / tal vez necesitemos recibirlas
                //como parametros a este método.

			}
		}	
		try {
			muc.sendMessage(message);
			//groupchat.sendMessage(message);
		} catch (XMPPException e) {
			throw new Exception(ErrorTextGenerator.getErrorText(e));
		}			
	}
	


	/**
	 * Leaves the chatroom.
	 */
	public void leave(){
		muc.leave();

	}
	
	/**
	 * Returns a unique id.
	 * @return a unique id
	 */
	private static synchronized String geAuxID(){
		String prefix = "_noID";
		String s = prefix + auxid;
		auxid++;
		return s;
	}

}
