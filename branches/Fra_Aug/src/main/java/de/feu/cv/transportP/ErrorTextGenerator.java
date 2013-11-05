package de.feu.cv.transportP;

import java.util.MissingResourceException;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.XMPPError;

import de.feu.cv.applicationLogicP.Resources;

/**
 * Generates error texts.
 * @author Verena Kunz
 *
 */
public class ErrorTextGenerator {
	
	/**
	 * Generates error texts for a XMPPException.
	 * @param e the XMPPExceptions
	 * @return the error text
	 */
	public static String getErrorText(XMPPException e){
		
		String description="";
		String messagetext="";
		String errorcodetext="";
		XMPPError error = e.getXMPPError();
		
		if (error!=null) {
			int code = error.getCode();
			switch ( code )
			{
			  case 302:
				  description = "Redirect";
				  break;
			  case 400:
				  description = "Bad Request";
				  break;
			  case 401:
				  description = "Unauthorized";
				  break;
			  case 402:
				  description = "Payment Required";
				  break;
			  case 404:
				  description = "Not Found";
				  break;
			  case 405:
				  description = "Not Allowed";
				  break;
			  case 406:
				  description = "Not Acceptable";
				  break;
			  case 407:
				  description = "Registration Required";
				  break;
			  case 408:
				  description = "Request Timeout";
				  break;
			  case 409:
				  description = "Conflict";
				  break;
			  case 500:
				  description = "Internal Server XMPPError";
				  break;
			  case 501:
				  description = "Not Implemented";
				  break;
			  case 502:
				  description = "Remote Server Error";
				  break;
			  case 503:
				  description = "Service Unavailable";
				  break;
			  case 504:
				  description = "Remote Server Timeout";
				  break;			  				    
			  default:
				  description = "Error";
			}
			errorcodetext =  " (Errorcode: "+code+")";
		}
		else {
			description = e.getMessage();

		}
		// check if there is a translation for the error
		// space characters are replaced with underlines
		try {
			description = Resources.getString(description.replaceAll(" ", "_"));
		}
		catch (MissingResourceException mre){
		}
		
		// for XMPPError append errorcode with number to the messagetext
		messagetext = description + errorcodetext;
		
		return messagetext;
		
	}

}
