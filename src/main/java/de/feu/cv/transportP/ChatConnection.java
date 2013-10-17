package de.feu.cv.transportP;


import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.Time;

import de.feu.cv.applicationLogicP.ChatAdministration;
import de.feu.cv.applicationLogicP.MuViChatConstants;
import de.feu.cv.applicationLogicP.Resources;


/**
 * Represents the connection to the chat server.
 * Uses the smack library for jabber chat connections
 * @author Verena Kunz
 *
 */
public class ChatConnection {

	
	/**
	 * The single Instance of ChatAdministration.
	 */
	private static ChatConnection singleInstance = new ChatConnection();
	/**
	 * The connection to the Jabber server
	 */
	private XMPPConnection xmppconnection;
	private long timeoffset=0;
	private static final String resource=MuViChatConstants.resource;
	private static final boolean debug_enabled = false;
	private static final int timeout=7000;
	
	/**
	 * Private contstructor of a chat connection.
	 */
	private ChatConnection(){
		// enabling debug modus shows all jabber messages in a separate window
		XMPPConnection.DEBUG_ENABLED = debug_enabled;
		// set timeout
		SmackConfiguration.setPacketReplyTimeout(timeout);
	}
	
	/**
	 * Returns the single instance of the class ChatConnection.
	 * @return the single instance of ChatConnection
	 */
	public static synchronized ChatConnection getInstance(){
		return singleInstance;
	}
	
	/**
	 * Returns the connection status.
	 * @return <code>true</code> if an connection is established; <code>false</code>otherwise.
	 */
	public boolean isConnected() {
		boolean connected = false;
		if (xmppconnection != null){
			connected = xmppconnection.isConnected();
		}
		return connected;
	}

	/**
	 * Opens a connection with the given connection configuration.
	 * @param cc the connection configuration
	 * @throws Exception
	 */
	public void openConnection(ConnectionConfiguration cc) throws Exception {
		try {
			XMPPConnection newconnection = new XMPPConnection(cc.getServer(),cc.getPort());
			newconnection.login(cc.getUser(),cc.getPasswd(),resource);
			newconnection.addConnectionListener(new ConnectionListener(){				
				public void connectionClosed() {
					//nothing to do
				}
				public void connectionClosedOnError(Exception e) {
			    	ChatAdministration.getInstance().goneOffline();
				}					
			});
			xmppconnection = newconnection;
			calculateTimeOffset();
		} catch (XMPPException e) {
			throw new Exception(ErrorTextGenerator.getErrorText(e));
		}		
	}

	
	/**
	 * Closes the current connection.
	 */
	public void closeConnection() {
		xmppconnection.close();
	}
	
	/**
	 * Creates a Jabber account with the given connection configuration.
	 * @param cc the connection configuration
	 * @throws Exception
	 */
	public void createAccount(ConnectionConfiguration cc) throws Exception{		
		try {
			XMPPConnection accountcreationxmppconnection = new XMPPConnection(cc.getServer(),5222);
			AccountManager am = accountcreationxmppconnection.getAccountManager();
			if (am.supportsAccountCreation()){
				am.createAccount(cc.getUser(),cc.getPasswd());
			}
			else
				throw new Exception(Resources.getString("msg_account_creation_not_supported"));
				
		} catch (XMPPException e) {
			throw new Exception(ErrorTextGenerator.getErrorText(e));
		}
	}
	
	/**
	 * Delete the currently logged in account.
	 * @throws Exception 
	 */
	public void deleteAccount() throws Exception{
		AccountManager am = xmppconnection.getAccountManager();
		try {
			am.deleteAccount();
		} catch (XMPPException e) {
			throw new Exception(ErrorTextGenerator.getErrorText(e));
		}
	}
	

