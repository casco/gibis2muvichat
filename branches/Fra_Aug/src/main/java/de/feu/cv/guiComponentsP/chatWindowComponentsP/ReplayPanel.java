package de.feu.cv.guiComponentsP.chatWindowComponentsP;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import de.feu.cv.applicationLogicP.Resources;
import de.feu.cv.applicationLogicP.conversationP.Conversation;
import de.feu.cv.applicationLogicP.conversationP.ReplayThread;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;

/**
 * Panel with replay buttons, slider and replay state.
 * @author Verena Kunz
 *
 */
public class ReplayPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The data model of the replayed conversation.
	 */
	private Conversation conversation;
	/**
	 * The thread which is doing the replay.
	 */
	private ReplayThread thread;
	/**
	 * Pause flag.
	 */
	private boolean pause;
	
	/**
	 * Running flag. 
	 */
	private boolean running;
	private JButton playButton = null;
	private JButton pauseButton = null;
	private JButton stopButton = null;
	private JSlider jSlider = null;

	
    private ImageIcon pauseButtonIcon = new ImageIcon(getClass().getResource("/images/Pause24.gif"));
    private ImageIcon playButtonIcon = new ImageIcon(getClass().getResource("/images/Play24.gif"));
    private ImageIcon stopButtonIcon = new ImageIcon(getClass().getResource("/images/Stop24.gif"));
	private JLabel replayRateLabel = null;
	private JLabel replayOnLabel = null;
	private JPanel layoutHelpPanel = null;
	/**
	 * This is the default constructor.
	 */
	public ReplayPanel(Conversation conversation) {
		super();
		this.conversation = conversation;
		initialize();
	}

	/**
	 * This method initializes this.
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
		gridBagConstraints32.gridx = 7;
		gridBagConstraints32.weightx = 1.0D;
		gridBagConstraints32.gridy = 1;
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.gridwidth = 3;
		gridBagConstraints4.gridy = 2;
		replayOnLabel = new JLabel();
		replayOnLabel.setText(" ");
		GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
		gridBagConstraints31.gridx = 4;
		gridBagConstraints31.anchor = GridBagConstraints.CENTER;
		gridBagConstraints31.insets = new Insets(0, 10, 10, 10);
		gridBagConstraints31.weightx = 0.0D;
		gridBagConstraints31.gridheight = 2;
		gridBagConstraints31.gridy = 1;
		replayRateLabel = new JLabel();
		replayRateLabel.setText(Resources.getString("lab_replayrate"));
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.gridx = 5;
		gridBagConstraints21.anchor = GridBagConstraints.WEST;
		gridBagConstraints21.gridy = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weightx = 0.0D;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.gridx = 5;
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 2;
		gridBagConstraints3.gridy = 1;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 1;
		gridBagConstraints2.gridy = 1;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createTitledBorder(null, Resources.getString("lab_replay_control"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
		this.add(getPlayButton(), gridBagConstraints1);
		this.add(getPauseButton(), gridBagConstraints2);
		this.add(getStopButton(), gridBagConstraints3);
		this.add(getJSlider(), gridBagConstraints);
		this.add(replayRateLabel, gridBagConstraints31);
		this.add(replayOnLabel, gridBagConstraints4);
		this.add(getLayoutHelpPanel(), gridBagConstraints32);
		
	}

	/**
	 * This method initializes playButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getPlayButton() {
		if (playButton == null) {
			playButton = new JButton();
			playButton.setIcon(playButtonIcon);
			playButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					replay();

					    
				}
			});
		}
		return playButton;
	}

	/**
	 * This method initializes pauseButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getPauseButton() {
		if (pauseButton == null) {
			pauseButton = new JButton();
			pauseButton.setIcon(pauseButtonIcon);
			pauseButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (thread !=null){
						if (thread.isAlive()){
							thread.pauseReplay();
							pause = true;
							replayOnLabel.setText(Resources.getString("lab_replaypaused"));
						}
					}
				}
			});
		}
		return pauseButton;
	}

	/**
	 * This method initializes stopButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getStopButton() {
		if (stopButton == null) {
			stopButton = new JButton();
			stopButton.setIcon(stopButtonIcon);
			stopButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (thread != null){
						thread.stopReplay();
						pause = false;
					}
				}
			});
		}
		return stopButton;
	}

	/**
	 * This method initializes jSlider.	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSlider() {
		if (jSlider == null) {
			jSlider = new JSlider(JSlider.HORIZONTAL,1,50,1);
			
			
			Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
			labelTable.put( new Integer( 1 ), new JLabel(Resources.getString("lab_realtime")));
			labelTable.put( new Integer(50), new JLabel(Resources.getString("lab_faster")) );
			jSlider.setLabelTable( labelTable );

			jSlider.setPaintLabels(true);
			jSlider.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (thread!=null){
						thread.setSpeedfactor(jSlider.getValue());
					}
				}
			});
		}
		return jSlider;
	}
	
	/**
	 * Plays or replays the conversation.
	 */
	private void replay(){
		// start new thread only when no one is running
		if (!running){
			running = true;
			thread = new ReplayThread(this,conversation);
			thread.setSpeedfactor(jSlider.getValue());
			thread.start();	
			replayOnLabel.setText(Resources.getString("lab_replayrunning"));
		}
		
		else {
			if (pause){ // end of pause
				replayOnLabel.setText(Resources.getString("lab_replayrunning"));
				thread.resumeReplay();
			    pause = false;
			}
			// else: thread ist already running: do nothing
		}
	}

	/**
	 * Sets the running state to false.
	 */
	public void setFinishedInfo() {
		running = false;
		replayOnLabel.setText(Resources.getString("lab_replayfinished"));
		
	}

	/**
	 * This method initializes layoutHelpPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getLayoutHelpPanel() {
		if (layoutHelpPanel == null) {
			layoutHelpPanel = new JPanel();
			layoutHelpPanel.setLayout(new GridBagLayout());
		}
		return layoutHelpPanel;
	}
	

}
