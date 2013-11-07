package de.feu.cv.ConversationModelP.Graph;

import java.util.ArrayList;

public class Node {

	// Atributos
	private String name;
	private ArrayList<Relation> relations; //transiciones de salida
	
	
	
	public Node(String name) {
		super();
		this.name = name;
		this.relations= new ArrayList<Relation>(); //nodo sin transiciones por defecto
	}
	
	public void addRelation(Relation t){
		relations.add(t);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Relation> getTransitions() {
		return relations;
	}
	public void setTransitions(ArrayList<Relation> transitions) {
		this.relations = transitions;
	}
	
	
}
