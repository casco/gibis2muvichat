package de.feu.cv.ConversationModelP;

public abstract class IbisType {

	public static IbisType getIbisType(String type){
		IbisType ibistype = null;
        if ("Issue".equals(type)) {
            ibistype = new IbisIssue();
        }
        if ("Position".equals(type)) {
            ibistype = new IbisPosition();
        }
        if ("Argument".equals(type)) {
            ibistype = new IbisArgument();
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
