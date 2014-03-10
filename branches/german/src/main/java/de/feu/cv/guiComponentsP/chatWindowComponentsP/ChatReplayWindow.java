package de.feu.cv.guiComponentsP.chatWindowComponentsP;

import javax.swing.*;

import de.feu.cv.applicationLogicP.Resources;
import de.feu.cv.applicationLogicP.chatFileP.ChatFile;
import de.feu.cv.applicationLogicP.conversationP.Conversation;

import java.io.File;

/**
 * Displays a saved chat conversation.
 * @author Verena Kunz
 *
 */
public class ChatReplayWindow extends ChatWindow {
	

	private static final long serialVersionUID = 675157372162233901L;
	/**
	 * The ChatFile displayed.
	 */
	private ChatFile chatfile;
	private JMenuBar jJMenuBar = null;
	/**
	 * The replay panel.
	 */
	private ReplayPanel replayPanel;
	/**
	 * The data model of the saved chat file.
	 */
	private Conversation conversation;
    private JMenuItem showOrBrowseConversationMenuItem ;

    /**
	 * This is the default constructor.
	 * @param file the ChatFile object to display
	 */
	public ChatReplayWindow(ChatFile file) {
		super();
		chatfile = file;
		initialize();
	}

	/**
	 * This method initializes this.
	 * 
	 */
	private void initialize() {
		this.setTitle(chatfile.getFile().getAbsolutePath());
		conversation = chatfile.getConversation();
		
		// creating the visualization panes
		this.createVisualizationPanes(conversation);
		
		jContentPane.add("South",getReplayPanel());
		
		this.setJMenuBar(getJJMenuBar());
		
		this.setVisible(true);	
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				chatfile.leave();
				dispose();
			}
		});		
	}

    @Override
    protected JMenuItem getShowOrBrowseConversationMenuItem() {

        if (showOrBrowseConversationMenuItem == null) {
            showOrBrowseConversationMenuItem = new JMenuItem();
            showOrBrowseConversationMenuItem.setText(Resources.getString("edit_conversation_type"));
            showOrBrowseConversationMenuItem.setEnabled(false);
        }
        return showOrBrowseConversationMenuItem;
    }

    @Override
    protected void updateConversationModel(File file) {
        //Do nothing on replay.
    }


    /**
	 * This method initializes jJMenuBar.	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			// view menu is inherited from ChatWindow
			jJMenuBar.add(getViewMenu());
			jJMenuBar.add(getExtrasMenu());
		}
		return jJMenuBar;
	}
		
		
	/**
	 * This method initializes textInputPane.	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JPanel getReplayPanel() {
		if (replayPanel == null) {
			replayPanel = new ReplayPanel(conversation);
					}
		return replayPanel;
	}
}




