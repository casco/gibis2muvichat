package de.feu.cv.applicationLogicP.conversationP;

import java.io.Serializable;
import java.util.Iterator;

import prefuse.data.Table;
import prefuse.data.Tree;
import prefuse.data.tuple.TableNode;

/**
 * Subclass of prefuse Tree class to save chat messages.
 * Model of prefuse based visualizations.
 * @author Verena Kunz
 *
 */
public class MessageTree extends Tree implements Serializable, Cloneable, ChatDataModel {


	private static final long serialVersionUID = 3901716652653542953L;

    //TODO add support for additional custom properties (not only parent_nick and parent_id)

	/**
	 * Creates a new message tree.
	 */
	public MessageTree() {
		super();
		
		Table nodestable = getNodeTable();
		Table edgestable = getEdgeTable();
		
		//standard columns for edges
        edgestable.addColumn("parent", int.class, new Integer(-1));
        edgestable.addColumn("child", int.class, new Integer(-1));
		
        //columns for nodes
		nodestable.addColumn("nick", String.class);
		nodestable.addColumn("text", String.class);
		nodestable.addColumn("date", long.class);
		nodestable.addColumn("id", String.class);
		nodestable.addColumn("parent_nick", String.class);
		nodestable.addColumn("parent_id", String.class);
		//{-*-}
		nodestable.addColumn("ibis-type", String.class);
		nodestable.addColumn("ibis-relation", String.class);
		//{-*-}
		createUnvisibleRoot();

	}

	/**
	 * Adds a message to the model.
	 * @param message the new message
	 */
	public void addMessage(ThreadedMessage message) {
		TableNode parentnode = getParentNode(message);
		TableNode newnode;
		if (parentnode == null){ 
			// add a new child node to root
			newnode = (TableNode) addChild(getRoot());
		}
		else{
			// add a new child node to the parent node
			newnode = (TableNode) addChild(parentnode);
		}
		
		// set the values
		newnode.setString("id", message.getID());
        newnode.setString("nick", message.getNick());
        newnode.setString("text",message.getText());
        newnode.setLong("date",message.getDate().getTime());
        newnode.setString("parent_nick", message.getParent_nick());
        newnode.setString("parent_id", message.getParent_id());
        //{-*-}
        newnode.setString("ibis-type", message.getIbis_type());
        newnode.setString("ibis-relation", message.getIbis_relation());
      	//{-*-}
		
        
	}
	
	/**
	 * Returns the parent TableNode of the ThreadedMessage.
	 * @param tm the ThreadedMessage
	 * @return the parent TableNode
	 */
	public TableNode getParentNode(ThreadedMessage tm){
		TableNode parentnode = null;
		String id = tm.getParent_id();
		String nick = tm.getParent_nick();
		for (Iterator i = nodes(); i.hasNext();){
			TableNode node = (TableNode)i.next();
			
			if (node.canGetString("nick") && node.canGetString("id")){
				if (node.getString("nick").equals(nick) && node.getString("id").equals(id))
					parentnode = node;
			}

		}
		return parentnode;
		
	}

	/**
	 * Deletes all nodes from tree.
	 * (except unvisible root)
	 */
	public void reset() {
		
		int root_row = getRoot().getRow();
		while (getChildCount(root_row) > 0){
			removeChild(getLastChildRow(root_row));
			
		}
		

	}
	
	/**
	 * Creates the root node.
	 */
	private void createUnvisibleRoot() {
    	TableNode root = (TableNode)addRoot();
     	root.setString("text","wurzel");
     	root.setString("id","wurzel");
     	root.setString("nick","wurzel");
	}

//	public MessageTree clone(){
//		try {
//			return (MessageTree) super.clone();
//		} catch (CloneNotSupportedException e) {

//			e.printStackTrace();
//			return null;
//		}
//	}
	

}
