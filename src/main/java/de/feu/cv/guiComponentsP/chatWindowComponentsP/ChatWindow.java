package de.feu.cv.guiComponentsP.chatWindowComponentsP;

import java.awt.BorderLayout;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSplitPane;

import de.feu.cv.applicationLogicP.MuViChatConstants;
import de.feu.cv.applicationLogicP.Resources;
import de.feu.cv.applicationLogicP.conversationP.Conversation;
import de.feu.cv.guiComponentsP.MainWindow;
import de.feu.cv.guiComponentsP.dialogP.ChatWindowPropertyDialog;

import javax.swing.JMenuItem;


public abstract class ChatWindow extends JFrame {

	private static final long serialVersionUID = 1L;

    /**
     * The dictionary of visualizations.
     */
    protected HashMap<String, ChatVisualizationPane> visualizations;
	
	/**
	 * The current visualization.
	 */
	private ChatVisualizationPane currentVisualization;
	
	protected JPanel jContentPane = null;
    	
	private JMenu viewMenu = null;

	private JSplitPane jSplitPaneAll = null;

	private JSplitPane jSplitPaneLeft = null;

	private JSplitPane jSplitPaneRight = null;

	private JMenu extrasMenu = null;

	private JMenuItem optionsMenuItem = null;

    private JMenuItem inspectorMenuItem = null;

	/**
	 * The dialog for properties of current visualization.
	 */
	private ChatWindowPropertyDialog propdialog;

    private ChatInspectorWindow inspectorWindow;


	/**
	 * This is the default constructor.
	 */
	public ChatWindow() {
		super();
		this.visualizations = new HashMap<String, ChatVisualizationPane>();
		initialize();
	}

