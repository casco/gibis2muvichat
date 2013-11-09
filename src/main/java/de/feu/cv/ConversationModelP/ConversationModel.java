package de.feu.cv.ConversationModelP;

import java.util.List;


public interface ConversationModel {
	
	
	/**
	 * Devuelve los typos (sus nombres) que pueden actuar como respuesta al que se envia como parametro 
	 * @param referencedType el tipo del mensaje seleccionado...
	 * @return
	 */
	  public List<String> getReplyMessageTypes(String referencedType);
	  
	  /**
	   * 
	   * @param sourceMessageType
	   * @param destinationMessageType
	   * @return
	   */
	  public List<String> getReplyRelationTypes(String sourceMessageType, String destinationMessageType);
	  
	  
	  /**
	   * Message type that can be used to start conversations
	   */
	  public List<String> getRootMessageTypes();

    /**
     * Clears all nodes and edges in teh receiver. Recreates itself from
     * the specification the dotString; a String that specifies a graph
     * using DOT notation: http://en.wikipedia.org/wiki/DOT_(graph_description_language)
     * @param dotString
     */
      public void reconfigureFromDOT(String dotString);
	  

}
