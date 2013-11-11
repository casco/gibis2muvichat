/**
 * 
 */
package de.feu.cv.ConversationModelP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class IbisConversationModel implements ConversationModel {

	ArrayList<String> rootNodes;
	HashMap<String, HashMap<String, String[]>> nodes;
	
	/**
	 * 
	 */
	public IbisConversationModel(String XML) {
		super();
		rootNodes = new ArrayList<String>();
		nodes = new HashMap<String, HashMap<String, String[]>>();
		createConversationModelFromXML(XML);
	}
	
	void createConversationModelFromXML(String XML) {
		/*<conversationModel name="IBIS">
		 *   <node name="Issue" isRoot=true>
		 *      <relation to="Issue">
		 *         <type name="generalizes"/>
		 *         ...
		 *      </relation>
		 *      <relation to="Position">
		 *         ...
		 *      </relation>
		 *   </node>
		 *   <node name="Position" isRoot=false>
		 *      ...
		 *   </node>
		 *   <node name="Argument" isRoot=false>
		 *      ...
		 *   </node>
		  </conversationModel>
		*/

		HashMap<String, String[]> relationsFromIssue = new HashMap<String, String[]>();
		String[] typesFromIssueToIssue = {"Generalizes", "Specializes", "Replaces", "Questions", "Is-suggested-by"};
		relationsFromIssue.put("Issue", typesFromIssueToIssue);
		String[] typesFromIssueToPosition = {"Questions", "Is-suggested-by"};
		relationsFromIssue.put("Position", typesFromIssueToPosition);
		String[] typesFromIssueToArgument = {"Questions", "Is-suggested-by"};
		relationsFromIssue.put("Argument", typesFromIssueToArgument);
		nodes.put("Issue", relationsFromIssue);
		rootNodes.add("Issue");
		
		HashMap<String, String[]> relationsFromPosition = new HashMap<String, String[]>();
		String[] typesFromPositionToIssue = {"Responds-to"};
		relationsFromPosition.put("Issue", typesFromPositionToIssue);
		nodes.put("Position", relationsFromPosition);
		
		HashMap<String, String[]> relationsFromArgument = new HashMap<String, String[]>();
		String[] typesFromArgumentToPosition = {"Supports", "Objects-to"};
		relationsFromArgument.put("Position", typesFromArgumentToPosition);
		nodes.put("Argument", relationsFromArgument);		
			
	}
	
	/**
	 * Devuelve los typos (sus nombres) que pueden actuar como respuesta al que se envia como parametro 
	 * @param referencedType el tipo del mensaje seleccionado...
	 * @return
	 */
	  public List<String> getReplyMessageTypes(String referencedType){
		  return new ArrayList<String>(nodes.get(referencedType).keySet());
	  }
	  
	  /**
	   * 
	   * @param sourceMessageType
	   * @param destination
	   * @return
	   */
	  public List<String> getReplyRelationTypes(String sourceMessageType, String destinationMessageType){
		  String[] relations = nodes.get(sourceMessageType).get(destinationMessageType);
		  return new ArrayList<String>(Arrays.asList(relations));
	  }
	  
	  
	    
	  /**
	   * Message type that can be used to start conversations
	   */
	  public List<String> getRootMessageTypes(){
		  return rootNodes;
	  }

	@Override
	public void reconfigureFromTGF(String tgfString) {
		// TODO Auto-generated method stub
		
	}




}
