package de.feu.cv.guiComponentsP.pluginsP.classicChat;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Observable;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.feu.cv.applicationLogicP.conversationP.Conversation;
import de.feu.cv.applicationLogicP.conversationP.ThreadedMessage;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane;


/**
 *  Visualization implementation "Classic Chat".
 * @author Verena Kunz
 *
 */
public class VisualizationPane extends ChatVisualizationPane  {

	private static final long serialVersionUID = 1L;
	private JTextArea messageArea;
	private VisualizationProperties visualProperties;  //  @jve:decl-index=0:
	private JScrollPane jScrollPane = null;



	/**
	 * Creates a new visualization pane. 
	 * @param conversation the displayed conversation
	 */
	public VisualizationPane(Conversation conversation) {
		super(conversation);
		visualProperties = new VisualizationProperties();	
		visualProperties.addObserver(this);
		
		initialize();
	}

	/**
	 * This method initializes this.
	 */
	private void initialize() {
		
		
		this.setLayout(new BorderLayout());
		this.setSize(300, 200);
//		this.setViewportView(getMessageArea());

		this.add(getJScrollPane(), BorderLayout.CENTER);
	}



	/**
	 * This method initializes messageArea.	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JTextArea getMessageArea() {
		if (messageArea == null) {
			messageArea = new JTextArea(conversation.getTransscript());
			changeProperty("bgcolor",visualProperties.getProperty("bgcolor"));	
			changeProperty("fgcolor",visualProperties.getProperty("fgcolor"));	
			changeProperty("size",visualProperties.getProperty("size"));	
			messageArea.setEditable(false);
		}
		return messageArea;
	}



	/**
	 * Displays a message in the visualization.
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable obs, Object arg) {
		//System.out.println("update");
		if (obs instanceof Conversation && arg instanceof ThreadedMessage){
			messageArea.setCaretPosition(messageArea.getDocument().getLength());
			setFocusToTextInput();
		}
		
		if (obs instanceof VisualizationProperties && arg instanceof String){
			//System.out.println("update in cv0");
			String key = (String) arg;
			String value = visualProperties.getProperty(key);
			changePropertyAndUpdateView(key,value);
		}

	}


	/**
	 * Changes the property key in this visualization.
	 * @param key	the property
	 * @param value	the new value
	 */
	private void changeProperty(String key, String value) {
		if (key.equals("size")){
			int size = Integer.parseInt(value);
			changeFontSize(size);

		}
		if (key.equals("fgcolor")){
			Color color = new Color(Integer.parseInt(value));
			changeFontColor(color);
			
		}
		if (key.equals("bgcolor")){
			Color color = new Color(Integer.parseInt(value));
			changeBackgroundColor(color);
		}
	}
	
	/**
	 * Changes the property and updates the visualization immediately.
	 * @param key the property
	 * @param value the new value
	 */
	private void changePropertyAndUpdateView(String key, String value) {
		changeProperty(key,value);
		repaint();
	}

	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#resetSelection()
	 */
	@Override
	public void resetSelection() {
		// no selection reset needed in "Classic Chat"
		
	}

	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#getConfigurationPanel()
	 */
	public JComponent getConfigurationPanel() {
		PropertyContentPane cp = new PropertyContentPane(visualProperties);
		return cp;
	}
	
	/**
	 * Changes the font size.
	 * @param size the new font size
	 */
	private void changeFontSize(int size){
		Font newfont = new Font("MS Sans Serif", Font.PLAIN, size);
		messageArea.setFont(newfont);
	}
	/**
	 * Changes the font color.
	 * @param color the new font color
	 */
	private void changeFontColor(Color color){
		messageArea.setForeground(color);
	}
	
	/**
	 * Changes the background color.
	 * @param color the new background color
	 */
	private void changeBackgroundColor(Color color){
		messageArea.setBackground(color);
	}
	
	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#getProperties()
	 */
	public VisualizationProperties getProperties(){
		return visualProperties;
	}


	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#getContentForRightSlot()
	 */
	public JComponent getContentForRightSlot() {
		// no content for right slot in this visualization.
		return null;
	}


	/**
	 * This method initializes jScrollPane.	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getMessageArea());
		}
		return jScrollPane;
	}
	

}
