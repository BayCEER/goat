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
/*
 * JTreeTabbedPane.java
 *
 * Created on 21. November 2002, 12:49
 */

package de.unibayreuth.bayeos.goat.tree;

import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.batik.util.gui.resource.ResourceManager;
import org.apache.log4j.Logger;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayeos.utils.MsgBox;

/**
 *
 * @author  oliver
 */
public class JTreeTabbedPane extends JTabbedPane {
    
    final static Logger logger = Logger.getLogger(JTreeTabbedPane.class.getName()); 
    
    /**
     * The resource file name
     */
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
    
       
    
    private JFrame frame;
    
    private Hashtable objektTreePanels = new Hashtable(10);
    private JObjektTree currentTree = null;
    private boolean filterChanged = false;    
    
   
    /** Creates a new instance of JTreePanel */
    public JTreeTabbedPane(JFrame frame) {
        this.frame = frame;
        setBorder(null);        
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);        
        addObjektPanel(ObjektArt.MESSUNG_ORDNER);
        addObjektPanel(ObjektArt.MESS_GERAET);
        addObjektPanel(ObjektArt.MESS_KOMPARTIMENT);
        addObjektPanel(ObjektArt.MESS_ORT);
        addObjektPanel(ObjektArt.MESS_ZIEL);
        addObjektPanel(ObjektArt.MESS_EINHEIT);                                       
        addObjektPanel(ObjektArt.WEB_ORDNER);
        
                
        
        
        
        // Umschalten im Explorer
        addChangeListener(new javax.swing.event.ChangeListener(){
            public void stateChanged(ChangeEvent e)
            { 
                JTabbedPane explorer = (JTabbedPane)e.getSource();
                JObjektTreePanel tp = (JObjektTreePanel)explorer.getSelectedComponent();
                if (tp == null) return;
                currentTree = tp.getObjektTree();
                if (filterChanged) currentTree.refreshRoot();
                fireChangeEvent(currentTree);
            }
        });

    }
    
    private void fireChangeEvent(JObjektTree tree) {
        TreeSelectionListener[] listeners = tree.getTreeSelectionListeners();
        for (int i=0; i<listeners.length; i++) {
            listeners[i].valueChanged(new TreeSelectionEvent(tree,null,false,null,null));
        }
    }
    
    private void addObjektPanel(ObjektArt objektArt) {
        try {
        JObjektTreePanel objektTreePanel = new JObjektTreePanel(objektArt);
        addTab(objektTreePanel.getTitle(),objektTreePanel.getIcon(),objektTreePanel);    
        objektTreePanels.put(objektArt, objektTreePanel);  
        if (currentTree == null) {
            currentTree = objektTreePanel.getObjektTree();
        }
        
        fireChangeEvent(objektTreePanel.getObjektTree());
        } catch (ObjektNodeHelperException h) {
            MsgBox.error(frame, "Can't create panel:" + objektArt + "\n" + h.getMessage());
        }
        
    }
    
    public JObjektTree getCurrentTree() {
        return currentTree;
    }
    
    public JObjektTreePanel getObjektTreePanel(ObjektArt objektArt) {
        if (objektArt.equals(ObjektArt.MESSUNG_LABORDATEN) || objektArt.equals(ObjektArt.MESSUNG_MASSENDATEN)) {
          return (JObjektTreePanel)objektTreePanels.get(ObjektArt.MESSUNG_ORDNER);} 
        else if (objektArt.isExtern()){
          return (JObjektTreePanel)objektTreePanels.get(ObjektArt.WEB_ORDNER);        	  
        } else {
          return (JObjektTreePanel)objektTreePanels.get(objektArt);
        }
    }

    
    public Hashtable getObjektTreePanels() {
        return objektTreePanels;
    }
    
    public void showObjektPanel(ObjektArt objektArt) {
     try {
      JObjektTreePanel treePanel = getObjektTreePanel(objektArt);
      setSelectedComponent(treePanel);
     } catch (IllegalArgumentException e) {
         MsgBox.error(frame,e.getMessage());
     }
    }
    
    public void expandObjektNode(ObjektArt objektArt,Integer objId) {
     try {
     JObjektTreePanel treePanel = getObjektTreePanel(objektArt);
     if (treePanel == null) {
         MsgBox.warn(frame, "Couldn't get TreePanel for " + objektArt);
         return;
     }
     ObjektNodeHelper helper = treePanel.getHelper();     
     TreePath path = helper.getTreePath((DefaultTreeModel)treePanel.getObjektTree().getModel(),objId,objektArt.toString());
     treePanel.getObjektTree().expandPathTo(path);
     showObjektPanel(objektArt);
     } catch (ObjektNodeHelperException e) {
         MsgBox.error(frame, e.getMessage());
     }
    }
    
    /** Setter for property currentTree.
     * @param currentTree New value of property currentTree.
     *
     */
    public void setCurrentTree(JObjektTree currentTree) {
        this.currentTree = currentTree;
    }    
   
    /**
     * Getter for property filterChanged.
     * @return Value of property filterChanged.
     */
    public boolean isFilterChanged() {
        return filterChanged;
    }    
    
    /**
     * Setter for property filterChanged.
     * @param filterChanged New value of property filterChanged.
     */
    public void setFilterChanged(boolean filterChanged) {
        this.filterChanged = filterChanged;
    }    
   
}

    
