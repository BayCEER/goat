/*******************************************************************************
 * Copyright (c) 2011 University of Bayreuth - BayCEER.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     University of Bayreuth - BayCEER - initial API and implementation
 ******************************************************************************/
package de.unibayreuth.bayeos.goat;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.apache.batik.util.gui.resource.ButtonFactory;
import org.apache.batik.util.gui.resource.MenuFactory;
import org.apache.batik.util.gui.resource.MissingListenerException;
import org.apache.batik.util.gui.resource.PopupMenuFactory;
import org.apache.batik.util.gui.resource.ResourceManager;
import org.apache.batik.util.gui.resource.ToolBarFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import de.unibayreuth.bayceer.bayeos.client.Client;
import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.TimeFilter;
import de.unibayreuth.bayeos.connection.Connection;
import de.unibayreuth.bayeos.connection.JDialogConnect;
import de.unibayreuth.bayeos.connection.JDialogPassword;
import de.unibayreuth.bayeos.goat.exp.JExportMatrixDialog;
import de.unibayreuth.bayeos.goat.filter.JMessungFilter;
import de.unibayreuth.bayeos.goat.filter.JMessungenFilter;
import de.unibayreuth.bayeos.goat.filter.JTreeFilter;
import de.unibayreuth.bayeos.goat.options.JOptionDialog;
import de.unibayreuth.bayeos.goat.panels.FormFactory;
import de.unibayreuth.bayeos.goat.panels.JPropertiesPanel;
import de.unibayreuth.bayeos.goat.panels.LoadableForm;
import de.unibayreuth.bayeos.goat.panels.XmlRpcObjektHelper;
import de.unibayreuth.bayeos.goat.panels.frame.JFramePanel;
import de.unibayreuth.bayeos.goat.panels.timeseries.JPanelDetailMatrix;
import de.unibayreuth.bayeos.goat.search.JSearchObjektDialog;
import de.unibayreuth.bayeos.goat.tree.JObjektTree;
import de.unibayreuth.bayeos.goat.tree.JObjektTreePanel;
import de.unibayreuth.bayeos.goat.tree.JTreeTabbedPane;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;
import de.unibayreuth.bayeos.goat.tree.ObjektTreeModel;
import de.unibayreuth.bayeos.goat.tree.ObjektTreeRenderer;
import de.unibayreuth.bayeos.utils.ImageFactory;
import de.unibayreuth.bayeos.utils.ImageFactoryException;
import de.unibayreuth.bayeos.utils.JChangePwdDialog;
import de.unibayreuth.bayeos.utils.LookUpTableFactory;
import de.unibayreuth.bayeos.utils.MsgBox;
import de.unibayreuth.bayeos.utils.WaitCursorEventQueue;

/*
 * Main Frame 
 * argument: http://localhost:8080/servlet/XMLServlet 
 * 
 */
