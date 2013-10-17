package de.feu.cv.applicationLogicP;

/**
 * Contains system wide constants
 * @author Verena Kunz
 *
 */
/**
 * @author Verena Kunz
 *
 */
public interface MuViChatConstants {
    /** Resource-String for MuVi-Chat-Client. */
    public static final String resource = "muvi";
    
    /** Names of viusalization Plugins. */
    public static final String[] visualizationPlugins = {"classicChat",												    	
    	                                                 "highlightList",
    	                                                 "simpleTree",
    	                                                 "timeByColorTree",
    	                                                 "timeByLayoutTree",
    	                                                 "sequenceByLayoutTree"
    	                                                 };
    /** Directory name for configuration files.*/
    public static final String muviHomeDir =".muvi";
    
    /**
     * Contact string.
     */
    public static final String contact = "Verena.Kunz@fernuni-hagen.de";
    
    /**
     * Version string. 
     */
    public static final String version = "1.0";
    

}
