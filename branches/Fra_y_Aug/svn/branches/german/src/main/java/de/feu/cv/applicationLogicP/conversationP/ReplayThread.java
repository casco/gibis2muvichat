package de.feu.cv.applicationLogicP.conversationP;

import java.util.Date;

import de.feu.cv.guiComponentsP.chatWindowComponentsP.ReplayPanel;

/**
 * Thread to replay discussion history.
 */
public class ReplayThread extends Thread

{
	/**
	 * The factor how many times faster the replay is.
	 */
	private double speedfactor;
	/**
	 * The pause flag.
	 */
	private boolean pause = false;
	/**
	 * The data model to replay.
	 */
	private Conversation conversation;
	/**
	 * The sub data model (chronologic).
	 */
	private ChatListModel messagelist;
	/**
	 * The panel to control the replay thread.
	 */
	private ReplayPanel panel;
	
	/**
	 * Creates a new replay thread.
	 * @param panel		the control panel of the thread
	 * @param conversation the conversation to replay
	 */
	public ReplayThread(ReplayPanel panel, Conversation conversation) {
		conversation.setReplay_on(true);
		this.panel = panel;
		this.speedfactor = 1f;
		this.conversation = conversation;
		this.messagelist = conversation.getBackgroundListModel();
		conversation.clearConversation();
	}

	/**
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run(){		
		replayMessages();
	}

	
	/**
	 * Replays the messages in the conversation.
	 */
	private void replayMessages(){
		
		int i = 0;
		// first message or first message after pause
		ThreadedMessage message = (ThreadedMessage)messagelist.get(i);
		Date messagedate = message.getDate();
		i++;
		
		while (i <  messagelist.size()&& (!isInterrupted())){
			
			if (!pause){
				conversation.addDisplayedElement(message);
				ThreadedMessage nextmessage =  (ThreadedMessage)messagelist.get(i);
				Date nextmessagedate = nextmessage.getDate();
				long diff = nextmessagedate.getTime()- messagedate.getTime();
				try {
					Thread.sleep((long) (diff/speedfactor));
					message = nextmessage;
					messagedate = nextmessagedate;	
					i++;
				} catch (InterruptedException e) {
					interrupt();
				}
			}

			else { // pause
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					interrupt();
				}	
			}

			
		}
		if (!isInterrupted())
		// don't forget last message
			conversation.addDisplayedElement(message);
		// thread is finished
		panel.setFinishedInfo();
	}

	/**
	 * Sets pause flag.
	 */
	public void pauseReplay() {
		pause = true;
	}

	/**
	 * Stops the thread and recover the conversation.
	 */
	public void stopReplay() {
		interrupt();
		conversation.setReplay_on(false);	
	}
	
	/**
	 * Resets pause flag.
	 */
	public void resumeReplay(){
		pause = false;
	}
	/**
	 * Sets the speedfactor.
	 * @param speedfactor the speed factor (1: realtime, x:x times faster)
	 */
	public void setSpeedfactor(double speedfactor){
		this.speedfactor = speedfactor;
	}
	
	
}	