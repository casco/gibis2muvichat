package de.feu.cv.ConversationModelP.Graph;

public class Relation {
	
	// Atributos
	private String name;
	private Node to;
	
	
	// Constructor
	public Relation(String name, Node to) {
		super();
		this.name = name;
		this.to = to;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Node getTo() {
		return to;
	}
	public void setTo(Node to) {
		this.to = to;
	}
	
}
