package de.feu.cv.ibisP;

public class IbisArgument extends IbisType {

	public String toString() {
		return "Argument";
	}

	@Override
	public String[] getResponseTypes() {
		String[] types = {"Issue"};
		return types;
	}

	@Override
	public String[] getRelations(IbisType type) {
		return type.getRelationsToArgument();
	}
	
	public String[] getRelationsToPosition(){
		String[] relations = {"Supports", "Objects-to"};
		return relations;
	}	

}
