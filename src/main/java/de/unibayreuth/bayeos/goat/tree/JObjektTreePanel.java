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
package de.unibayreuth.bayeos.goat.tree;


import java.awt.dnd.DnDConstants;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;

import org.apache.batik.util.gui.resource.ResourceManager;
import org.apache.log4j.Logger;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayeos.utils.ImageFactory;
import de.unibayreuth.bayeos.utils.ImageFactoryException;


    

public class JObjektTreePanel extends JPanel  {
    final static Logger logger = Logger.getLogger(JObjektTreePanel.class.getName()); 
    
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
    
        private JObjektTree objektTree;
        private JScrollPane scrollPane;
        private Icon icon;
        private String title;
        private ObjektArt objektArt;
        private ObjektNodeHelper helper;
        
        public JObjektTreePanel(ObjektArt objektArt) throws ObjektNodeHelperException {
        setBorder(null);
        this.objektArt = objektArt;        
        helper = new XmlRpcObjektNodeHelper();
        DefaultTreeModel model = new ObjektTreeModel(helper);
        ObjektNode root = helper.getRoot(objektArt.toString());
        if (root != null) {
            model.setRoot(new DefaultObjektNode(root, helper));
        }       
	    objektTree = new JObjektTree(model);
        // Allows Drag and Drop of Nodes 
        new DefaultTreeTransferHandler(objektTree,DnDConstants.ACTION_COPY_OR_MOVE);
        scrollPane = new JScrollPane(objektTree);
        scrollPane.setBorder(null);
        
        setLayout(new java.awt.BorderLayout());
        add(scrollPane, java.awt.BorderLayout.CENTER);
        try {
        icon = ImageFactory.getIcon(resources.getString("Tab." + objektArt + ".Icon"));
        title = resources.getString("Tab." + objektArt + ".Title");
        } catch (ImageFactoryException i) {
            logger.warn(i.getMessage());
        } catch (MissingResourceException r) {
            logger.warn(r.getMessage());
        }
        

     }
    
        /** Getter for property objektTree.
         * @return Value of property objektTree.
         *
         */
        public JObjektTree getObjektTree() {
            return objektTree;
        }        
    
        /** Setter for property objektTree.
         * @param objektTree New value of property objektTree.
         *
         */
        public void setObjektTree(JObjektTree objektTree) {
            this.objektTree = objektTree;
        }
        
        /** Getter for property scrollPane.
         * @return Value of property scrollPane.
         *
         */
        public javax.swing.JScrollPane getScrollPane() {
            return scrollPane;
        }
        
        /** Setter for property scrollPane.
         * @param scrollPane New value of property scrollPane.
         *
         */
        public void setScrollPane(javax.swing.JScrollPane scrollPane) {
            this.scrollPane = scrollPane;
        }
        
        /** Getter for property icon.
         * @return Value of property icon.
         *
         */
        public javax.swing.Icon getIcon() {
            return icon;
        }
        
        /** Setter for property icon.
         * @param icon New value of property icon.
         *
         */
        public void setIcon(javax.swing.Icon icon) {
            this.icon = icon;
        }
        
        /** Getter for property title.
         * @return Value of property title.
         *
         */
        public java.lang.String getTitle() {
            return title;
        }
        
        /** Setter for property title.
         * @param title New value of property title.
         *
         */
        public void setTitle(java.lang.String title) {
            this.title = title;
        }
        
        /** Getter for property objektArt.
         * @return Value of property objektArt.
         *
         */
        public ObjektArt getObjektArt() {
            return objektArt;
        }
        
        /** Setter for property objektArt.
         * @param objektArt New value of property objektArt.
         *
         */
        public void setObjektArt(ObjektArt objektArt) {
            this.objektArt = objektArt;
        }
        
        /**
         * Getter for property helper.
         * @return Value of property helper.
         */
        public ObjektNodeHelper getHelper() {
            return helper;
        }
        
        
        
 }


