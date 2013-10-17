package de.feu.cv.guiComponentsP.chatWindowComponentsP;

import javax.swing.*;

import de.feu.cv.applicationLogicP.chatRoomP.ChatRoom;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Container with text input field.
 * 
 * @author Verena Kunz
 *
 */
public class ChatTextInputPane extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private JTextArea chatTextArea = null;

    private JComboBox<String> typesCombo = null;
    private JComboBox<String> relationsCombo = null;


	/**
	 * The ChatRoom where the input goes.
	 */
	private ChatRoom chatroom;


    /**
	 * Creates an new TextInputPane connected with the chatroom.
	 * @param chatroom the chatroom the text input is send to
	 */
	public ChatTextInputPane(ChatRoom chatroom) {
		super();
		this.chatroom = chatroom;
        chatroom.getConversation().addObserver(this);
		initialize();
		
	}

	/**
	 * This method initializes this.
	 */
	private void initialize() {
		//this.setSize(300, 200);
		//this.setViewportView(getChatTextArea());
        JPanel combosPanel = new JPanel(new BorderLayout());
        this.setLayout(new BorderLayout());
        typesCombo = new JComboBox<String>();
        relationsCombo = new JComboBox<String>();
        combosPanel.add(typesCombo, BorderLayout.NORTH);
        combosPanel.add(relationsCombo, BorderLayout.SOUTH);
        this.add(getChatTextArea(), BorderLayout.CENTER);
        this.add(combosPanel, BorderLayout.EAST);
	}


    @Override
    public void update(Observable o, Object arg) {
        //Something changed in teh conversation of my chatroom (possibly its selection ?
        if (chatroom.getConversation().getSelection() != null)       {

            //TODO: Acá hay que armar la lista para los combo box con las cosas que corresponde
            String[] messageTypeStrings = {"Message is...", "Issue", "Position", "Argument" };

            typesCombo.removeAllItems();
            for (String item : messageTypeStrings) {
              typesCombo.addItem(item);
            }
            typesCombo.setSelectedIndex(0);


            String[] relationTypeStrings = {"Relation ...", "generalizes", "specializes", "questions", "is suggested by",
                    "responds to", "supports", "objects to", "replaces" };

            relationsCombo.removeAllItems();
            for (String item : relationTypeStrings) {
                relationsCombo.addItem(item);
            }
            relationsCombo.setSelectedIndex(0);
        }



    }

	/**
	 * This method initializes chatTextArea.
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getChatTextArea() {
		if (chatTextArea == null) {
			chatTextArea = new JTextArea();
			chatTextArea.setLineWrap(true);
			chatTextArea.setRows(2);
			chatTextArea.setWrapStyleWord(true);
			chatTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					
					   if(e.getKeyChar()=='\n') { // RETURN/ENTER
                           //TODO: Acá me di cuenta de que apretó enter y envia el mensaje.

						    String text = chatTextArea.getText();
						    // remove the \n
						    text = text.replaceAll("\n","");
					    
						    if (!text.equals("")){// dont send empty messages
                                //TODO Acá envía el mensaje!! Probablemente tenga que enviar mas parametros.
								chatroom.sendThreadedMessage(text);
						    }
						    // clear the input field
							chatTextArea.setText("");

                           //TODO: Luego de enviar el mensaje, vacío el combo box.
                            typesCombo.removeAllItems();
                           relationsCombo.removeAllItems();

					   } 
				}
			});
		}
		// set focus to textinput after window creation
		SwingUtilities.invokeLater(new Runnable() {
		    public void run()
		    {
		    	setFocusToTextInput();
		    }

		}); 
	
		return chatTextArea;
	}
	/**
	 * Sets the focus to the text input window. 
	 */
	public void setFocusToTextInput(){
		chatTextArea.requestFocus();
	}

}
