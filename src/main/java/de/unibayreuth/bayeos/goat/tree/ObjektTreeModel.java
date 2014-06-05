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

import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayeos.utils.MsgBox;


public class ObjektTreeModel extends DefaultTreeModel
{
    ObjektNodeHelper helper;
    
    
    protected final static Logger logger = Logger.getLogger("ObjektTreeModel.class");
    
    /**
      * Creates a new instance of SampleTreeModel 
      */
    public ObjektTreeModel(ObjektNodeHelper helper) {
        super(null);
        this.helper = helper;
    }
    
    /**
      * Subclassed to message setString() to the changed path item.
      */
    public void valueForPathChanged(TreePath path, Object newValue) {
	/* Update the user object. */
       	DefaultMutableTreeNode aNode = (DefaultMutableTreeNode)path.getLastPathComponent();
        renameNode(aNode, (String)newValue);
    }
      
    public void insertNodeInto(String name, String art, DefaultMutableTreeNode parent)  {
        try { 
         DefaultMutableTreeNode newChild = new DefaultObjektNode(helper.newNode((ObjektNode)parent.getUserObject(),name,art),helper);
         super.insertNodeInto(newChild, parent,parent.getChildCount());
        } catch (ObjektNodeHelperException e) {
         logger.error(e.getMessage());
         MsgBox.error(e.getMessage());
        }
     }     
     
     public void removeNodeFromParent(DefaultMutableTreeNode node) {
         try { 
         DefaultMutableTreeNode  parent = (DefaultMutableTreeNode)node.getParent();
         if (parent == null) return; // Root darf nicht entfernt werden
         if (helper.deleteNode((ObjektNode)node.getUserObject())){
             super.removeNodeFromParent(node);
         }
         } catch (ObjektNodeHelperException e) {
          logger.error(e.getMessage());   
          MsgBox.error(e.getMessage());
        }
     }
     
     
     public void renameNode(DefaultMutableTreeNode node,String name)  {
        try {
         ObjektNode objektNode = (ObjektNode)node.getUserObject();
         if (helper.renameNode(objektNode,name))             	
            nodeChanged(node);
        } catch (ObjektNodeHelperException e) {
         logger.error(e.getMessage());   
         MsgBox.error(e.getMessage());
        }
     }
     
     public void getChildMessungen(DefaultMutableTreeNode node, Vector o){
                for(int i=0;i<node.getChildCount();i++){
                    DefaultMutableTreeNode t = (DefaultMutableTreeNode)node.getChildAt(i);                    
                    ObjektNode obj = (ObjektNode)t.getUserObject();
                    if (isLeaf(t) && (obj.getObjektart().equals(ObjektArt.MESSUNG_LABORDATEN) || 
                    obj.getObjektart().equals(ObjektArt.MESSUNG_MASSENDATEN))){
                        o.add(t.getUserObject());
                    } else {
                        getChildMessungen(t,o);
                    }                   
                }
     }
     
          
     public void moveNode(DefaultMutableTreeNode node, DefaultMutableTreeNode parent){
         try {
         // Database
         ObjektNode objektNode = (ObjektNode)node.getUserObject();
         ObjektNode objektParent = (ObjektNode)parent.getUserObject();
         helper.moveNode(objektNode,objektParent);               
           
         
         // Tree
         super.removeNodeFromParent(node);
         // An letzter Stelle anfügen
         super.insertNodeInto(node,parent,parent.getChildCount());
         
         } catch (ObjektNodeHelperException e) {
         logger.error(e.getMessage());   
         MsgBox.error(e.getMessage());
        }         
         
     }
     
     
     public void linkNode(DefaultMutableTreeNode node, DefaultMutableTreeNode parent){
         try {
         // Database
         ObjektNode objektNode = (ObjektNode)node.getUserObject();
         ObjektNode objektParent = (ObjektNode)parent.getUserObject();
         helper.linkNode(objektNode,objektParent);               
         
         // Tree
         //super.removeNodeFromParent(node);
         // An letzter Stelle anfügen
         super.insertNodeInto(node,parent,parent.getChildCount());
         
         } catch (ObjektNodeHelperException e) {
         logger.error(e.getMessage());   
         MsgBox.error(e.getMessage());
        }         
         
     }

      
     public DefaultMutableTreeNode copyNode(DefaultMutableTreeNode node, DefaultMutableTreeNode parent){
         
         try {
         
         ObjektNode oNode = (ObjektNode)node.getUserObject();
         ObjektNode oParent = (ObjektNode)parent.getUserObject();                  
                  
         Vector v = new Vector();
         v.add(Boolean.TRUE);
         v.add(Boolean.TRUE);         
         v.add(helper.copyNode(oNode,oParent));
         v.add(oParent.getId());
         v.add(oNode.getObjektart().toString());
         v.add(oNode.getDe());
         v.add(null);
         v.add(null);
         v.add(null);
         v.add(null);
         v.add(Boolean.FALSE);
         v.add(Boolean.FALSE);
         v.add(Boolean.FALSE);
         v.add(Boolean.FALSE);

         ObjektNode nObjektNode = new ObjektNode(v);
         
         //DefaultMutableTreeNode nNode = new DefaultMutableTreeNode(nObjektNode);         
         DefaultObjektNode nNode = new DefaultObjektNode(nObjektNode,helper);         
         
         // An letzter Stelle anfügen
         super.insertNodeInto(nNode,parent,(parent.getChildCount() > 1)?parent.getChildCount() -1:0);         
         
         return nNode; 
         } catch (ObjektNodeHelperException e) {
          MsgBox.error(e.getMessage());
          return null;
        }         
        
     }
     
}
