package de.feu.cv.guiComponentsP.pluginsP.sequenceByLayoutTree;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import prefuse.Constants;
import prefuse.Display;
import prefuse.action.layout.graph.TreeLayout;
import prefuse.data.Graph;
import prefuse.data.Schema;
import prefuse.data.tuple.TupleSet;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualTable;
import de.feu.cv.guiComponentsP.prefuseP.actionsP.OrientationTreeLayout;

/**
 * <p>TreeLayout that computes a layout of a node-link tree
 * diagram and uses the "time" field of the node to strech the diagram
 * The orientation of the
 * tree can be set such that the tree goes left-to-right (default),
 * right-to-left, top-to-bottom, or bottom-to-top.</p>
 * 
 * SequenzialTreeLayout is based on NodeLinkTreeLayout from jeffrey heer,
 * but is much more simple 
 * 
 * @author Verena Kunz
 */
public class SequenzialTreeLayout extends TreeLayout implements OrientationTreeLayout{
    
    private int    m_orientation;  // the orientation of the tree

    private double m_offset = 50;  // pixel offset for root node position
    private double m_distance = 70;
    private double m_dspace = 5;
    private double m_ax, m_ay; // for holding anchor co-ordinates
    
	private double nextfreexpos = 0;

	/**
     * Create a new SequenzialTreeLayout. A left-to-right orientation is assumed.
     * @param group the data group to layout. Must resolve to a Graph instance.
     */
    public SequenzialTreeLayout(String group) {
        super(group);
        m_orientation = Constants.ORIENT_LEFT_RIGHT;
    }
    
    /**
     * Create a new SequenzialTreeLayout.
     * @param group the data group to layout. Must resolve to a Graph instance.
     * @param orientation the orientation of the tree layout. One of
     * {@link prefuse.Constants#ORIENT_LEFT_RIGHT},
     * {@link prefuse.Constants#ORIENT_RIGHT_LEFT},
     * {@link prefuse.Constants#ORIENT_TOP_BOTTOM}, or
     * {@link prefuse.Constants#ORIENT_BOTTOM_TOP}.
     */
    public SequenzialTreeLayout(String group, int orientation,
            double distance)
    {
        super(group);
        m_orientation = orientation;
        m_distance = distance;

    }
    
    // ------------------------------------------------------------------------
    
    /**
     * Set the orientation of the tree layout.
     * @param orientation the orientation value. One of
     * {@link prefuse.Constants#ORIENT_LEFT_RIGHT},
     * {@link prefuse.Constants#ORIENT_RIGHT_LEFT},
     * {@link prefuse.Constants#ORIENT_TOP_BOTTOM}, or
     * {@link prefuse.Constants#ORIENT_BOTTOM_TOP}.
     */
    public void setOrientation(int orientation) {
        if ( orientation < 0 || 
             orientation >= Constants.ORIENTATION_COUNT ||
             orientation == Constants.ORIENT_CENTER )
        {
            throw new IllegalArgumentException(
                "Unsupported orientation value: "+orientation);
        }
        m_orientation = orientation;
    }
    
    /**
     * Get the orientation of the tree layout.
     * @return the orientation value. One of
     * {@link prefuse.Constants#ORIENT_LEFT_RIGHT},
     * {@link prefuse.Constants#ORIENT_RIGHT_LEFT},
     * {@link prefuse.Constants#ORIENT_TOP_BOTTOM}, or
     * {@link prefuse.Constants#ORIENT_BOTTOM_TOP}.
     */
    public int getOrientation() {
        return m_orientation;
    }
    
    
    /**
     * Set the offset value for placing the root node of the tree. The
     * dimension in which this offset is applied is dependent upon the
     * orientation of the tree. For example, in a left-to-right orientation,
     * the offset will a horizontal offset from the left edge of the layout
     * bounds.
     * @param o the value by which to offset the root node of the tree
     */
    public void setRootNodeOffset(double o) {
        m_offset = o;
    }
    
