package de.feu.cv.guiComponentsP.prefuseP;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Observable;

import javax.swing.JComponent;

import de.feu.cv.guiComponentsP.prefuseP.controlP.ChatEdgeRenderer;
import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.StrokeAction;
import prefuse.action.layout.Layout;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Node;
import prefuse.data.Tree;
import prefuse.data.Tuple;
import prefuse.data.expression.Predicate;
import prefuse.data.tuple.TupleSet;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.RendererFactory;
import prefuse.util.ColorLib;
import prefuse.util.display.PaintListener;
import prefuse.visual.VisualItem;
import prefuse.visual.expression.InGroupPredicate;
import de.feu.cv.applicationLogicP.conversationP.Conversation;
import de.feu.cv.applicationLogicP.conversationP.ThreadedMessage;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane;
import de.feu.cv.guiComponentsP.chatWindowComponentsP.VisualProperties;
import de.feu.cv.guiComponentsP.prefuseP.actionsP.ChatDataColorAction;
import de.feu.cv.guiComponentsP.prefuseP.actionsP.PanAction;
import de.feu.cv.guiComponentsP.prefuseP.actionsP.PostLayoutAction;
import de.feu.cv.guiComponentsP.prefuseP.actionsP.PreLayoutAction;
import de.feu.cv.guiComponentsP.prefuseP.actionsP.StandardTreeLayout;
import de.feu.cv.guiComponentsP.prefuseP.controlP.AutoFocusOffControl;
import de.feu.cv.guiComponentsP.prefuseP.controlP.ChatControl;


/**
 * Abstract base class for visualizations with prefuse.  
 * @author Verena Kunz
 *
 */
public abstract class ChatVisualizationPanePrefuse extends ChatVisualizationPane  {

	private static final long serialVersionUID = 1L;
	/**
	 * The main display.
	 */
	private Display display;
	
	/**
	 * The Visualization object.
	 */
	private Visualization vis;
	/**
	 * The root of the tree.
	 */
	private Node root;
	/**
	 * The newest visual item.
	 */
	private VisualItem newvi;  
	/**
	 * The autofoxus flag.
	 */
	private boolean autofocus = true;
	/**
	 * The item next to middle of display.
	 */
	private VisualItem middle_item;
	/**
	 * The point to hold fix. 
	 */
	private Point2D.Double fix;  
	/**
	 * The tree layout.
	 */
	private StandardTreeLayout layout;  
	/**
	 * The color actionlist.
	 */
	private ActionList color; 
	/**
	 * The panel with overview and control buttons.
	 */
	private OverviewAndControlPanel overviewAndControlPanel;
	/**
	 * The visual properties.
	 */
	protected VisualProperties visualProperties;  
	/**
	 * The chat label renderer.
	 */
	private ChatLabelRenderer lr;
    /**
     * The chat Edge renderer (makes sense for edged that have relation names).
     */
    private ChatEdgeRenderer er;
	/**
	 * Creates a new visualization pane. 
	 * @param conversation the displayed conversation
	 */
	public ChatVisualizationPanePrefuse(Conversation conversation) {
		super(conversation);
		
		//Properties werden nur in unterklassen ben�tigt
		getProperties();	
			
		Tree tree = conversation.getMessageTree();
	    	
     	// create visualization object
        vis = new Visualization();
        vis.add("tree", tree);
        
        //initialize newest item
        Node newnode = conversation.getNewestNode();
        if (newnode != null)
        	newvi = vis.getVisualItem("tree.nodes", newnode); 	
        
        // set root node unvisible
        root = tree.getRoot();
        VisualItem viroot = vis.getVisualItem("tree.nodes", root);
        viroot.setVisible(false);
        
        //set root edges unvisible (is needed for saved trees)
        Iterator it = tree.childEdges(root);
        while (it.hasNext()){
			VisualItem rootedge = vis.getVisualItem("tree.edges", (Tuple) it.next());
			rootedge.setVisible(false);  	
        }
        	     
        vis.setRendererFactory(createRendererFactory());
                
        // add the actions to the visualization

        vis.putAction("layout_autofocus_animated",createLayoutAutofocusActionList(true));
        vis.putAction("layout_autofocus",createLayoutAutofocusActionList(false));
        vis.putAction("layout_autofocusOff", createLayoutAutofocusOffActionList());
        vis.putAction("update_color",createUpdateColorActionList());
        vis.putAction("repaint", new RepaintAction());
        
		initialize();
	}

