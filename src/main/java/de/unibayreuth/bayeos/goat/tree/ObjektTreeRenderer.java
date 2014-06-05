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
 * ObjektTreeRenderer.java
 *
 * Created on 19. September 2002, 17:47
 */

package de.unibayreuth.bayeos.goat.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.apache.batik.util.gui.resource.ResourceManager;
import org.apache.log4j.Logger;

import de.unibayreuth.bayeos.goat.ObjektIconFactory;


/**
 *
 * @author  oliver
 */
public class ObjektTreeRenderer extends DefaultTreeCellRenderer  {
    
    protected final static Logger logger = Logger.getLogger("ObjektTreeRenderer.class");

    protected final static String RESOURCES = "de.unibayreuth.bayeos.goat.JMainFrame";
    
    private final static java.util.Date now = new java.util.Date();
    
    
   
             
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
    
    
    
    public Component getTreeCellRendererComponent(
                        JTree tree,
                        Object value,
                        boolean sel,
                        boolean expanded,
                        boolean leaf,
                        int row,
                        boolean hasFocus) {

        super.getTreeCellRendererComponent(
                        tree, value, sel,
                        expanded, leaf, row,
                        hasFocus);
         DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
         ObjektNode o = (ObjektNode)(node.getUserObject());    
         if (node.isRoot()){
           setIcon(ObjektIconFactory.getIcon(o.getObjektart(),false));    
           setFont(getFont().deriveFont(Font.BOLD));
         } else {
           setIcon(ObjektIconFactory.getIcon(o.getObjektart(),false));    
           setFont(getFont().deriveFont((o.getActive().booleanValue())? Font.BOLD: Font.PLAIN));
           setForeground( (o.getRecordsMissing().booleanValue() && o.getActive().booleanValue()) ? Color.RED: Color.BLACK);           
         }
         setToolTipText(o.getId().toString());
	 return this;
    }
        
}

