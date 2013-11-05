package de.feu.cv.applicationLogicP.chatFileP;
import java.io.File;
import java.util.Hashtable;
import java.util.TreeSet;
import javax.swing.table.AbstractTableModel;

import de.feu.cv.applicationLogicP.Resources;



/**
 * This class is the model for the list of the displayed chat protocol files.
 * @author Verena Kunz
 *
 */
public class ChatFileTableModel extends AbstractTableModel{

	/**
	 * The generated serialVersionUID.
	 */
	private static final long serialVersionUID = 2471838505634242587L;
	
	/**
	 * List of open files.
	 */
	private Hashtable<String, ChatFile> filelist;
	
	/**
	 * Creates a new model for the file list.
	 */
	public ChatFileTableModel() {
		super();
		filelist = new Hashtable<String, ChatFile>();
	}


	/** 
	 * One column, may be extended later.
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return 1;
	}


	/** 
	 * One row per file.
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		int rowcount = filelist.size();
		return rowcount;
	}


	/** 
	 * The values for the cells.
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		String content = null;
		// sort the keys
		TreeSet<String> keyset = new TreeSet<String>(filelist.keySet());
		String[] keylist = keyset.toArray(new String[keyset.size()]);
		String filename = keylist[rowIndex];
		if (columnIndex == 0)
			content = filename;

		return content;

	}

	/** 
	 * The column names.
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int columnIndex) {
		String columnname = null;
		if (columnIndex == 0)
			columnname = Resources.getString("ch_filename");
		return columnname;
	}

	/**
	 * Tests, if the given file is in the list.
	 * @param filename
	 * @return <code>true</code> if the file is in the list; <code>false</code>otherwise
	 */
	public boolean containsFile(File filename) {
		return filelist.containsKey(filename.getAbsolutePath());
	}

	/**
	 * Returns the ChatFile with the given filename.
	 * @param filename the name of the file
	 * @return the ChatFile object with the filename
	 */
	public ChatFile get(String filename) {
		return filelist.get(filename);
	}

	/**
	 * Add the file to the list.
	 * @param cf the ChatFile
	 */
	public void put(ChatFile cf) {
		filelist.put(cf.getFile().getAbsolutePath(), cf);
		fireTableDataChanged();
		
	}
	
	/**
	 * Removes the file from the list.
	 * @param cf the ChatFile
	 */
	public void remove(ChatFile cf){
		filelist.remove(cf.getFile().getAbsolutePath());
		fireTableDataChanged();
	}
	
	/**
	 * Activates one file.
	 * @param row the line number of the file
	 */
	public void setActiveChat(int row){
		String filename = (String) getValueAt(row,0);
		ChatFile chatfile = get(filename);
		chatfile.setActiveChat();

	}
}