	/**
	 * This method initializes this.
	 */
	private void initialize() {
		this.add(getDisplay(),"Center");			
		vis.run("layout_autofocus");
	}
	

	/**
	 * This method initializes display.	
	 * 	
	 * @return the prefuse display	
	 */
	public Display getDisplay(){
		if (display == null) {
			display = new Display(vis);
			addListenersToDisplay();
		}
		return display;
	}
	
	/**
	 * Displays a message in the visualization.
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void  update(Observable obs, Object arg) {

		if (obs instanceof Conversation && arg instanceof ThreadedMessage){
			
			boolean isfirstitem = ( newvi == null );
			
			Node newnode = conversation.getNode((ThreadedMessage) arg);
			newvi = vis.getVisualItem("tree.nodes", newnode);
					
	        // set root edges unvisible		
			if (newnode.getParent().equals(root)){
				VisualItem rootedge = vis.getVisualItem("tree.edges", newnode.getParentEdge());
				rootedge.setVisible(false);
			}
	                        
	        doDuringUpdate();
	        
	        // do refresh only if the visualization ist active
	        if (display.isDisplayable()){
	        	
	        	if (autofocus){
	        		if (isfirstitem)
	        			vis.run("layout_autofocus");
	        		else
	        			vis.run("layout_autofocus_animated");
	        	}
	        	else {
	        		vis.run("layout_autofocusOff");
	        	}
	        }
		}
		if (obs instanceof VisualPropertiesPrefuse && arg instanceof String){
			//System.out.println("update in cv0");
			String key = (String) arg;
			//String value = visualProperties.getProperty(key);
			//changePropertyAndUpdateView(key,value);
			changePropertyAndUpdateView(key);
		}
	}

	/**
	 * Changes the property and updates the visualization immediately.
	 * @param key the property
	 */
	private void changePropertyAndUpdateView(String key) {
		changeProperty(key);
		vis.invalidateAll();

//		if (key.equals("orientation")){
//		    display.damageReport();
//		    display.repaint();
			setAutofocus(true);
			vis.run("layout_autofocus");
//		}
	}

	
	/**
	 * Changes the property key in this visualization
	 * @param key	the property
	 * @param value	the new value
	 */
	protected void changeProperty(String key, String value) {
		if (key.equals("orientation")){
			int orientation = Integer.parseInt(value);
			changeOrientation(orientation);
		}
		if (key.equals("depthspacing")){
			int depthspacing = Integer.parseInt(value);
			changeDepthspacing(depthspacing);
		}
		if (key.equals("breadthspacing")){
			int breadthspacing = Integer.parseInt(value);
			changeBreadthspacing(breadthspacing);
		}
		if (key.equals("subtreespacing")){
			int subtreespacing = Integer.parseInt(value);
			changeSubtreespacing(subtreespacing);
		}
		if (key.equals("linebreakwidth")){
			int linebreakwidth = Integer.parseInt(value);
			changeLinebreakwidth(linebreakwidth);
		}

	}
		
	/**
	 * Changes the property key in this visualization.
	 * @param key	the property
	 */
	protected void changeProperty(String key) {
		String value = visualProperties.getProperty(key);
		if (key.equals("orientation")){
			int orientation = Integer.parseInt(value);
			changeOrientation(orientation);
		}
		if (key.equals("depthspacing")){
			int depthspacing = Integer.parseInt(value);
			changeDepthspacing(depthspacing);
		}
		if (key.equals("breadthspacing")){
			int breadthspacing = Integer.parseInt(value);
			changeBreadthspacing(breadthspacing);
		}
		if (key.equals("subtreespacing")){
			int subtreespacing = Integer.parseInt(value);
			changeSubtreespacing(subtreespacing);
		}
		if (key.equals("linebreakwidth")){
			int linebreakwidth = Integer.parseInt(value);
			changeLinebreakwidth(linebreakwidth);
		}

	}

