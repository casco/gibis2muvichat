package de.feu.cv.guiComponentsP.pluginsP.highlightList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Observable;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import de.feu.cv.applicationLogicP.conversationP.Conversation;
import de.feu.cv.applicationLogicP.conversationP.ThreadedMessage;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties;

import javax.swing.JScrollPane;


/**
 * Visualization implementation "Highlight List".
 * @author Verena Kunz
 *
 */
public class VisualizationPane extends ChatVisualizationPane  {

	private static final long serialVersionUID = 1L;
	private JList messageList = null;
	private VisualizationPaneRenderer cellrenderer;
	private DefaultListModel listmodel;
	private VisualizationProperties visualProperties;
	private JScrollPane jScrollPane = null;


	/**
	 * Creates a new visualization pane. 
	 * @param conversation the displayed conversation
	 */
	public VisualizationPane(Conversation conversation) {
		super(conversation);
		listmodel = (DefaultListModel) conversation.getListModel();
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
		//this.setViewportView(getMessageList());
		this.add(getJScrollPane(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes messageList.	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getMessageList() {
		if (messageList == null) {
			messageList = new JList(listmodel);


			messageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			messageList
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
						public void valueChanged(javax.swing.event.ListSelectionEvent e) {
							setSelection();
	     					setFocusToTextInput();
						}
					});
			cellrenderer = new VisualizationPaneRenderer();
			messageList.setCellRenderer(cellrenderer);
			changeProperty("bgcolor",visualProperties.getProperty("bgcolor"));	
			changeProperty("fgcolor",visualProperties.getProperty("fgcolor"));	
			changeProperty("size",visualProperties.getProperty("size"));	
			changeProperty("bgcolor_selection",visualProperties.getProperty("bgcolor_selection"));	
			changeProperty("fgcolor_selection",visualProperties.getProperty("fgcolor_selection"));	
			//changeProperty("bgcolor_ancestors",visualProperties.getProperty("bgcolor_ancestors"));	
			//changeProperty("fgcolor_ancestors",visualProperties.getProperty("fgcolor_ancestors"));	
			changeProperty("format_ancestors",visualProperties.getProperty("format_ancestors"));	
			//changeProperty("format_selection",visualProperties.getProperty("format_selection"));	

		}
		return messageList;
	}
	/** 
	 * Select a message in the conversation data model.
	 * and highlight its parents in the view
	 */
	public void setSelection(){
		ThreadedMessage tm = (ThreadedMessage)messageList.getSelectedValue();
		conversation.setSelection(tm);
		if (tm != null)
			highlightParents(tm);

	}
	/**
	 * Ensures that the last message ist visible.
	 */
	public void setLastMessageVisible(){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int lastindex = listmodel.getSize()-1;
				messageList.ensureIndexIsVisible(lastindex);
		      }
		 });
	}

	
	/**
	 * Highlight the ancestor messages of the given message.
	 * @param tm the message
	 */
	private void highlightParents(ThreadedMessage tm){
		// update the parents in the cell renderer and repaint
		// message list
		cellrenderer.setParents(conversation.getAncestors(tm));
		Graphics g = messageList.getGraphics();
		if (g != null) // active visualization
			messageList.paint (g);
		
	}

	/**
	 * Displays a message in the visualization.
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable obs, Object arg) {
		if (obs instanceof Conversation && arg instanceof ThreadedMessage){
			ThreadedMessage tm = (ThreadedMessage)arg;

			// scroll down to the last message
			setLastMessageVisible();
			
			// highlight the new message and its parents
			highlightParents(tm);
			
		}
		if (obs instanceof VisualizationProperties && arg instanceof String){
			//System.out.println("update in cv1");
			String key = (String) arg;
			String value = visualProperties.getProperty(key);
			changePropertyAndUpdateView(key,value);
		}

	}

	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#resetSelection()
	 */
	@Override
	public void resetSelection() {
		messageList.clearSelection();
		
	}

	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#getContentForRightSlot()
	 */
	public JComponent getContentForRightSlot() {
		// no content for right slot in this visualization
		return null;
	}

	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#getProperties()
	 */
	public VisualProperties getProperties() {
		return visualProperties;
	}
	
	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#getConfigurationPanel()
	 */
	public JComponent getConfigurationPanel() {
		PropertyContentPane cp = new PropertyContentPane(visualProperties);
		return cp;
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
		if (key.equals("bgcolor_selection")){
			Color color = new Color(Integer.parseInt(value));
			changeBackgroundColorSelection(color);
		}
		if (key.equals("fgcolor_selection")){
			Color color = new Color(Integer.parseInt(value));
			changeFontColorSelection(color);
		}

		if (key.equals("format_ancestors")){
			changeFormatAncestors(Integer.parseInt(value));
			
		}
		
		
		
	}

	/**
	 * Changes the format of the ancestors.
	 * @param format the new format: 0-plain, 1-bold, 2-italic
	 */
	private void changeFormatAncestors(int format) {
		int size = Integer.parseInt(visualProperties.getProperty("size"));
		cellrenderer.setAncestorsFont(new Font("MS Sans Serif", format, size));
		
	}

	/**
	 * Changes the font color for selected elements.
	 * @param color the new font color
	 */
	private void changeFontColorSelection(Color color) {
		cellrenderer.setSelectionFontColor(color);
		
	}

	/**
	 * Changes the background color for selected elements.
	 * @param color the new background color
	 */
	private void changeBackgroundColorSelection(Color color) {
		cellrenderer.setSelectionBgColor(color);
		
	}

	/**
	 * Changes the background color for normal elements.
	 * @param color the new background color
	 */
	private void changeBackgroundColor(Color color) {
		messageList.setBackground(color);

		
	}

	/**
	 * Changes the font color for normal elements.
	 * @param color the new font color
	 */
	private void changeFontColor(Color color) {
		messageList.setForeground(color);
		
	}

	/**
	 * Changes the font size.
	 * @param size the new font size
	 */
	private void changeFontSize(int size) {
		Font newfont = new Font("MS Sans Serif", Font.PLAIN, size);
		messageList.setFont(newfont);
		if (cellrenderer != null){
			cellrenderer.setStandardFont(newfont);
			int format_ancestors = Integer.parseInt(visualProperties.getProperty("format_ancestors"));
		    Font ancestorsfont = new Font("MS Sans Serif",format_ancestors, size);
		    cellrenderer.setAncestorsFont(ancestorsfont);

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
	 * This method initializes jScrollPane.	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getMessageList());
		}
		return jScrollPane;
	}

}
