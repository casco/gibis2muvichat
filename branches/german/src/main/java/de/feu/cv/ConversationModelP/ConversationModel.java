package de.feu.cv.ConversationModelP;

import java.util.List;

public interface ConversationModel {
	
	/**
	 * Devuelve los typos (sus nombres) que pueden actuar como respuesta al que se envia como parametro 
	 * @param referencedType el tipo del mensaje seleccionado...
	 * @return
	 */
	  public List<String> messageTypes(String referencedType);
	  
	  /**
	   * 
	   * @param sourceMessageType
	   * @param destination
	   * @return
	   */
	  public List<String> relationTypes(String sourceMessageType, String destinationMessageType);
	  
	  
	  /**
	   * Message type that can be used to start conversations
	   */
	  public List<String> rootTypes();
	  

}