	/**
	 * Changes the linebreakwidth.
	 * @param linebreakwidth the new linebreakwidth
	 */
	private void changeLinebreakwidth(int linebreakwidth) {
		lr.setLinebreakwidth(linebreakwidth);
		
	}

	/**
	 * Changes the subtreespacing.
	 * @param subtreespacing the new subtreespacing
	 */
	private void changeSubtreespacing(int subtreespacing) {
		layout.setSubtreeSpacing(subtreespacing);
		
	}

	/**
	 *  Changes the breadthspacing.
	 * @param breadthspacing the new breadthspacing
	 */
	private void changeBreadthspacing(int breadthspacing) {
		layout.setBreadthSpacing(breadthspacing);
		
	}

	/**
	 * Changes the depthspacing.
	 * @param depthspacing the new depthspacing
	 */
	private void changeDepthspacing(int depthspacing) {
		layout.setDepthSpacing(depthspacing);
		
	}

	/**
	 * Changes the orientation.
	 * @param orientation the new orientation
	 */
	protected void changeOrientation(int orientation) {
		layout.setOrientation(orientation);
		
	}

	/**
	 * Sets selection in model.
	 */
	public void setSelection() {
		TupleSet ts = vis.getFocusGroup(Visualization.FOCUS_ITEMS);
		Node node = null;
		
		if (ts.getTupleCount()!=0){
			VisualItem item = (VisualItem) ts.tuples().next();
			node = (Node) item.getSourceTuple();
		}
		conversation.setSelection(node);		
	}

	/**
	 * Clears the selection of a message.
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#resetSelection()
	 */
	@Override
	public void resetSelection() {
		TupleSet ts = vis.getFocusGroup(Visualization.FOCUS_ITEMS);
		ts.clear();
		setSelection();
		
	}
	
	
	/**
	 * Create the ColorAction for the fillcolor of the nodes.
	 * @return the ColorAction for the fillcolor
	 */
	protected ColorAction createfillColorAction() {
		return new ChatDataColorAction("tree.nodes", "nick",
                Constants.NOMINAL, VisualItem.FILLCOLOR);
	}
	
	/**
	 * Creates the Layout. Default is NodeLinkTreeLayout.
	 * @return the Layout
	 */
	public Layout getTreeLayout() {
		
		if (layout == null){
			layout = new StandardTreeLayout("tree");
	        changeProperty("orientation");
	        changeProperty("depthspacing");
	        changeProperty("breadthspacing");
	        changeProperty("subtreespacing");
	        layout.setLayoutAnchor(new Point2D.Double(0,0));
		}

        return layout;
	}
	
	/**
	 * Abstract method for subclass specific update actions.
	 */
	protected void doDuringUpdate() {
	}

	
	/**
	 * Adds listeners to the display.
	 */
	protected void addListenersToDisplay(){
		// add listener for selection
        display.addControlListener(new ChatControl(this));
        //display.addControlListener(new FocusControl(1,"color"));
        display.setHighQuality(true);
      
        // pan with left-click drag on background
        display.addControlListener(new PanControl()); 
        // zoom with right-click drag
        display.addControlListener(new ZoomControl());
        
        // zoom with wheel
        display.addControlListener(new WheelZoomControl());
        
        display.addControlListener(new AutoFocusOffControl());
        
        
        display.addPaintListener(new PaintListener(){

			/**
			 * @see prefuse.util.display.PaintListener#postPaint(prefuse.Display, java.awt.Graphics2D)
			 */
			public void postPaint(Display arg0, Graphics2D g) {
				// uncomment for troubleshooting
//				System.out.println("Post: Item-Bounds: " +display.getItemBounds());
//				System.out.println("DisplayX: "+ display.getDisplayX());
//				System.out.println("DisplayY: "+ display.getDisplayY());
//				// get bounding rectangle of main display
//				Rectangle2D itembounds = display.getItemBounds();
//				Rectangle2D.Double mainbounds = new Rectangle2D.Double();
//				mainbounds.height = itembounds.getHeight()*display.getScale();
//				mainbounds.width = itembounds.getWidth()*display.getScale();
//				double itemboundsX = itembounds.getX();
//				double itemboundsY = itembounds.getY();
//			
//				mainbounds.x = (itemboundsX*display.getScale() - display.getDisplayX());
//				mainbounds.y = (itemboundsY*display.getScale() - display.getDisplayY());
//				
//				//System.out.println("ABS:"+display.getAbsoluteCoordinate(new Point2D.Double(220.5,232), null));
//				System.out.println("Mainbounds: "+mainbounds);
//				g.draw(mainbounds);
				calculateMiddleItem();
				
				// refresh overview
				if (overviewAndControlPanel !=null)
					overviewAndControlPanel.repaint();
			}

			/**
			 * @see prefuse.util.display.PaintListener#prePaint(prefuse.Display, java.awt.Graphics2D)
			 */
			public void prePaint(Display arg0, Graphics2D arg1) {
				 //System.out.println("Pre: Item-Bounds: " +display.getItemBounds());
				 //System.out.println("DisplayX: "+ display.getDisplayX());
				 //System.out.println("DisplayY: "+ display.getDisplayY());
				 //System.out.println(display.getAbsoluteCoordinate(new Point2D.Double(0,0), null));
			}
        	
        });
	}



