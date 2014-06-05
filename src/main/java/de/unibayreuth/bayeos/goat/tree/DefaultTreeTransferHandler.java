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
 * DefaultTreeTransferHandler.java
 *
 * Created on 21. Juli 2004, 12:03
 */
package de.unibayreuth.bayeos.goat.tree;

import java.awt.Point;
import java.awt.dnd.DnDConstants;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;

public class DefaultTreeTransferHandler extends AbstractTreeTransferHandler {

     public DefaultTreeTransferHandler(JObjektTree tree, int action) {
           super(tree, action, true);
      }

     public boolean canPerformAction(JObjektTree target, DefaultMutableTreeNode draggedNode, int action, Point location) {
           ObjektTreeModel model = (ObjektTreeModel)target.getModel();
           TreePath pathTarget = target.getPathForLocation(location.x, location.y);
           if (pathTarget == null) {
               target.setSelectionPath(null);
               return(false);
           }
           target.setSelectionPath(pathTarget);
           DefaultMutableTreeNode parentNode =(DefaultMutableTreeNode)pathTarget.getLastPathComponent();
           
            if(action == DnDConstants.ACTION_MOVE) {
                   if (!draggedNode.isRoot() && parentNode != draggedNode.getParent() && !draggedNode.isNodeDescendant(parentNode)) {
                    ObjektNode d = (ObjektNode)draggedNode.getUserObject();
                    ObjektNode p = (ObjektNode)parentNode.getUserObject();
                        if (d.getObjektart().equals(p.getObjektart()) && !d.getObjektart().equals(ObjektArt.MESSUNG_LABORDATEN) && !d.getObjektart().equals(ObjektArt.MESSUNG_MASSENDATEN)) {
                            return true;
                        } else if( (d.getObjektart().equals(ObjektArt.MESSUNG_LABORDATEN) || d.getObjektart().equals(ObjektArt.MESSUNG_MASSENDATEN)) &&
                            p.getObjektart().equals(ObjektArt.MESSUNG_ORDNER)){
                            return true;
                        } else {
                            return false ;
                        }
                   } else {
                       return false;
                   }
            } else if (action == DnDConstants.ACTION_COPY)       {
                if (!draggedNode.isRoot() && !draggedNode.equals(parentNode)) {
                    ObjektNode d = (ObjektNode)draggedNode.getUserObject();
                    ObjektNode p = (ObjektNode)parentNode.getUserObject();
                        if (d.getObjektart().equals(p.getObjektart()) && !d.getObjektart().equals(ObjektArt.MESSUNG_LABORDATEN) && !d.getObjektart().equals(ObjektArt.MESSUNG_MASSENDATEN)) {
                            return true;
                        } else if( (d.getObjektart().equals(ObjektArt.MESSUNG_LABORDATEN) || d.getObjektart().equals(ObjektArt.MESSUNG_MASSENDATEN)) &&
                            p.getObjektart().equals(ObjektArt.MESSUNG_ORDNER)){
                            return true;
                        } else {
                            return false ;
                        }
                   } else {
                       return false;
                   }
            } else {
                return false;
            }
     }

       public boolean executeDrop(JObjektTree target, DefaultMutableTreeNode draggedNode, DefaultMutableTreeNode newParentNode, int action) { 
           
           ObjektTreeModel model = (ObjektTreeModel)target.getModel();
           
           if (action == DnDConstants.ACTION_COPY) {
              JOptionPane pane = new JOptionPane("Do you really want to copy " + ((ObjektNode)draggedNode.getUserObject()).getDe() + " to " + 
              ((ObjektNode)newParentNode.getUserObject()).getDe() +  " ?",JOptionPane.WARNING_MESSAGE 
               ,JOptionPane.OK_CANCEL_OPTION);
              JDialog dialog = pane.createDialog(target, "Goat");
              dialog.show();
              Integer i = (Integer)pane.getValue();
            if (i.intValue() == JOptionPane.OK_OPTION) {            
               DefaultMutableTreeNode newNode = model.copyNode(draggedNode,newParentNode);               
               TreePath treePath = new TreePath(newNode);
               target.scrollPathToVisible(treePath);
               target.setSelectionPath(treePath);
               return(true);
            } else {
                return(false);
            }
           } else if (action == DnDConstants.ACTION_MOVE) {
               JOptionPane pane = new JOptionPane("Do you really want to move " + ((ObjektNode)draggedNode.getUserObject()).getDe() + " to " + 
              ((ObjektNode)newParentNode.getUserObject()).getDe() +  " ?",JOptionPane.WARNING_MESSAGE 
               ,JOptionPane.OK_CANCEL_OPTION);
              JDialog dialog = pane.createDialog(target, "Goat");
              dialog.show();
              Integer i = (Integer)pane.getValue();
            if (i.intValue() == JOptionPane.OK_OPTION) {            
               model.moveNode(draggedNode,newParentNode);
               TreePath treePath = new TreePath(draggedNode.getPath());
               target.scrollPathToVisible(treePath);
               target.setSelectionPath(treePath);
               target.refreshCurrentNode();
               return(true);
            } else {
                return(false);
            }
            } else {
               return(false);
           }
  }
}