	/**
	 * This method initializes this.
	 */
	private void initialize() {
		this.setSize(600, 400);
		this.setLocation(MainWindow.getInstance().getLocationForChatWindow());
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				saveVisualProperties();
			}
		});
	}



	/**
	 * This method initializes jContentPane.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPaneAll(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	
	/**
	 * Change the visualization of chat messages.
	 * @param keyname the keyname of the visualization to show
	 */
	public void setVisualization(String keyname){
		// remove current
		removeCurrentVisualisatzion();
		
		// set main panel
		ChatVisualizationPane vp = visualizations.get(keyname); 
		jSplitPaneAll.setLeftComponent((JComponent)vp);
		

		// set configuration panel
//		JComponent configurationPanel = vp.getConfigurationPanel();
//		if (configurationPanel !=null){
//			jSplitPaneLeft.setLeftComponent(getPropertyPanel());
//			propertyPanel.setConfigPanel(configurationPanel);
//		}
				
		// place content in right slot
		JComponent rightcontent = (JComponent) vp.getContentForRightSlot();
		jSplitPaneRight.setBottomComponent(rightcontent);
		jSplitPaneRight.revalidate();

		// validate
		jSplitPaneAll.validate();
		jSplitPaneAll.resetToPreferredSizes();
		
		currentVisualization = vp;
		
		// do some visualization specific actions
		vp.visualizationChanged();
	}
	
	/**
	 * Removes the current visualization from window.
	 */
	private void removeCurrentVisualisatzion(){
		// remove components of previous visualization
		if (currentVisualization!=null)
		{
			// remove main visualization panel
			jSplitPaneAll.remove((JComponent)currentVisualization);
			
			// remove content in right slot
			JComponent rightcontent = (JComponent) currentVisualization.getContentForRightSlot();
			if (rightcontent != null){
				jSplitPaneRight.remove(rightcontent);
			}
			
			// remove configuration panel
//			JComponent configurationPanel = currentVisualization.getConfigurationPanel();
//			if (configurationPanel != null){
//				jSplitPaneLeft.remove(propertyPanel);
//			}
			
			// close open configuration dialog
			if (propdialog !=null)
				propdialog.dispose();
		}
	}

	
	/**
	 * Creates the visualisation panes for all visualization plugins.
	 * @param conversation
	 */
	protected void createVisualizationPanes(Conversation conversation){
		//get plugin list
		String[] pluginlist = MuViChatConstants.visualizationPlugins;
		
		for (int i = 0; i< pluginlist.length; i++){
			try {
				// load class into virtual machine
				Class c = Class.forName("de.feu.cv.guiComponentsP.pluginsP."+pluginlist[i]+".VisualizationPane");
				
				// parameter list for constructor ChatVisualizationPane(Conversation)	
				Class[] param_classes = new Class[1];
				param_classes[0] = Class.forName( "de.feu.cv.applicationLogicP.conversationP.Conversation" );

				// get constructor
				Constructor constr = c.getConstructor(param_classes);

				// parameter list
				Object[] params = new Object[1];
				params[0] = conversation;

				// create instance with constructor and parameter list
				ChatVisualizationPane cvp = (ChatVisualizationPane) constr.newInstance(params);
				
				// add visualization to HashMap
				//visualizations.put(cvp.getKeyName(), cvp);
				visualizations.put(pluginlist[i], cvp);
				
				
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			} catch (InstantiationException e) {

				e.printStackTrace();
			} catch (IllegalAccessException e) {

				e.printStackTrace();
			} catch (SecurityException e) {

				e.printStackTrace();
			} catch (NoSuchMethodException e) {

				e.printStackTrace();
			} catch (IllegalArgumentException e) {

				e.printStackTrace();
			} catch (InvocationTargetException e) {

				e.printStackTrace();
			}
			
		}
		

		// activate the first visualisation
		String first = pluginlist[0];
		setVisualization(first);
		
	}
	
	/**
	 * Brings this window to the front.
	 */
	public void bringToFront(){
		this.toFront();
	}
	
	/**
	 * This method initializes viewMenu	.
	 * 	
	 * @return javax.swing.JMenu	
	 */
	protected JMenu getViewMenu() {
		if (viewMenu == null) {
			viewMenu = new JMenu();
			viewMenu.setText((Resources.getString("menu_view")));
			addVisualizationMenuItems();
		}
		return viewMenu;
	}

	/**
	 * Creates the menu items for the visualizations and adds them
	 * to a button group and to the viewMenu.
	 */
	private void addVisualizationMenuItems()
	{		
	    //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    
	    String[] pluginlist = MuViChatConstants.visualizationPlugins;
		for (int i = 0; i< pluginlist.length; i++){
	    	JRadioButtonMenuItem viewMenuItem = new JRadioButtonMenuItem();
	    	

	    	viewMenuItem.setText(Resources.getString("menu_view_"+pluginlist[i]));
			viewMenuItem.setActionCommand(pluginlist[i]);
			viewMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisualization(e.getActionCommand());
				}
			});
			viewMenu.add(viewMenuItem);
		    group.add(viewMenuItem);
	    }

		// activate first menu item
		((AbstractButton) viewMenu.getMenuComponent(0)).setSelected(true);
	}
	
	/**
	 * This method initializes jSplitPaneAll.	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	protected JSplitPane getJSplitPaneAll() {
		if (jSplitPaneAll == null) {
			jSplitPaneAll = new JSplitPane();
			//jSplitPaneAll.setLeftComponent(getJSplitPaneLeft());
			jSplitPaneAll.setRightComponent(getJSplitPaneRight());
			jSplitPaneAll.setResizeWeight(1);
		}
		return jSplitPaneAll;
	}

	/**
	 * This method initializes jSplitPaneLeft.
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	protected JSplitPane getJSplitPaneLeft() {
		if (jSplitPaneLeft == null) {
			jSplitPaneLeft = new JSplitPane();
			//jSplitPaneLeft.setLeftComponent(getPropertyPanel());
		}
		return jSplitPaneLeft;
	}
	

	
	/**
	 * This method initializes jSplitPaneRight.	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	protected JSplitPane getJSplitPaneRight() {
		if (jSplitPaneRight == null) {
			jSplitPaneRight = new JSplitPane();
			jSplitPaneRight.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPaneRight.setResizeWeight(0);
			jSplitPaneRight.setTopComponent(null);
		}
		return jSplitPaneRight;
	}

	
	/**
	 * Saves the current visual properties of all visualization panes.
	 */
	protected void saveVisualProperties() {
		Iterator it = visualizations.values().iterator();
		
		while (it.hasNext()){
			VisualProperties props = ((ChatVisualizationPane)it.next()).getProperties();
			if (props !=null){			
				props.saveVisualProperties();
			}
		}	
	}

	/**
	 * This method initializes extrasMenu.	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	protected JMenu getExtrasMenu() {
		if (extrasMenu == null) {
			extrasMenu = new JMenu();
			extrasMenu.setText((Resources.getString("menu_extras")));
			extrasMenu.add(getOptionsMenuItem());
            extrasMenu.add(getInspectorMenuItem());
		}
		return extrasMenu;
	}

	/**
	 * This method initializes optionsMenuItem.	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getOptionsMenuItem() {
		if (optionsMenuItem == null) {
			optionsMenuItem = new JMenuItem();
			optionsMenuItem.setText(Resources.getString("menu_extras_properties"));
			optionsMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					createChatWindowPropertyDialog();
				}
			});
		}
		return optionsMenuItem;
	}

    /**
     * This method initializes optionsMenuItem.
     *
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getInspectorMenuItem() {
        if (inspectorMenuItem == null) {
            inspectorMenuItem = new JMenuItem();
            inspectorMenuItem.setText("Inspect");
            inspectorMenuItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    createInspectorWindow();
                }
            });
        }
        return inspectorMenuItem;
    }
	
	
	/**
	 * Creates a new property dialog window or set the existing one visible.
	 */
	private void createChatWindowPropertyDialog(){
		if (propdialog == null)
			propdialog = new ChatWindowPropertyDialog(this);
		// get config panel from visualization
		JComponent configurationPanel = currentVisualization.getConfigurationPanel();
		propdialog.setConfigPanel(configurationPanel);
		propdialog.setVisible(true);
	}


    private void createInspectorWindow(){
        if (inspectorWindow == null)
            inspectorWindow = new ChatInspectorWindow(currentVisualization.conversation);

        inspectorWindow.open();

    }



}
