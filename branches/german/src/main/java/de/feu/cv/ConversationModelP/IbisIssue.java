package de.feu.cv.ConversationModelP;

public class IbisIssue extends IbisType{
	
	public String toString() {		
		return "Issue";
	}

	@Override
	public String[] getResponseTypes() {
		String[] types = {"Issue", "Position"};
		return types;
	}

	@Override
	public String[] getRelations(IbisType type) {		
		return type.getRelationsToIssue();
	}

	public String[] getRelationsToIssue(){
		String[] relations = {"Generalizes", "Specializes", "Replaces",
				"Questions", "Is-suggested-by"};
		return relations;
	}
	
	public String[] getRelationsToPosition(){
		String[] relations = {"Questions", "Is-suggested-by"};
		return relations;
	}
	
	public String[] getRelationsToArgument(){
		String[] relations = {"Questions", "Is-suggested-by"};
		return relations;
	}
	
}
