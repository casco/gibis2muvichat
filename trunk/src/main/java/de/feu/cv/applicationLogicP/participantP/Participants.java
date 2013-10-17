package de.feu.cv.applicationLogicP.participantP;

import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;

import de.feu.cv.transportP.RoomConnection;

/**
 * The model for the participant list.
 * Uses the observer pattern to get participant changes from the roomconnection.
 * @author Verena Kunz
 *
 */
public class Participants extends DefaultListModel implements Observer {
	/**
	 * The generated serialVersionUID.
	 */
	private static final long serialVersionUID = -3101658557105086475L;
	Observable roomConnection;
	
	/**
	 * Creates new participants list which is listening to the observable.
	 * @param roomConnection
	 */
	public Participants(Observable roomConnection){
        this.roomConnection = roomConnection;
        roomConnection.addObserver(this);
	}
	/**
	 * Update the model when new participants changes arrive.
	 * @param obs	the Observable (RoomConnection)
	 * @param arg	the incoming message (ParticipantsChange)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable obs, Object arg) {
	
		if (obs instanceof RoomConnection && arg instanceof ParticipantsChange){

			ParticipantsChange pc = (ParticipantsChange)arg;
			final boolean available = pc.isAvailable();
			final String nick = pc.getNick();
			final String resource = pc.getResource();
			final Participant p = new Participant(nick,resource);
			
			SwingUtilities.invokeLater(new Runnable()
		    {
		      public void run()
		      {
					if (available){
						if (!contains(p)){
							addElement(p);
						}
					}
					if (!available){
						if (contains(p)){
							removeElement(p);
						}
					}
		      }
		    });
			

			fireIntervalAdded(this,0,this.getSize());
		
		}	
	}
}
