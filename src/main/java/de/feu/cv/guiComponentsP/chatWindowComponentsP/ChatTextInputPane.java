package de.feu.cv.guiComponentsP.chatWindowComponentsP;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import de.feu.cv.ConversationModelP.ConversationModel_Interface;
import de.feu.cv.ConversationModelP.ConversationModel;
import de.feu.cv.applicationLogicP.chatRoomP.ChatRoom;
import de.feu.cv.applicationLogicP.conversationP.ThreadedMessage;

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
	
	private class ConversationTypeSelectionActionListener implements ItemListener{		
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				// obtener el nombre del Mtype del combo
				String mType = (String) typesCombo.getSelectedItem();
				ThreadedMessage selection = chatroom.getConversation().getSelection();
				
				if (selection!=null){ // Hay un mensaje seleccionado
					String mType_parent = selection.getMessageType();
					// refrescar lista de relaciones
					updateRelationList(mType, mType_parent);
				}else{// no hay mensaje seleccionado
					relationsCombo.removeAll();
				}
			}			
		}		
		
		private void updateRelationList(String relation_type, String relation_parent_type){
		
			// Obtener todas las posibles relaciones entre un Mtype padre y el Mtype actual
			ConversationModel conversationModel = chatroom.getConversation().getConversationModel();
			List<String> relations = conversationModel.getReplyRelationTypes(relation_parent_type, relation_type);
			
			// Iterar sobre las relaciones y agregarlas al combo		
			relationsCombo.removeAllItems();
			for (int i=0; i < relations.size(); i++){
				relationsCombo.addItem(relations.get(i));
			}
		}
	}
	//{-*-}

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
        
     // inicializaci�n del combo de types
        initializeComboRootsMTypes();
        
        //{-*-}        
        typesCombo.addItemListener(new ConversationTypeSelectionActionListener());
        
        //{-*-}
	}

    @Override
    public void update(Observable o, Object arg) {
        //Something changed in teh conversation of my chatroom (possibly its selection ?
    	ThreadedMessage message = chatroom.getConversation().getSelection(); 
        if (message != null)       {
        	//{-*-}
        	
        	// Pedir al mensaje el Mtype
        	//TODO - cambiar el nombre del m�todo en la clase thrededmessage
        	String parent_type = message.getMessageType();
        	// pedir los posibles mTypes a los que se puede llegar desde el mType actual        	
        	ConversationModel conversationModel = chatroom.getConversation().getConversationModel();
        	List<String> messageTypeStrings = conversationModel.getReplyMessageTypes(parent_type);
        	
        	// Actualizar combo mType
            typesCombo.removeAllItems();
            for (String item : messageTypeStrings) {
              typesCombo.addItem(item);
            }
            typesCombo.setSelectedIndex(0);          
        }else{
        	initializeComboRootsMTypes();
        }



    }

	private void initializeComboRootsMTypes() {
		ConversationModel conversationModel = chatroom.getConversation().getConversationModel();
		List<String> messageTypeStrings = conversationModel.getRootMessageTypes();
    	
    	// Actualizar combo mType
        typesCombo.removeAllItems();
        for (String item : messageTypeStrings) {
          typesCombo.addItem(item);
        }
        typesCombo.setSelectedIndex(0);
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
                           //TODO: Acá me di cuenta de que apretó enter y envía el mensaje.

						    String text = chatTextArea.getText();
						    // remove the \n
						    text = text.replaceAll("\n","");
					    
						    if (!text.equals("")){// dont send empty messages

                                //TODO Acá envía el mensaje!! Armo un diccionario con las propiedades adicionales.
                                HashMap<String, String> properties = buildPropertyMap();

								chatroom.sendThreadedMessage(text, properties);
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
     * creates a hash map with all the properties that should be added to the message before sending it
     *
     * @return a HashMap of propertyName, value pairs (all Strings)
     */
    private HashMap<String, String> buildPropertyMap() {
        //TODO: Aquí se agregan las propiedades relativas al tipo de mensaje y tipo de relación con el padre
        HashMap<String, String> properties = new HashMap<String, String>();
        if (typesCombo.getSelectedItem() != null) {
            properties.put("mType", (String) typesCombo.getSelectedItem());
        }
        if (relationsCombo.getSelectedItem() != null) {
            properties.put("rType", (String) relationsCombo.getSelectedItem());
        }
        return properties;  //To change body of created methods use File | Settings | File Templates.
    }

    /**
	 * Sets the focus to the text input window. 
	 */
	public void setFocusToTextInput(){
		chatTextArea.requestFocus();
	}

}