	/**
	 * Changes the passwort of the currently logged in account.
	 * @param newpassword the new account
	 * @throws Exception
	 */
	public void changePassword(String newpassword) throws Exception{
		AccountManager am = xmppconnection.getAccountManager();

		try {
			am.changePassword(newpassword);
		} catch (XMPPException e) {
			throw new Exception(ErrorTextGenerator.getErrorText(e));
		}		
	}
	
	
	/**
	 * Gives the name of the Multi-User-Chat-Service on the connected server.
	 * 
	 * @return the name of the MUC-Service
	 * @throws Exception 
	 */
	public String getMUCServiceName() throws Exception{
        //get the name of muc service
		String mucservice = null;
		Collection mucservices;
		try {
			mucservices = MultiUserChat.getServiceNames(xmppconnection);
			Iterator it = mucservices.iterator();			
			// most servers have only one, so we take that one
			mucservice = (String)it.next();
		} catch (XMPPException e) {
			e.printStackTrace();
			throw new Exception(ErrorTextGenerator.getErrorText(e));
			
		}
		return mucservice;	
	}		
	/**
	 * Gives a list of chatrooms on the connected server.
	 * @return an iterator of the sorted name list
	 * @throws Exception 
	 */
	public Iterator getRoomList() throws Exception{
			// get the roomlist in this service
			Collection roomlist = null;
			try {
				roomlist = MultiUserChat.getHostedRooms(xmppconnection, getMUCServiceName());
			} catch (XMPPException e) {
				e.printStackTrace();
				throw new Exception(ErrorTextGenerator.getErrorText(e));
				
			}
			TreeSet<String> sortedgrouplist = new TreeSet<String>();
			Iterator it = roomlist.iterator();
			while (it.hasNext()){
				HostedRoom hr = (HostedRoom)it.next();
				sortedgrouplist.add(hr.getName());
			}
			Iterator sorted_it = sortedgrouplist.iterator();
			return sorted_it;			
	}
	
	/**
	 * Gives a list of the chatroom jids on the connected server.
	 * @return an iterator of the jid list
	 * @throws Exception 
	 */
	public Iterator getRoomJidList() throws Exception{
			// get the roomlist in this service
			Collection roomlist = null;
			try {
				roomlist = MultiUserChat.getHostedRooms(xmppconnection, getMUCServiceName());
			} catch (XMPPException e) {
				e.printStackTrace();
				throw new Exception(ErrorTextGenerator.getErrorText(e));
				
			}
			return roomlist.iterator();			
	}
	
	/**
	 * Login to existing chatroom or create new one if it doesn't exist.
	 * @param roomname
	 * @return a roomconnection
	 */
	public RoomConnection createChat(String roomname,String nickname){
		
		RoomConnection roomconnection = null;
		String mucName;
		try {
			mucName = getMUCServiceName();
			String roomjid = roomname + "@"+ mucName;	
			MultiUserChat muc = new MultiUserChat(xmppconnection,roomjid);
			roomconnection = new RoomConnection(muc,nickname);
			

//			dann ist n�mlich keine Unterscheidung m�glich zwischen
//			muvi-Benutzern und anderen !!
//					try {
//						RoomInfo info = MultiUserChat.getRoomInfo(xmppconnection, roomjid);
//						System.out.println("nicht anonym:"+ info.isNonanonymous());
//					} catch (XMPPException e) {
//
//						e.printStackTrace();
//					}
			
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return roomconnection;
	
	}
	/**
	 * Gives the userid of the current connection.
	 * @return the user id
	 */
	public String getConnectedUserID()
	{
		return StringUtils.parseName((xmppconnection.getUser()));
	}
	

	/**
	 * Sets the time offset between server and client time.
	 */
	private void calculateTimeOffset() {
		long servertime = getServerTime().getTime();
		long clienttime = new Date().getTime()/1000*1000;
		timeoffset = servertime-clienttime;
	}
		
	/**
	 * Returns the server time.
	 * @return the server time
	 */
	public Date getServerTime(){
		 Date serverTime = null;
		 // Create a time request
		 Time timeRequest = new Time();
		 timeRequest.setType(IQ.Type.GET);
		 timeRequest.setTo(xmppconnection.getHost());

		 // Create a packet collector to listen for a response.
		 PacketCollector collector = xmppconnection.createPacketCollector(
				 new PacketIDFilter(timeRequest.getPacketID()));

		 xmppconnection.sendPacket(timeRequest);

		 // Wait up to 5 seconds for a result.
		 IQ result = (IQ)collector.nextResult(5000);
		 if (result != null && result.getType() == IQ.Type.RESULT) {
		     Time timeResult = (Time)result;
		     serverTime = timeResult.getTime();
		 }
		 return serverTime;
	}
	
	/**
	 * Returns the time offset between server and client.
	 * @return difference between servertime and clienttime in ms
	 */
	public long getTimeOffset(){
		return timeoffset;
	}
}
