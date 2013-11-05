package de.feu.cv.ConversationModelP;

import java.util.List;

public class IbisConversationModel implements ConversationModel {

	/**
	 * Devuelve los typos (sus nombres) que pueden actuar como respuesta al que se envia como parametro 
	 * @param referencedType el tipo del mensaje seleccionado...
	 * @return
	 */
	public List<String> messageTypes(String referencedType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> relationTypes(String sourceMessageType,
			String destinationMessageType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> rootTypes() {
		// TODO Auto-generated method stub
		return null;
	}

}
