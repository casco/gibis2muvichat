package de.feu.cv.guiComponentsP.chatWindowComponentsP;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JList;

import de.feu.cv.applicationLogicP.participantP.Participants;

import java.awt.Dimension;

/**
 * The scroll pane which holds the participant list.
 * @author Verena Kunz
 *
 */
public class ParticipantsPane extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private JList participantList = null;
    /**
     * The data model for the participants.
     */
    Participants participants;

	/**
	 * Creates a new scrollpane with participant list.
	 * @param participants
	 */
	public ParticipantsPane(Participants participants) {
		
		super();
		this.participants = participants;
		initialize();
		
	}

	/**
	 * This method initializes this.
	 */
	private void initialize() {

		this.setPreferredSize(new Dimension(3, 200));
		this.setViewportView(getParticipantList());
	}

	/**
	 * This method initializes participantList.	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getParticipantList() {
		if (participantList == null) {
			participantList = new JList(participants);
			participantList.setBackground(Color.lightGray);
		}
		return participantList;
	}

}
