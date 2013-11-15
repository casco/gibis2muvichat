/**
 * 
 */
package de.feu.cv.ConversationModelP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ConversationModel implements ConversationModel_Interface {

	ArrayList<String> rootNodes;
	HashMap<String, HashMap<String, String[]>> nodes;
	
	/**
	 * 
	 */
	public ConversationModel(String str) {
		super();
		rootNodes = new ArrayList<String>();
		nodes = new HashMap<String, HashMap<String, String[]>>();
		createConversationModelFromString(str);
	}
	
	public void createConversationModelFromString(String str_model) {
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
		  return Arrays.asList(relations);
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
	
	public static String fileToString(File file) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		//generar un string con todo el archivo
		StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append('\n');
            line = br.readLine();
        }
        // string completo del archivo
        String fileData = sb.toString();
        System.out.println("File Data: " + fileData);
        br.close();
        return fileData;
	}




}