	/**
	 * Creates the color action list.
	 * @return the color action list
	 */
	protected ActionList getColorAction(){
		
		if (color == null){
	        // fill color: diffrent colors from palette for diffrent users
	        ColorAction fill = createfillColorAction();
	      
	        // text color
	        ColorAction text = new ColorAction("tree.nodes",
	                VisualItem.TEXTCOLOR, ColorLib.gray(0));
	        
	        // stroke color
	        ColorAction stroke = new ColorAction("tree.nodes",
	                VisualItem.STROKECOLOR, ColorLib.rgba(255,255,255,0));
	        
	        Predicate sel_pred = new InGroupPredicate(Visualization.FOCUS_ITEMS);
	        stroke.add(sel_pred, ColorLib.color(Color.black));
	        
	        // edge color
	        ColorAction edges = new ColorAction("tree.edges",
	                VisualItem.STROKECOLOR, ColorLib.gray(0));
	        
	        
	        StrokeAction strokeaction = new StrokeAction("tree.nodes");
	        strokeaction.add(sel_pred, new BasicStroke(2f));
	
	        // create an action list containing all color assignments
	        color = new ActionList();
	        color.add(fill);
	        color.add(text);
	        color.add(edges);
	        color.add(stroke);
	        color.add(strokeaction);
		}

        return color;
	}
	
	/**
	 * Creates the layout autofocus action list.
	 * @return the layout autofocus action list
	 */
	protected ActionList createLayoutAutofocusActionList(boolean animated) {

        ActionList layoutAutofocus = new ActionList();
        
        layoutAutofocus.add(getColorAction());
        layoutAutofocus.add(new RepaintAction());
        layoutAutofocus.add(getTreeLayout());
        if (animated)
        	layoutAutofocus.add(new PanAction(500));
        else 
        	layoutAutofocus.add(new PanAction(0));
        layoutAutofocus.add(new RepaintAction());
        
		return layoutAutofocus;
	}	
	
	/**
	 * Creates the layout autofocusOff action list.
	 * @return the layout autofocusOff action list
	 */
	protected ActionList createLayoutAutofocusOffActionList() {

        ActionList layoutAutofocusOff = new ActionList();
        
        layoutAutofocusOff.add(getColorAction());
        layoutAutofocusOff.add(new RepaintAction());
        layoutAutofocusOff.add(new PreLayoutAction());
        layoutAutofocusOff.add(getTreeLayout());
        layoutAutofocusOff.add(new PostLayoutAction());    
        layoutAutofocusOff.add( new RepaintAction());

		return layoutAutofocusOff;
	}	
	
	protected ActionList createUpdateColorActionList() {
		ActionList colorupdate = new ActionList();
		colorupdate.add(getColorAction());
		colorupdate.add(new RepaintAction());
		return colorupdate;
	}
	
