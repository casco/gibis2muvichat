package de.feu.cv.ConversationModelP.Graph;

import java.util.ArrayList;

public class Node {

	// Atributos
	private String name;
	private ArrayList<Transition> transitions; //transiciones de salida
	
	
	
	public Node(String name) {
		super();
		this.name = name;
		this.transitions= new ArrayList<Transition>(); //nodo sin transiciones por defecto
	}
	
	public void addTransition(Transition t){
		transitions.add(t);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Transition> getTransitions() {
		return transitions;
	}
	public void setTransitions(ArrayList<Transition> transitions) {
		this.transitions = transitions;
	}
	
	
}
