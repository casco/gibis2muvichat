package de.feu.cv.ConversationModelP.Graph;

import java.util.ArrayList;

public class Node {

	// Atributos
	private String name;
	private ArrayList<Relation> transitions; //transiciones de salida
	
	
	
	public Node(String name) {
		super();
		this.name = name;
		this.transitions= new ArrayList<Relation>(); //nodo sin transiciones por defecto
	}
	
	public void addTransition(Relation t){
		transitions.add(t);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Relation> getTransitions() {
		return transitions;
	}
	public void setTransitions(ArrayList<Relation> transitions) {
		this.transitions = transitions;
	}
	
	
}
