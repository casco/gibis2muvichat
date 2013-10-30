package de.feu.cv.ibisP;

public abstract class IbisType {

	public static IbisType getIbisType(String type){
		IbisType ibistype = null;
		switch (type) {
			case "Issue": ibistype = new IbisIssue(); break;
			case "Position": ibistype = new IbisPosition(); break;
			case "Argument": ibistype = new IbisArgument(); break;
		}
		return ibistype;
	}	
	
	public abstract String[] getResponseTypes();
	public abstract String[] getRelations(IbisType type);
	
	public String[] getRelationsToIssue(){
		String[] relations = {};
		return relations;
	}
	
	public String[] getRelationsToPosition(){
		String[] relations = {};
		return relations;
	}
	
	public String[] getRelationsToArgument(){
		String[] relations = {};
		return relations;
	}
	
}