    /**
     * Get the offset value for placing the root node of the tree.
     * @return the value by which the root node of the tree is offset
     */
    public double getRootNodeOffset() {
        return m_offset;
    }
    
    
    /**
     * @see prefuse.action.layout.Layout#getLayoutAnchor()
     */
    public Point2D getLayoutAnchor() {
        if ( m_anchor != null )
            return m_anchor;
        
        m_tmpa.setLocation(0,0);
        if ( m_vis != null ) {
            Display d = m_vis.getDisplay(0);
            Rectangle2D b = this.getLayoutBounds();
            switch ( m_orientation ) {
            case Constants.ORIENT_LEFT_RIGHT:
                m_tmpa.setLocation(m_offset, d.getHeight()/2.0);
                break;
            case Constants.ORIENT_RIGHT_LEFT:
                m_tmpa.setLocation(b.getMaxX()-m_offset, d.getHeight()/2.0);
                break;
            case Constants.ORIENT_TOP_BOTTOM:
                m_tmpa.setLocation(d.getWidth()/2.0, m_offset);
                break;
            case Constants.ORIENT_BOTTOM_TOP:
                m_tmpa.setLocation(d.getWidth()/2.0, b.getMaxY()-m_offset);
                break;
            }
            d.getInverseTransform().transform(m_tmpa, m_tmpa);
        }
        return m_tmpa;
    }

 
    /**
     * @see prefuse.action.Action#run(double)
     */
    public void run(double frac) {
        Graph g = (Graph)m_vis.getGroup(m_group);
        initSchema(g.getNodes());     
                
        //set layout anchor
        Point2D a = getLayoutAnchor();
        m_ax = a.getX();
        m_ay = a.getY();
        
        NodeItem root = getLayoutRoot();
        

        
        // do first pass - calculate x-positions
        firstWalk(root);
        
        // do second pass - assign layout positions
        secondWalk(root, null);
        
        // reset counter
        nextfreexpos = 0;
    }

    /**
     * Sets the x-posistion recursivly
     * @param n the node
     */
    private void firstWalk(NodeItem n) {

        Params np = getParams(n);
        
        boolean expanded = n.isExpanded();
        if ( n.getChildCount() == 0 || !expanded ) // is leaf
        { 
            np.xpos = giveNextFreeXPos();  
        }
        else if ( expanded )
        {
            NodeItem leftMost = (NodeItem)n.getFirstChild();
            NodeItem rightMost = (NodeItem)n.getLastChild();
            NodeItem c = leftMost;
            for ( int i=0; c != null; ++i, c = (NodeItem)c.getNextSibling() )
            {
                firstWalk(c);
            }
            // parent in the middle of children
            np.xpos = 0.5 * (getParams(leftMost).xpos + getParams(rightMost).xpos);
        }
        //System.out.println("Parameter f�r: "+ n.getString("text"));
        //System.out.println("xpos: "+ np.xpos);
    }

    
    /**
     * Sets the y posistion
     * @param n the node
     * @param p the parent node
     */
    private void secondWalk(NodeItem n, NodeItem p) {
        Params np = getParams(n);
        
        // set x-position, first shift to place the anchor in the middle
        //System.out.println(n.getString("text")+":" +np.xpos);
   
        double shifted_xpos = np.xpos - ((nextfreexpos - m_offset)/2);
        setBreadth(n, p, shifted_xpos);
        
        // set y-position
        // rowindex contains the sequence number of the item
        int rowindex = n.getRow();
        long ypos;
        boolean v = ( m_orientation == Constants.ORIENT_TOP_BOTTOM ||
                m_orientation == Constants.ORIENT_BOTTOM_TOP );
        
        //Graph g = (Graph)m_vis.getGroup(m_group);
        VisualTable ts = (VisualTable) m_vis.getGroup("tree.nodes");
        
        long sum = 0;
        
        for (int i = 1; i<=rowindex; i++){	
        	NodeItem item = (NodeItem) ts.getTuple(i);
            double d = ( v ? item.getBounds().getHeight() 
                     : item.getBounds().getWidth() );
            if (i != rowindex)
            	sum = (long) (sum + d);
            else
            	sum = (long) (sum + d/2);
            //System.out.println("d: "+d+" i: "+ i+ " Summe:" + sum);
        }
        	
        
        ypos = (long) ((rowindex-1)*m_dspace)+sum;
          
        //System.out.println("ypos:"+ypos);
        
        
        setDepth(n, p, ypos);

        if ( n.isExpanded() ) {
            for ( NodeItem c = (NodeItem)n.getFirstChild();
                  c != null; c = (NodeItem)c.getNextSibling() )
            {
                secondWalk(c, n);
            }
        }

    }
    
