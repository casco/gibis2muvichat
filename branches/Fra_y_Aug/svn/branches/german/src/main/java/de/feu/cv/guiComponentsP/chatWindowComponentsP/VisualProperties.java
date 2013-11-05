package de.feu.cv.guiComponentsP.chatWindowComponentsP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Properties;

import de.feu.cv.applicationLogicP.ChatAdministration;

/**
 * Abstract class for the visual properties of a visualization.
 * @author Verena Kunz
 *
 */
public abstract class VisualProperties extends Observable{
	/**
	 * The properties object.
	 */
	private Properties props;
	/**
	 * The filename where the properties are saved.
	 */
	protected String filename;


	/**
	 * The default contructor.
	 */
	public VisualProperties() {
		props = new Properties();
		setDefaultValues();
		setFile();
		loadVisualProperties();
	}

	/**
	 * Sets a property and notifies the observers.
	 * @param key the key of the property
	 * @param value the value of the property
	 */
	public void setProperty (String key, String value){
		props.setProperty(key, value);
		setChanged();
		notifyObservers(key);
		
	}
	
	/**
	 * Returns the value of a property.
	 * @param key the key
	 * @return the value
	 */
	public String getProperty (String key){
		return props.getProperty(key);
	}

	/**
	 * Sets new properties.
	 * @param p the properties to set
	 */
	public void setProperties(Properties p) {
		this.props = p;
		
	}
	/**
	 * Returns the actual properties.
	 * @return the properties
	 */
	public Properties getProperties() {
		return props;
		
	}
	
	/**
	 * Loads the visual properties from file.
	 */
	public void loadVisualProperties(){
		
		File abs_file = new File(ChatAdministration.getInstance().getHomeDir(),filename);
		try {
		    FileInputStream propInFile = new FileInputStream(abs_file);
		    Properties p = new Properties();
		    p.load( propInFile );
		    props = p;

		    
		     }
		     catch ( FileNotFoundException e ) {
		     
		     }
		     catch ( IOException e ) {
		     
	     }   
	}
	
	/**
	 * Saves visual properties to file.
	 */
	public void saveVisualProperties(){
		File abs_file = new File(ChatAdministration.getInstance().getHomeDir(),filename);
		
		try {
			FileOutputStream propOutFile = new FileOutputStream( abs_file );
			try {
				props.store(propOutFile, "Visualization parameters");
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (FileNotFoundException e2) {

			e2.printStackTrace();
		}
	}


	/**
	 * Sets the visual properties to the default values.
	 */
	public abstract void setDefaultValues();
	
	/**
	 * Sets the name of the file where the properties are saved. 
	 */
	public abstract void setFile();
	

}