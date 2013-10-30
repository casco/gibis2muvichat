package de.feu.cv.ibisP;

public class IbisPosition extends IbisType{

	public String toString() {
		return "Position";
	}

	@Override
	public String[] getResponseTypes() {
		String[] types = {"Issue", "Argument"};
		return types;
	}

	@Override
	public String[] getRelations(IbisType type) {
		return type.getRelationsToPosition();
	}
		
	public String[] getRelationsToIssue(){
		String[] relations = {"Responds-to"};
		return relations;
	}	
	
}