    /**
     * Sets the breadth position from anchor
     * @param n the node
     * @param p the parent node
     * @param b the breadth position
     */
    private void setBreadth(NodeItem n, NodeItem p, double b) {
        switch ( m_orientation ) {
        case Constants.ORIENT_LEFT_RIGHT:
        case Constants.ORIENT_RIGHT_LEFT:
            setY(n, p, m_ay + b);
            break;
        case Constants.ORIENT_TOP_BOTTOM:
        case Constants.ORIENT_BOTTOM_TOP:
            setX(n, p, m_ax + b);
            break;
        default:
            throw new IllegalStateException();
        }
    }
    
    /**
     * Sets the depth position from anchor.
     * @param n the node 
     * @param p the parent node
     * @param d the depth position
     */
    private void setDepth(NodeItem n, NodeItem p, double d) {
        switch ( m_orientation ) {
        case Constants.ORIENT_LEFT_RIGHT:
            setX(n, p, m_ax + d);
            break;
        case Constants.ORIENT_RIGHT_LEFT:
            setX(n, p, m_ax - d);
            break;
        case Constants.ORIENT_TOP_BOTTOM:
            setY(n, p, m_ay + d);
            break;
        case Constants.ORIENT_BOTTOM_TOP:
            setY(n, p, m_ay - d);
            break;
        default:
            throw new IllegalStateException();
        }
    }
    
    // ------------------------------------------------------------------------
    // Params Schema
    
    /**
     * The data field in which the parameters used by this layout are stored.
     */
    public static final String PARAMS = "_simpleTree";
    /**
     * The schema for the parameters used by this layout.
     */
    public static final Schema PARAMS_SCHEMA = new Schema();
    static {
        PARAMS_SCHEMA.addColumn(PARAMS, Params.class);
    }
    
    /**
     * Adds parameters to tupleSet.
     * @param ts the tuple set
     */
    protected void initSchema(TupleSet ts) {
        ts.addColumns(PARAMS_SCHEMA);
    }
    
    /**
     * Returns the parameters of an item
     * @param item the item
     * @return the parameters of the item
     */
    private Params getParams(NodeItem item) {
        Params rp = (Params)item.get(PARAMS);
        if ( rp == null ) {
            rp = new Params();
            item.set(PARAMS, rp);
        }

        return rp;
    }
    
    /**
     * Wrapper class holding parameters used for each node in this layout.
     */
    public static class Params implements Cloneable {
    	double xpos;
    	        
    }

	
	/**
	 * Returns the next free xpositon.
	 * @return the next free xpostition
	 */
	private double giveNextFreeXPos(){
		double returnvalue = nextfreexpos;
		nextfreexpos = nextfreexpos + m_distance;
		return returnvalue;
	}
	
	/**
	 * Sets the breadth space between nodes.
	 * @param m_distance the breadth space between nodes
	 */
	public void setDistance(double m_distance) {
		this.m_distance = m_distance;
	}

	/**
	 * Sets the depth space between nodes.
	 * @param m_dspace the depth space between nodes
	 */
	public void setDepthSpacing(double m_dspace) {
		this.m_dspace = m_dspace;
	}
}
	
 