package de.feu.cv.ConversationModelP;

import java.util.List;


public interface ConversationModel_Interface {
	
	
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
     * Clears all nodes and edges in the receiver. Recreates itself from
     * the specification the tgfString; a String that specifies a graph
     * using TGF notation: http://en.wikipedia.org/wiki/Trivial_Graph_Format
     * @param tgfString
     */
      public void reconfigureFromTGF(String tgfString);
	  

}
