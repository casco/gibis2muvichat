package de.feu.cv.guiComponentsP.chatWindowComponentsP;

import javax.swing.*;

import de.feu.cv.ConversationModelP.IbisConversationModel;
import de.feu.cv.ConversationModelP.IbisType;
import de.feu.cv.applicationLogicP.chatRoomP.ChatRoom;
import de.feu.cv.applicationLogicP.conversationP.ThreadedMessage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    
    // Conversation Model
    private IbisConversationModel conversationModel;


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
				String mType_parent = selection.getIbis_type();
				// refrescar lista de relaciones
				updateRelationList(mType, mType_parent);
			}			
		}		
		
		private void updateRelationList(String relation_type, String relation_parent_type){
			// Obtener todas las posibles relaciones entre un Mtype padre y el Mtype actual
			List<String> relations = conversationModel.getReplyRelationTypes(relation_parent_type, relation_type);
			String[] r= (String[]) relations.toArray(); // convierte en Array de strings
			// Iterar sobre als relaciones y agregarlas al combo		
			relationsCombo.removeAllItems();
			for (int i=0; i < r.length; i++){
				relationsCombo.addItem(r[i]);
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
        
        // Conversation Model
        //TODO cambiar NULL por un archivo XML real. Habr�a que desharcodear IBIS
        this.conversationModel= new IbisConversationModel("NULL");
        //{-*-}        
        typesCombo.addItemListener(new ConversationTypeSelectionActionListener());
        
        //{-*-}
	}

/*	//{-*-}  m�todo de Germ�n --------------------------------------------------
	private String[] getAvailableIbisMessageTypes(String parent_type){		
		IbisType type = IbisType.getIbisType(parent_type);
		return type.getResponseTypes();
	}
*/
	
	private String[] getAvailableIbisRelationTypes(String parent_type){		
		IbisType type = IbisType.getIbisType(parent_type);
		String[] responses = type.getResponseTypes();
		return type.getRelations(IbisType.getIbisType(responses[0]));
	}
	//{-*-}
	

    @Override
    public void update(Observable o, Object arg) {
        //Something changed in teh conversation of my chatroom (possibly its selection ?
    	ThreadedMessage message = chatroom.getConversation().getSelection(); 
        if (message != null)       {
        	//{-*-}
        		
            //TODO: Acá hay que armar la lista para los combo box con las cosas que corresponde
            //String[] messageTypeStrings = {"Message is...", "Issue", "Position", "Argument" };
        	
        	// Pedir al mensaje el Mtype
        	//TODO - cambiar el nombre del m�todo en la clase thrededmessage
        	String parent_type = message.getIbis_type();
        	// pedir los posibles mTypes a los que se puede llegar desde el mType actual
        	// c�digo de Germ�n --> String[] messageTypeStrings = getAvailableIbisMessageTypes(parent_type);
        	List<String> messageTypeStrings_list = conversationModel.getReplyMessageTypes(parent_type);
        	String[] messageTypeStrings= (String[]) messageTypeStrings_list.toArray(); // convertir a Array
        	// Actualizar combo mType
            typesCombo.removeAllItems();
            for (String item : messageTypeStrings) {
              typesCombo.addItem(item);
            }
            typesCombo.setSelectedIndex(0);


//            String[] relationTypeStrings = {"Relation ...", "generalizes", "specializes", "questions", "is suggested by",
//                    "responds to", "supports", "objects to", "replaces" };
            String[] relationTypeStrings = getAvailableIbisRelationTypes(parent_type);
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
            properties.put("ibis-type", (String) typesCombo.getSelectedItem());
        }
        if (relationsCombo.getSelectedItem() != null) {
            properties.put("ibis-relation", (String) relationsCombo.getSelectedItem());
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