public class JMainFrame extends JFrame implements
		org.apache.batik.util.gui.resource.ActionMap, Constants {

	protected final static Logger logger = Logger.getLogger("JMainFrame.class");

	protected final static String RESOURCES = "de.unibayreuth.bayeos.goat.JMainFrame";

	/**
	 * The resource bundle
	 */
	protected static ResourceBundle bundle;

	/**
	 * The resource manager
	 */
	protected static ResourceManager resources;

	static {
		bundle = ResourceBundle.getBundle(RESOURCES, Locale.getDefault());
		resources = new ResourceManager(bundle);
	}

	/*
	 * GUI Elements
	 */
	private JMenuBar menuBar;
	private JToolBar toolbar;
	private JPopupMenu treePopupMenu;

	private JPanel leftPanel, rightPanel;

	private FormFactory formFactory;
	private LoadableForm detailForm;

	private JTreeTabbedPane treePane;

	private JLabel statusLabel;

	private JSplitPane splitPane;

	private ObjektNode lastSelectedNode;
	private Preferences pref;

	/*
	 * Actions
	 */

	public final static String ABOUT_ACTION = "AboutAction";
	public final static String CREATE_SESSION_ACTION = "CreateSessionAction";
	public final static String TERMINATE_SESSION_ACTION = "TerminateSessionAction";
	public final static String EXIT_ACTION = "ExitAction";

	public final static String SEARCH_ACTION = "SearchAction";

	public final static String TREE_FILTER_PROP_ACTION = "TreeFilterPropAction";
	public final static String TREE_FILTER_TOGGLE_ACTION = "TreeFilterToggleAction";

	public final static String LOAD_DATA_ACTION = "LoadDataAction";

	public final static String VIEW_PROPERTIES_ACTION = "ViewPropertiesAction";

	public final static String NEW_MESSUNG_ORDNER_ACTION = "NewMessungOrdnerAction";
	public final static String NEW_MESSUNG_MASSENDATEN_ACTION = "NewMessungMassendatenAction";
	public final static String NEW_MESSUNG_LABORDATEN_ACTION = "NewMessungLabordatenAction";

	public final static String NEW_DEVICE_ACTION = "NewDeviceAction";
	public final static String NEW_COMPARTMENT_ACTION = "NewCompartmentAction";
	public final static String NEW_LOCATION_ACTION = "NewLocationAction";
	public final static String NEW_UNIT_ACTION = "NewUnitAction";
	public final static String NEW_TARGET_ACTION = "NewTargetAction";

	public final static String DELETE_NODE_ACTION = "DeleteNodeAction";
	public final static String CLEAN_MESSUNG_ACTION = "CleanMessungAction";

	public final static String RENAME_NODE_ACTION = "RenameNodeAction";
	public final static String REFRESH_NODE_ACTION = "RefreshNodeAction";

	public final static String EXPORT_MATRIX_ACTION = "ExportMatrixAction";
	public final static String EDIT_ANY_ACTION = "EditAnyAction";

	public final static String CHANGE_PASSWORD = "ChangePasswordAction";
	public final static String OPTIONS_ACTION = "OptionsAction";

	protected CreateSessionAction createSessionAction = new CreateSessionAction();
	protected ExitAction exitAction = new ExitAction();
	protected ExportMatrixAction exportMatrixAction = new ExportMatrixAction();

	protected TreeFilterPropAction treeFilterPropAction = new TreeFilterPropAction();
	protected TreeFilterToggleAction treeFilterToggleAction = new TreeFilterToggleAction();

	protected TerminateSessionAction terminateSessionAction = new TerminateSessionAction();

	protected SearchAction searchAction = new SearchAction();

	protected LoadDataAction loadDataAction = new LoadDataAction();
	protected ViewPropertiesAction viewPropertiesAction = new ViewPropertiesAction();

	protected NewMessungOrdnerAction newMessungOrdnerAction = new NewMessungOrdnerAction();
	protected NewMessungLabordatenAction newMessungLabordatenAction = new NewMessungLabordatenAction();
	protected NewMessungMassendatenAction newMessungMassendatenAction = new NewMessungMassendatenAction();
	protected CleanMessungAction cleanMessungAction = new CleanMessungAction();

	protected NewDeviceAction newDeviceAction = new NewDeviceAction();
	protected NewCompartmentAction newCompartmentAction = new NewCompartmentAction();
	protected NewLocationAction newLocationAction = new NewLocationAction();
	protected NewUnitAction newUnitAction = new NewUnitAction();
	protected NewTargetAction newTargetAction = new JMainFrame.NewTargetAction();

	protected DeleteNodeAction deleteNodeAction = new DeleteNodeAction();
	protected RenameNodeAction renameNodeAction = new RenameNodeAction();
	protected RefreshNodeAction refreshNodeAction = new RefreshNodeAction();

	protected EditAnyAction editAnyAction = new EditAnyAction();
	protected OptionsAction optionsAction = new OptionsAction();
	protected ChangePasswordAction changePasswordAction = new ChangePasswordAction();

	/**
	 * The map that contains the action listeners
	 */
	protected Map listeners = new HashMap();

	/*
	 * Dialogs
	 */
	private JMessungFilter messungFilterDialog;
	private JMessungenFilter messungenFilterDialog;

	private JDialogConnect dialogConnect;
	private JOptionDialog optionDialog;
	private JSearchObjektDialog searchDialog;
	private JTreeFilter treeFilter;
	private JEditAnyStatusDialog editAnyStatus;

	
	protected String AppDir;

	// for wait cursor
	private EventQueue waitQueue;

	public XmlRpcObjektHelper xmlHelper;

	public final static String homeFolder = new JFileChooser()
			.getFileSystemView().getDefaultDirectory().getAbsolutePath();

	public JMainFrame() {
		super(resources.getString("Frame.Title"));

		// Logging
		ClassLoader cl = this.getClass().getClassLoader();
		PropertyConfigurator.configure(cl.getResource(resources
				.getString("LogPropFile")));

		System.setProperty("user.timezone", "GMT+1");
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));

		logger.info("Default timezone: " + TimeZone.getDefault().getID());
		pref = Preferences.userNodeForPackage(JMainFrame.class);

		Charset d = Charset.defaultCharset();
		logger.info("Charset Name:" + d.name());

		System.setProperty("file.encoding", "UTF-8");
		logger.info("File.Encoding:" + System.getProperty("file.encoding"));

		// Simple JOptionPanes
		MsgBox.setComponent(this);
		MsgBox.setTitle(this.getTitle());

		logger.debug("Constructing JMainFrame");

		waitQueue = new WaitCursorEventQueue(200);
		Toolkit.getDefaultToolkit().getSystemEventQueue().push(waitQueue);

		this.AppDir = System.getProperty("user.home") + File.separatorChar
				+ resources.getString("Application.Dir");

		File AppFile = new File(AppDir);
		if (!AppFile.exists()) {
			if (!AppFile.mkdir()) {
				logger.warn("Application directory " + AppDir
						+ " not created !");
			}
			;
		}

		// Frame Icon
		try {
			setIconImage(ImageFactory.getImage(resources
					.getString("Frame.Icon")));
		} catch (ImageFactoryException i) {
			logger.warn("Image Frame.Icon not set.");
		}

		logger.debug("Creating Actions");
		listeners.put(ABOUT_ACTION, new AboutAction());
		listeners.put(CREATE_SESSION_ACTION, createSessionAction);
		listeners.put(TERMINATE_SESSION_ACTION, terminateSessionAction);
		listeners.put(EXIT_ACTION, exitAction);

		listeners.put(NEW_MESSUNG_ORDNER_ACTION, newMessungOrdnerAction);
		listeners.put(NEW_MESSUNG_MASSENDATEN_ACTION,
				newMessungMassendatenAction);
		listeners
				.put(NEW_MESSUNG_LABORDATEN_ACTION, newMessungLabordatenAction);
		listeners.put(CLEAN_MESSUNG_ACTION, cleanMessungAction);

		listeners.put(NEW_DEVICE_ACTION, newDeviceAction);
		listeners.put(NEW_TARGET_ACTION, newTargetAction);
		listeners.put(NEW_COMPARTMENT_ACTION, newCompartmentAction);
		listeners.put(NEW_UNIT_ACTION, newUnitAction);
		listeners.put(NEW_LOCATION_ACTION, newLocationAction);

		listeners.put(RENAME_NODE_ACTION, renameNodeAction);
		listeners.put(DELETE_NODE_ACTION, deleteNodeAction);
		listeners.put(REFRESH_NODE_ACTION, refreshNodeAction);

		listeners.put(LOAD_DATA_ACTION, loadDataAction);
		listeners.put(VIEW_PROPERTIES_ACTION, viewPropertiesAction);

		listeners.put(SEARCH_ACTION, searchAction);

		listeners.put(OPTIONS_ACTION, optionsAction);

		listeners.put(EXPORT_MATRIX_ACTION, exportMatrixAction);
		listeners.put(EDIT_ANY_ACTION, editAnyAction);

		listeners.put(CHANGE_PASSWORD, changePasswordAction);
		listeners.put(TREE_FILTER_PROP_ACTION, treeFilterPropAction);
		listeners.put(TREE_FILTER_TOGGLE_ACTION, treeFilterToggleAction);

		logger.debug("Constructing toolbar and menus");
		MenuFactory mf = new MenuFactory(bundle, this);
		menuBar = mf.createJMenuBar("MenuBar");
		setJMenuBar(menuBar);

		ToolBarFactory tbf = new ToolBarFactory(bundle, this);
		toolbar = tbf.createJToolBar("ToolBar");
		getContentPane().add(toolbar, BorderLayout.NORTH);

		ButtonFactory bf = new ButtonFactory(bundle, this);
		JToggleButton b = bf.createJToggleButton("TreeFilterToggle");
		b.setSelected(pref.getBoolean("tree_use_filter", false));
		toolbar.add(b);

		formFactory = new FormFactory(this);

		logger.debug("Constructing panes");
		leftPanel = new JPanel(new BorderLayout());
		leftPanel.setBorder(null);

		rightPanel = new JPanel(new BorderLayout());
		rightPanel.setBorder(null);

		initSplitPane();
		initLocation();

		addWindowListener(new WindowAdapter() {
			// Invoked when user clicks the close box
			public void windowClosing(WindowEvent evt) {
				exitAction.actionPerformed(null);
			}
		});

		logger.debug("JMainFrame constructed");

	}

	private void initSplitPane() {
		statusLabel = new JLabel("");
		statusLabel.setBorder(BorderFactory.createEtchedBorder());

		getContentPane().add(statusLabel, BorderLayout.SOUTH);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,
				rightPanel);
		splitPane.setBorder(BorderFactory.createEmptyBorder());
		getContentPane().add(splitPane, BorderLayout.CENTER);

		splitPane.setDividerLocation(pref.getInt("SplitPane", 200));
	}

	private void initLocation() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) (screenSize.width * 0.3);
		int height = (int) (screenSize.height * 0.3);

		width = Math.max(pref.getInt("Width", width), 400);
		height = Math.max(pref.getInt("Height", height), 300);

		setSize(width, height);

		int x = Math.max(0, (screenSize.width - width) / 2);
		int y = Math.max(0, (screenSize.height - height) / 2);

		setLocation(pref.getInt("Position_X", x), pref.getInt("Position_Y", y));

	}

	public static void main(String[] args) {
		JMainFrame frame = null;
		try {
			frame = new JMainFrame();
			frame.setVisible(true);
			frame.createSessionAction.actionPerformed(null);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, e.getMessage(),
					"JMainFrame.main()", JOptionPane.ERROR_MESSAGE);
		}
	}


	/**
	 * Returns the action associated with the given string or null on error
	 * 
	 * @param key
	 *            the key mapped with the action to get
	 * @throws MissingListenerException
	 *             if the action is not found
	 */
	public Action getAction(String key) throws MissingListenerException {
		Action result = (Action) listeners.get(key);
		if (result == null) {
			throw new MissingListenerException("Can't find action for key : "
					+ key, RESOURCES, key);
		}
		return result;
	}

	public void toggleAction(String key, boolean enabled)
			throws MissingListenerException {
		Action result = (Action) listeners.get(key);
		if (result == null) {
			throw new MissingListenerException("Can't find action for key: "
					+ key, RESOURCES, key);
		}
		result.setEnabled(enabled);
	}

	/**
	 * To show the about dialog
	 */
	public class AboutAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {

			JTextArea textArea = new JTextArea(
					resources.getString("AboutAction.text"), 20, 1);
			textArea.setPreferredSize(new Dimension(400, 100));
			textArea.setEditable(false);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			JOptionPane.showMessageDialog(JMainFrame.this, textArea,
					"About...", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public class TreeFilterToggleAction extends JDefaultAction {
		public void actionPerformed(ActionEvent e) {
			logger.debug("TreeFilterToggleAction");
			JToggleButton b = (JToggleButton) e.getSource();
			pref.putBoolean("tree_use_filter", b.isSelected());
			if (treePane != null) {
				JObjektTree tree = treePane.getCurrentTree();
				tree.refreshRoot();
				treePane.setFilterChanged(true);
			}
		}
	}

	public class TreeFilterPropAction extends JDefaultAction {

		public void actionPerformed(ActionEvent e) {
			logger.debug("TreeFilterPropAction");
			if (treeFilter == null) {
				treeFilter = new JTreeFilter(JMainFrame.this, true);
			}
			treeFilter.setVisible(true);

			if (treeFilter.isFilterChanged()) {
				if (treePane != null) {
					JObjektTree tree = treePane.getCurrentTree();
					tree.refreshRoot();
					treePane.setFilterChanged(true);
				}
			}

		}
	}
	
	
	

	public class CreateSessionAction extends JDefaultAction {
		public void actionPerformed(ActionEvent ev) {
			
			
		    
						
			dialogConnect = new JDialogConnect(JMainFrame.this);
			dialogConnect.init();
			dialogConnect.setVisible(true);

			if (dialogConnect.isOkPressed()) {
				// Try to create a session
				try {
					Connection con = dialogConnect.getSelectedConnection();
					String pw = con.getPassword();
					if (pw == null) {
						JDialogPassword p = new JDialogPassword(JMainFrame.this);
						p.setVisible(true);
						if (p.isOkPressed()) {
							pw = p.getPassword();							
						} else {
							return;
						}
					}
					
					Client.getInstance().connect(con.getURL(), con.getUserName(), pw);

					if (pref.getBoolean("rememberlastconnection", true)) {
						pref.put("lastconnection", con.getName());
					}
					initGUI();

				} catch (XmlRpcException e) {					
					logger.error(e.getMessage());
					MsgBox.error("Failed to create session please try again.");
				} 
			}
		}
	}
	
	
	public void initGUI() {
		
		xmlHelper = new XmlRpcObjektHelper();

		// PopUpMenu
		PopupMenuFactory pf = new PopupMenuFactory(bundle, JMainFrame.this);
		treePopupMenu = pf.createJPopupMenu("TreePopupMenu");

		createSessionAction.setEnabled(false);
		terminateSessionAction.setEnabled(true);
		
		editAnyAction.setEnabled(true);
		exportMatrixAction.setEnabled(true);
		loadDataAction.setEnabled(true);
		changePasswordAction.setEnabled(true);
		searchAction.setEnabled(true);
		treeFilterPropAction.setEnabled(true);
		treeFilterToggleAction.setEnabled(true);

		// Init leftPanel
		ObjektSelectionListener selListener = new ObjektSelectionListener();
		ObjektMouseListener mouseListener = new ObjektMouseListener();
		ObjektTreeRenderer treeRenderer = new ObjektTreeRenderer();

		treePane = new JTreeTabbedPane(JMainFrame.this);
		treePane.setBorder(null);

		@SuppressWarnings("rawtypes")
		Iterator it = treePane.getObjektTreePanels().values().iterator();
		while (it.hasNext()) {
			JObjektTree tree = ((JObjektTreePanel) it.next())
					.getObjektTree();
			tree.addTreeSelectionListener(selListener);
			tree.addMouseListener(mouseListener);
			tree.setCellRenderer(treeRenderer);
		}
		leftPanel.removeAll();
		leftPanel.add(treePane, BorderLayout.CENTER);
		leftPanel.revalidate();

		logger.debug("Init Factories");
		LookUpTableFactory.initFactory();

		logger.debug("Init Filter Dialog");
		messungFilterDialog = new JMessungFilter(JMainFrame.this);
		messungenFilterDialog = new JMessungenFilter(JMainFrame.this);

	

}

	public class TerminateSessionAction extends JDefaultAction {
		public void actionPerformed(ActionEvent e) {
			logger.debug("Terminating Session ...");

			try {
				Client.getInstance().close();										
			} catch (XmlRpcException ex) {
				logger.error(ex.getMessage());
			}
			
			xmlHelper = null;
			treePopupMenu = null;
			
			LookUpTableFactory.disposeFactory();
			
			editAnyAction.setEnabled(false);
			exportMatrixAction.setEnabled(false);
			loadDataAction.setEnabled(false);
			changePasswordAction.setEnabled(false);
			searchAction.setEnabled(false);
			viewPropertiesAction.setEnabled(false);
			renameNodeAction.setEnabled(false);
			refreshNodeAction.setEnabled(false);
			
			newCompartmentAction.setEnabled(false);
			newDeviceAction.setEnabled(false);
			newLocationAction.setEnabled(false);
			newMessungLabordatenAction.setEnabled(false);
			newMessungMassendatenAction.setEnabled(false);
			newMessungOrdnerAction.setEnabled(false);
			newTargetAction.setEnabled(false);
			newUnitAction.setEnabled(false);
			deleteNodeAction.setEnabled(false);
			cleanMessungAction.setEnabled(false);
			
			
			createSessionAction.setEnabled(true);
			terminateSessionAction.setEnabled(false);
			
			treeFilterPropAction.setEnabled(false);
			treeFilterToggleAction.setEnabled(false);
			
			treePane = null;
			leftPanel.removeAll();
			leftPanel.repaint();
			
			unloadForm();		
			messungFilterDialog = null;
			messungenFilterDialog = null;
		}
	}
	
	


	

	public class LoadDataAction extends JDefaultAction {

		public void actionPerformed(ActionEvent e) {
			logger.debug("LoadDataAction");
			DefaultMutableTreeNode node = treePane.getCurrentTree().getLastSelectedNode();
			
			if (node == null) {
				loadMatrixForm(null, null);
				return;
			}
				
			ObjektNode n = (ObjektNode) node.getUserObject();
			
			if (n.getObjektart() == ObjektArt.DATA_FRAME) {							
				loadForm(new JFramePanel(n));														
			} else if (node.isLeaf()) {
				loadDetailForm(n);
			} else {								
				loadMatrixForm(n,getChildMessungen());
			}
			

		}
	}

	private void loadDetailForm(ObjektNode o) {

		try {
			messungFilterDialog.setLocationRelativeTo(JMainFrame.this);
			messungFilterDialog.setObjektNode(lastSelectedNode);
			messungFilterDialog.setMaxRecNumber(pref.getInt("maxrecnumber",200000));
			messungFilterDialog.setVisible(true);

			if (messungFilterDialog.okPressed()) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				LoadableForm f = formFactory.getDetailForm(o,
						messungFilterDialog.getStatusFilter(),
						messungFilterDialog.getTimeFilter(),
						messungFilterDialog.getAggrFilter());
				f.setObjektNode(o);
				loadForm(f);
			}

		} finally {
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}

	}

	private void loadMatrixForm(ObjektNode node, Vector nodes) {
		messungenFilterDialog.setLocationRelativeTo(JMainFrame.this);
		messungenFilterDialog.setObjektNodes(nodes);
		messungenFilterDialog.setOriginalSelected();
		if (node == null) {
			messungenFilterDialog.setTimeFilter(new TimeFilter());
		} else {
			messungenFilterDialog.setTimeFilter(new TimeFilter(node
					.getRec_start(), node.getRec_end()));
		}
		messungenFilterDialog.setVisible(true);

		if (messungenFilterDialog.isOkPressed()) {
			try {
				JPanelDetailMatrix f = new JPanelDetailMatrix(this);
				f.setTimeFilter(messungenFilterDialog.getTimeFiler());
				f.setObjektNode(node);
				f.setObjektNodes(messungenFilterDialog.getObjektNodes());
				if (messungenFilterDialog.isOriginalSelected()) {
					f.setStatusFilter(messungenFilterDialog.getStatusFilter());
					f.setAggregateEnabled(false);
				} else {
					f.setAggregateFilter(messungenFilterDialog
							.getAggregateFilter());
					f.setAggregateEnabled(true);
				}
				loadForm(f);
			} finally {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}

	}

	public class OptionsAction extends JDefaultAction {
		public void actionPerformed(ActionEvent e) {
			logger.debug("OptionsAction");
			if (optionDialog == null) {
				optionDialog = new JOptionDialog(JMainFrame.this, true);
			}
			optionDialog.load();
			optionDialog.setVisible(true);
		}
	}

	public class ExportMatrixAction extends JDefaultAction {
		public void actionPerformed(ActionEvent e) {
			logger.debug("ExportMatrixAction");
			JExportMatrixDialog i = new JExportMatrixDialog(JMainFrame.this);
			if (lastSelectedNode != null) {

				i.setObjektNodes(getChildMessungen());
				i.setTimeFilter(new TimeFilter(lastSelectedNode.getRec_start(),
						lastSelectedNode.getRec_end()));
				i.setOriginalSelected();
				StringBuffer b = new StringBuffer(lastSelectedNode.getDe());
				b.append(".csv");
				i.setFilePath(b.toString());
			}
			i.setVisible(true);
		}
	}

	public class NewCompartmentAction extends JMainFrame.NewNodeByNameAction {
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			logger.debug("NewCompartmentAction");
			String name = getName(ObjektArt.MESS_KOMPARTIMENT);
			if (name == null)
				return;
			insertNodeByName(name, ObjektArt.MESS_KOMPARTIMENT);
		}
	}

	public class NewDeviceAction extends JMainFrame.NewNodeByNameAction {
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			logger.debug("NewDeviceAction");
			String name = getName(ObjektArt.MESS_GERAET);
			if (name == null)
				return;
			insertNodeByName(name, ObjektArt.MESS_GERAET);
		}
	}

	public class NewLocationAction extends JMainFrame.NewNodeByNameAction {
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			logger.debug("NewLocationAction");
			String name = getName(ObjektArt.MESS_ORT);
			if (name == null)
				return;
			insertNodeByName(name, ObjektArt.MESS_ORT);
		}
	}

	public class NewUnitAction extends JMainFrame.NewNodeByNameAction {
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			logger.debug("NewUnitAction");
			String name = getName(ObjektArt.MESS_EINHEIT);
			if (name == null)
				return;
			insertNodeByName(name, ObjektArt.MESS_EINHEIT);
		}
	}

	public class NewTargetAction extends JMainFrame.NewNodeByNameAction {
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			logger.debug("NewTargetAction");
			String name = getName(ObjektArt.MESS_ZIEL);
			if (name == null)
				return;
			insertNodeByName(name, ObjektArt.MESS_ZIEL);
		}
	}

	private class NewNodeAction extends JDefaultAction {
		JObjektTree tree;
		ObjektTreeModel treeModel;
		DefaultMutableTreeNode parent;

		public void actionPerformed(ActionEvent e) {
			tree = treePane.getCurrentTree();
			treeModel = (ObjektTreeModel) tree.getModel();
			parent = (DefaultMutableTreeNode) tree.getLastSelectedNode();
			if (parent == null)
				parent = (DefaultMutableTreeNode) treeModel.getRoot();
		}

		protected void selectNewNode(DefaultMutableTreeNode node) {
			TreePath path = new TreePath(node.getPath());
			tree.setSelectionPath(path);
			tree.scrollPathToVisible(path);
		}

	}

	private class NewNodeByNameAction extends JMainFrame.NewNodeAction {

		protected void insertNodeByName(String name, ObjektArt objektArt) {
			treeModel.insertNodeInto(name, objektArt.toString(), parent);
		}

		protected String getName(ObjektArt art) {
			JNameObjektDialog d = new JNameObjektDialog(JMainFrame.this, art);
			d.setTitle("New");
			d.setLocationRelativeTo(JMainFrame.this);
			d.setVisible(true);
			if (d.isOkPressed()) {
				return d.getName();
			} else {
				return null;
			}
		}

		protected void insertNodesByName(ArrayList<String> names,
				ObjektArt objektArt) {
			for (String name : names) {
				treeModel.insertNodeInto(name, objektArt.toString(), parent);
			}
		}

		protected ArrayList<String> getNames(ObjektArt art) {
			JNewObjektsDialog d = new JNewObjektsDialog(JMainFrame.this, art);
			d.setLocationRelativeTo(JMainFrame.this);
			d.setVisible(true);
			if (d.isOkPressed()) {
				return d.getNames();
			} else {
				return null;
			}
		}

	}

	public class NewMessungOrdnerAction extends JMainFrame.NewNodeByNameAction {
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			logger.debug("NewMessungOrdnerAction");
			String name = getName(ObjektArt.MESSUNG_ORDNER);
			if (name == null) {
				return;
			} else {
				insertNodeByName(name, ObjektArt.MESSUNG_ORDNER);
			}

		}
	}

	public class NewMessungLabordatenAction extends
			JMainFrame.NewNodeByNameAction {
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			logger.debug("NewMessungLabordatenAction");
			ArrayList<String> names = getNames(ObjektArt.MESSUNG_LABORDATEN);
			if (names != null) {
				insertNodesByName(names, ObjektArt.MESSUNG_LABORDATEN);
			}

		}
	}

	public class NewMessungMassendatenAction extends
			JMainFrame.NewNodeByNameAction {
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			logger.debug("NewMessungMassendatenAction");
			ArrayList<String> names = getNames(ObjektArt.MESSUNG_MASSENDATEN);
			if (names != null) {
				insertNodesByName(names, ObjektArt.MESSUNG_MASSENDATEN);
			}

		}
	}

	public ObjektNode getObjektofDetail() {
		if (detailForm == null) {
			return null;
		} else {
			return detailForm.getObjektNode();
		}
	}

	public void showPropertiesofSelected() {
		ObjektNode oNode = getObjektofDetail();
		if ((oNode != null) && oNode.equals(lastSelectedNode)) {
			viewPropertiesAction.actionPerformed(null);
		}
	}

	public class RenameNodeAction extends JDefaultAction {
		public void actionPerformed(ActionEvent e) {
			JNameObjektDialog d = new JNameObjektDialog(JMainFrame.this,
					lastSelectedNode.getObjektart());
			d.setTitle("Rename");
			d.setName(lastSelectedNode.getDe());
			d.setLocationRelativeTo(JMainFrame.this);
			d.setVisible(true);
			if (d.isOkPressed() && d.getName() != null) {
				logger.debug("Rename Node to :" + d.getName());
				ObjektTreeModel model = (ObjektTreeModel) treePane
						.getCurrentTree().getModel();
				model.renameNode(treePane.getCurrentTree()
						.getLastSelectedNode(), d.getName());
				treePane.getCurrentTree().repaint();
				showPropertiesofSelected();
			}
		}
	}

	public class RefreshNodeAction extends JDefaultAction {
		public void actionPerformed(ActionEvent e) {
			logger.debug("Refresh Node");
			treePane.getCurrentTree().refreshCurrentNode();
		}
	}

	public class DeleteNodeAction extends JDefaultAction {
		DefaultMutableTreeNode cNode;

		public void actionPerformed(ActionEvent e) {
			JOptionPane pane = new JOptionPane("Do you really want to delete "	+ lastSelectedNode.getDe() + " ?",	JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);																				// Do
			JDialog dialog = pane.createDialog(JMainFrame.this, "Goat");
			dialog.setVisible(true);
			Integer i = (Integer) pane.getValue();
			if (i.intValue() == JOptionPane.OK_OPTION) {
				logger.debug("Delete Node");
				ObjektTreeModel model = (ObjektTreeModel) treePane.getCurrentTree().getModel();
				cNode = treePane.getCurrentTree().getLastSelectedNode();
				model.removeNodeFromParent(cNode);
				ObjektNode oNode = getObjektofDetail();
				if ((oNode != null)
						&& oNode.equals((ObjektNode) cNode.getUserObject())) {
					unloadForm();
				}
			}
		}

	}

	public class CleanMessungAction extends JDefaultAction {
		DefaultMutableTreeNode cNode;

		public void actionPerformed(ActionEvent e) {
			JOptionPane pane = new JOptionPane("Do you really want to clean "
					+ lastSelectedNode.getDe() + " ?",
					JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);// To
																				// Do
			JDialog dialog = pane.createDialog(JMainFrame.this, "Goat");
			dialog.setVisible(true);
			Integer i = (Integer) pane.getValue();
			if (i.intValue() == JOptionPane.OK_OPTION) {
				logger.debug("Clean Node");
				cNode = treePane.getCurrentTree().getLastSelectedNode();
				// Identify Node Type
				ObjektNode n = (ObjektNode) cNode.getUserObject();
				try {

					if (n.getObjektart() == ObjektArt.MESSUNG_MASSENDATEN) {
						Vector params = new Vector();
						params.add(n.getId());
						Boolean bol = (Boolean) Client
								.getInstance()
								.getXmlRpcClient()
								.execute("MassenTableHandler.removeAllRows",
										params);
					} else if (n.getObjektart() == ObjektArt.MESSUNG_LABORDATEN) {
						Vector params = new Vector();
						params.add(n.getId());
						Boolean bol = (Boolean) Client
								.getInstance()
								.getXmlRpcClient()
								.execute("LaborTableHandler.removeAllRows",
										params);
					} else {
						MsgBox.error(JMainFrame.this, "Can't clean series of "
								+ n.getObjektart());
					}

				} catch (XmlRpcException ex) {
					logger.error(ex.getMessage());
					MsgBox.error(JMainFrame.this, "Failed to clean rows of "
							+ n.getDe());
				}

			}
		}

	}

	public class SearchAction extends JDefaultAction {
		public void actionPerformed(ActionEvent e) {
			// To Do
			if (searchDialog == null) {
				searchDialog = new JSearchObjektDialog(JMainFrame.this, false);
			}
			searchDialog.setVisible(true);
		}
	}

	public class EditAnyAction extends JDefaultAction {
		public void actionPerformed(ActionEvent e) {
			logger.debug(e.getActionCommand());
			try {
				if (editAnyStatus == null)
					editAnyStatus = new JEditAnyStatusDialog(JMainFrame.this);
				editAnyStatus.setVisible(true);
			} catch (JEditAnyStatusException x) {
				logger.error(x.getMessage());
				MsgBox.error(JMainFrame.this, x.getMessage());
				return;
			}

		}
	}

	public class ChangePasswordAction extends JDefaultAction {
		public void actionPerformed(ActionEvent e) {
			logger.debug(e.getActionCommand());
			JChangePwdDialog d = new JChangePwdDialog(JMainFrame.this);
			d.setVisible(true);
		}
	}

	public class ExitAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			logger.debug("Close Application ...");
			if (Client.getInstance().isConnected())
				terminateSessionAction.actionPerformed(null);
			pref.putInt("Position_X", JMainFrame.this.getLocationOnScreen().x);
			pref.putInt("Position_Y", JMainFrame.this.getLocationOnScreen().y);
			pref.putInt("SplitPane", splitPane.getDividerLocation());
			pref.putInt("Height", JMainFrame.this.getHeight());
			pref.putInt("Width", JMainFrame.this.getWidth());
			dispose();
			System.exit(0);
		}
	}

	private void loadForm(LoadableForm form) {

		if (!form.loadData()) {
			MsgBox.warn(JMainFrame.this, "Can't load form for "	+ form.getObjektNode().getDe() + ".");
			return;
		}
		rightPanel.removeAll();
		rightPanel.add((JPanel) form, BorderLayout.CENTER);
		detailForm = form;
		rightPanel.validate();
		rightPanel.repaint();
	}

	public LoadableForm getDetailForm() {
		return detailForm;
	}

	private void unloadForm() {
		detailForm = null;
		rightPanel.removeAll();
		rightPanel.validate();
		rightPanel.repaint();

	}

	public class ViewPropertiesAction extends JDefaultAction {
		public void actionPerformed(ActionEvent e) {
			logger.debug("PropertiesAction ....");
			if (lastSelectedNode == null)
				return;

			if (lastSelectedNode.getObjektart().isExtern()) {
				String url = xmlHelper.getUrl(lastSelectedNode.getId(),	lastSelectedNode.getObjektart());
				try {
					Runtime.getRuntime().exec(
							"rundll32 url.dll,FileProtocolHandler " + url);
				} catch (IOException i) {
					logger.error(i.getMessage());
				}

			} else {
				LoadableForm f = new JPropertiesPanel(JMainFrame.this);
				f.setObjektNode(lastSelectedNode);
				loadForm(f);
			}

		}

	}

	/**
	 * Getter for property statusLabel.
	 * 
	 * @return Value of property statusLabel.
	 * 
	 */
	public javax.swing.JLabel getStatusLabel() {
		return statusLabel;
	}

	/**
	 * Setter for property statusLabel.
	 * 
	 * @param statusLabel
	 *            New value of property statusLabel.
	 * 
	 */
	public void setStatusLabelText(String msg) {
		statusLabel.setText(msg);
	}

	/**
	 * Setter for property statusLabel.
	 * 
	 * @param statusLabel
	 *            New value of property statusLabel.
	 * 
	 */
	public void setStatusLabel(JLabel statusLabel) {
		this.statusLabel = statusLabel;
	}

	/**
	 * Getter for property treePane.
	 * 
	 * @return Value of property treePane.
	 * 
	 */
	public JTreeTabbedPane getTreePane() {
		return treePane;
	}

	/**
	 * Setter for property treePane.
	 * 
	 * @param treePane
	 *            New value of property treePane.
	 * 
	 */
	public void setTreePane(JTreeTabbedPane treePane) {
		this.treePane = treePane;
	}

	/**
	 * Getter for property lastSelectedNode.
	 * 
	 * @return Value of property lastSelectedNode.
	 * 
	 */
	public ObjektNode getLastSelectedNode() {
		return lastSelectedNode;
	}

	/**
	 * Setter for property lastSelectedNode.
	 * 
	 * @param lastSelectedNode
	 *            New value of property lastSelectedNode.
	 * 
	 */
	public void setLastSelectedNode(ObjektNode lastSelectedNode) {
		this.lastSelectedNode = lastSelectedNode;
	}

	private class ObjektSelectionListener implements TreeSelectionListener {
		public synchronized void valueChanged(TreeSelectionEvent e) {
			JObjektTree tree = (JObjektTree) e.getSource();
			getTreePane().setCurrentTree(tree);
			lastSelectedNode = (ObjektNode) tree.getLastSelectedUserObjekt();

			// OpenPanel Action
			if (lastSelectedNode != null) {
				boolean fullAccess = lastSelectedNode.hasFullAccess();
				newUnitAction.setEnabled(lastSelectedNode.getObjektart()
						.equals(ObjektArt.MESS_EINHEIT) && fullAccess);
				newTargetAction.setEnabled(lastSelectedNode.getObjektart()
						.equals(ObjektArt.MESS_ZIEL) && fullAccess);
				newCompartmentAction.setEnabled(lastSelectedNode
						.getObjektart().equals(ObjektArt.MESS_KOMPARTIMENT)
						&& fullAccess);
				newLocationAction.setEnabled(lastSelectedNode.getObjektart()
						.equals(ObjektArt.MESS_ORT) && fullAccess);
				newDeviceAction.setEnabled(lastSelectedNode.getObjektart()
						.equals(ObjektArt.MESS_GERAET) && fullAccess);

				boolean isMessungOrdner = lastSelectedNode.getObjektart()
						.equals(ObjektArt.MESSUNG_ORDNER);
				boolean isMessung = lastSelectedNode.getObjektart().equals(
						ObjektArt.MESSUNG_MASSENDATEN)
						|| lastSelectedNode.getObjektart().equals(
								ObjektArt.MESSUNG_LABORDATEN);

				newMessungOrdnerAction
						.setEnabled(isMessungOrdner && fullAccess);
				newMessungLabordatenAction.setEnabled(isMessungOrdner
						&& fullAccess);
				newMessungMassendatenAction.setEnabled(isMessungOrdner
						&& fullAccess);
				cleanMessungAction.setEnabled(isMessung && fullAccess);
				viewPropertiesAction.setEnabled(true);
				renameNodeAction.setEnabled(fullAccess);
				deleteNodeAction.setEnabled(fullAccess
						&& tree.getLastSelectedNode().isLeaf());
				refreshNodeAction.setEnabled(true);

			} else {
				viewPropertiesAction.setEnabled(false);
				renameNodeAction.setEnabled(false);
				deleteNodeAction.setEnabled(false);
				refreshNodeAction.setEnabled(true);

				newMessungOrdnerAction.setEnabled(false);
				newMessungLabordatenAction.setEnabled(false);
				newMessungMassendatenAction.setEnabled(false);
				cleanMessungAction.setEnabled(false);

			}

		} // end value changed

	}

	private class ObjektMouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				treePopupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}

		public void mouseClicked(MouseEvent e) {
			JObjektTree tree = (JObjektTree) e.getSource();
			lastSelectedNode = (ObjektNode) (tree.getLastSelectedUserObjekt());
			if (lastSelectedNode != null && e.getClickCount() == 2) {
				viewPropertiesAction.actionPerformed(null);
			}
		}
	}

	public XmlRpcClient getXmlClient() {
		return Client.getInstance().getXmlRpcClient();
	}
	
	private Vector getChildMessungen() {
		JObjektTree tr = treePane.getCurrentTree();
		ObjektTreeModel m = (ObjektTreeModel) tr.getModel();
		Vector c = new Vector();
		DefaultMutableTreeNode node = tr.getLastSelectedNode();
		if (node.isLeaf()) {
			c.add(node.getUserObject());
		} else {
			m.getChildMessungen(node, c);
		}
		return c;
	}

}