	/**
	 * Creates the renderer factory.
	 * @return the renderer factory
	 */
	protected RendererFactory createRendererFactory(){
        // define rendering labels and edges
        lr = new ChatLabelRenderer();
        lr.setRoundedCorner(5, 5);
        lr.setHorizontalTextAlignment(Constants.LEFT);
        lr.setHorizontalPadding(4);
        lr.setVerticalPadding(4);
        changeProperty("linebreakwidth");
        
        //lr.setRenderType(ChatLabelRenderer.RENDER_TYPE_FILL);
        EdgeRenderer er = new EdgeRenderer(Constants.EDGE_TYPE_LINE);
        //er.setDefaultLineWidth(1);
       

        // er.setArrowType(Constants.EDGE_ARROW_FORWARD);
        // er.setArrowHeadSize(6, 6);       
        
        DefaultRendererFactory rf = new DefaultRendererFactory(lr,er);
        return rf;
	}
	
	
	/**
	 * Returns the newest VisualItem.
	 * @return the newest VisualItme
	 */
	public VisualItem getNewVi(){
		return newvi;
	}
	

	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#visualizationChanged()
	 */
	public void visualizationChanged(){
		resetView();
		setAutofocus(true);
		
	}
	
	
	/**
	 * Resets the view. No zoom. Last message in the middle. 
	 */
	public void resetView(){
		double scale = display.getScale();
		
		Point2D.Double p = new Point2D.Double(0,0);
		display.zoom(p, 1/scale);
		vis.run("layout_autofocus");
		setFocusToTextInput();
	}



	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#getConfigurationPanel()
	 */
	public JComponent getConfigurationPanel() {
		PropertyContentPanePrefuse cp = new PropertyContentPanePrefuse((VisualPropertiesPrefuse) visualProperties);
		return cp;
	}


	/**
	 * Sets autofocus on and sets the selection in check box.
	 * @param autofocus <code> true</code> autofocus on, <code> false</code>autofocus off
	 */
	public void setAutofocus(boolean autofocus) {
		this.autofocus = autofocus;
		overviewAndControlPanel.setAutofocusSelected(autofocus);
	}
	
	/**
	 * Finds the item in the middle of display.
	 */
	public void calculateMiddleItem()
	{
		Point2D.Double center = new Point2D.Double(display.getSize().getWidth()/2,display.getSize().getHeight()/2);
		Point2D centerabsolute = display.getAbsoluteCoordinate(center, null);
		double minoffset = 1000000;
		
		Iterator it = vis.visibleItems("tree.nodes");
		VisualItem middle_item = null;
		VisualItem item = null;

		while (it.hasNext()){
			item = (VisualItem) it.next();
			
			double offset_x = item.getX() - centerabsolute.getX();
			double offset_y = item.getY() - centerabsolute.getY();
			
			// Pythagoras to calculate the offset
			double offset = Math.sqrt(Math.pow(offset_x, 2)+Math.pow(offset_y, 2));
			if (offset < minoffset){
				minoffset = offset;
				middle_item = item;	
			}
 		}
		if (middle_item !=null){
			//System.out.println("Am n�chsten am Mittelpunkt ist: "+ middle_item.getString("text"));
			this.middle_item = middle_item;
		}

	}

	/**
	 * Returns the fix point in the layout.
	 * @return the fix point
	 */
	public Point2D.Double getFix() {
		return fix;
	}



	/**
	 * Sets the fix point in the layout. If layout changes,
	 * pre and post layout actions pan display to fix this point.
	 * @param fix the fix point
	 */
	public void setFix(Point2D.Double fix) {
		//System.out.println(fix);
		this.fix = fix;
	}



	/**
	 * Returns the item next to the middle of display.
	 * @return the visual item in the middle of display
	 */
	public VisualItem getMiddle_item() {
		return middle_item;
	}

	/**
	 * @see de.feu.cv.guiComponentsP.chatWindowComponentsP.ChatVisualizationPane#getContentForRightSlot()
	 */
	public JComponent getContentForRightSlot() {
		if (overviewAndControlPanel == null)
			overviewAndControlPanel = new OverviewAndControlPanel(this);
		return overviewAndControlPanel;
	}

	/**
	 * Returns the visualization object connected with this panel.
	 * @return the visualization object
	 */
	public Visualization getVisualization() {
		return vis;
	}


	



	
}