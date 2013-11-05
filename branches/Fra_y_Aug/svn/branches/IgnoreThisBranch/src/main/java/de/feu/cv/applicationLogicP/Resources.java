package de.feu.cv.applicationLogicP;
import java.util.ResourceBundle;

/**
 * Holds the resource bundle to get translations.
 * @author Verena Kunz
 *
 */
public class Resources{

	 /**
	 * The resource bundle for the translations.
	 */
	private static final ResourceBundle resBundle = ResourceBundle.getBundle("muvi");


	 /**
	  * Returns the translated string to a given key.
	 * @param key the key used in the source code
	 * @return the translation
	 */
	 public static final String getString(final String key){
		 
		 String translation = "";
	   	 translation = resBundle.getString(key);
	    
	   return translation;
	 }

	// public static final Enumeration getKeys(){
	//   return resBundle.getKeys();
	// }
	//
	//  public static final Locale getLocale(){
	//    return resBundle.getLocale();
	//  }

} 