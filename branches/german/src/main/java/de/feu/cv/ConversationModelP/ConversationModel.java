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
	HashMap<String, HashMap<String, ArrayList<String>>> nodes;
	
	/**
	 * 
	 */
	public ConversationModel(String str) {
		super();
		rootNodes = new ArrayList<String>();
		nodes = new HashMap<String, HashMap<String, ArrayList<String>>>();
		createConversationModelFromString(str);
	}
	
	public void createConversationModelFromString(String str_model) {
		nodes.clear();
		rootNodes.clear();
		String[] str_model_aux = str_model.split("#\n");
		String str_nodes = str_model_aux[0];
		String[] str_arr_nodes = str_nodes.split("\n");
		ArrayList<String> names = new ArrayList<String>();
		for(String str_n : str_arr_nodes) {
			String[] str_n_aux = str_n.split(" ");
			names.add(str_n_aux[1]);
			if (str_n_aux.length==3) {
				rootNodes.add(str_n_aux[1]);
			}
		}
		
		String[] str_arr_relations = str_model_aux[1].split("\n");
		HashMap<String, ArrayList<String>> destinos;
		ArrayList<String> relaciones;
		for(String str_r : str_arr_relations) {
			String[] str_r_aux = str_r.split(" ");		    
		    int destino = Integer.parseInt(str_r_aux[0])-1;
		    int origen = Integer.parseInt(str_r_aux[1])-1;
		    String relacion = str_r_aux[2];
		    if (nodes.containsKey(names.get(origen))) {
		    	destinos = nodes.get(names.get(origen));
		    	if (destinos.containsKey(names.get(destino))) {
		    		relaciones = destinos.get(names.get(destino));
		    		relaciones.add(relacion);
		    	} else {
		    		relaciones = new ArrayList<String>();
		    		relaciones.add(relacion);
		    		destinos.put(names.get(destino), relaciones);
		    	}
		    		
		    } else {
		    	relaciones = new ArrayList<String>();
		    	relaciones.add(relacion);
		    	destinos = new HashMap<String, ArrayList<String>>();
		    	destinos.put(names.get(destino), relaciones);
		    	nodes.put(names.get(origen), destinos);
		    	
		    }
		    	
		}			
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
		  return nodes.get(sourceMessageType).get(destinationMessageType);
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
        //System.out.println("File Data: " + fileData);
        br.close();
        return fileData;
	}




}
