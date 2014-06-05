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

import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.log4j.Logger;


public class JObjektTree extends javax.swing.JTree
{
    
  protected final static Logger logger = Logger.getLogger("JObjektTree.class");
  private final static Insets autoscrollInsets = new Insets(20, 20, 20, 20); // insets
   
  public JObjektTree(TreeModel treeModel) 
  {
    super(treeModel);
    setBorder(null);
    getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    // ensure that the tree view displays tool tips
    ToolTipManager.sharedInstance().registerComponent(this);
  }
                                
   public void expandPathTo(TreePath path){
       logger.debug("expandPathTo:" + path.toString());
       expandPath(path);
       setSelectionPath(path);
       scrollPathToVisible(path);
   }     
  
   // drag and drop scrolling
   public void autoscroll(Point cursorLocation)  {
         Insets insets = getAutoscrollInsets();
          Rectangle outer = getVisibleRect();
             Rectangle inner = new Rectangle(outer.x+insets.left, outer.y+insets.top, outer.width-(insets.left+insets.right), outer.height-(insets.top+insets.bottom));
              if (!inner.contains(cursorLocation))  {
                 Rectangle scrollRect = new Rectangle(cursorLocation.x-insets.left, cursorLocation.y-insets.top, insets.left+insets.right, insets.top+insets.bottom);
                    scrollRectToVisible(scrollRect);
                }
       }

 public Insets getAutoscrollInsets()  {
          return (autoscrollInsets);
 }
   
  public void refreshCurrentNode() {
       TreePath  path = getSelectionPath();       
       if (path != null) {
         logger.debug(path.toString());
         DefaultObjektNode node = (DefaultObjektNode)getLastSelectedPathComponent();
         node.unloadChildren();
         ((DefaultTreeModel)getModel()).nodeStructureChanged(node);        
       }
   }
  public void refreshRoot(){
      TreeModel m = this.treeModel;
      DefaultObjektNode root = (DefaultObjektNode)getModel().getRoot();
      root.unloadChildren();
      ((DefaultTreeModel)getModel()).nodeStructureChanged(root);          
      
  }
   
    
   public ObjektNode getLastSelectedUserObjekt() {
    DefaultObjektNode node = (DefaultObjektNode)getLastSelectedPathComponent();
    if (node == null) return null; 
    return (ObjektNode)node.getUserObject();
   }
   
   /**
      * Returns the TreeNode instance that is selected in the tree.
      * If nothing is selected, null is returned.
      */
    public DefaultMutableTreeNode getLastSelectedNode() {
	TreePath   selPath = getSelectionPath();
	if(selPath != null)
	    return (DefaultMutableTreeNode)selPath.getLastPathComponent();
	return null;
    }
    
  
}
