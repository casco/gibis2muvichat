package de.feu.cv.ConversationModelP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.feu.cv.ConversationModelP.Graph.Node;


public class ConversationModel { // Clase Padre
	
	// Atributos
	HashMap<String, Node> roots; // cada nodo inicial (root) con su nombre
	HashMap<String, Node> nodes;
	
	
	/**
	 * Devuelve los typos (sus nombres) que pueden actuar como respuesta al que se envia como parametro 
	 * @param referencedType el tipo del mensaje seleccionado...
	 * @return
	 */
	  public List<String> getReplyMessageTypes(String referencedType){
		  
		  return null;
	  }
	  
	  /**
	   * 
	   * @param sourceMessageType
	   * @param destination
	   * @return
	   */
	  public List<String> getReplyRelationTypes(String sourceMessageType, String destinationMessageType){
		  return null;
	  }
	  
	  
	  /**
	   * Message type that can be used to start conversations
	   */
	  public List<String> getRootMTypes(){
		  return new ArrayList<String>(roots.keySet());
	  }
	  

}
