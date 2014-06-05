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
 * TransferableNode.java
 *
 * Created on 21. Juli 2004, 12:00
 */
package de.unibayreuth.bayeos.goat.tree;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.Arrays;

import javax.swing.tree.DefaultMutableTreeNode;

public class TransferableNode implements Transferable {
     public static final DataFlavor NODE_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, "Node");
     private DefaultMutableTreeNode node;
    private DataFlavor[] flavors = { NODE_FLAVOR };

 public TransferableNode(DefaultMutableTreeNode nd) {
            node = nd;
      }  

     public synchronized Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
               if (flavor == NODE_FLAVOR) {
                    return node;
            }
               else {
                  throw new UnsupportedFlavorException(flavor);   
                }                       
        }

       public DataFlavor[] getTransferDataFlavors() {
          return flavors;
 }

       public boolean isDataFlavorSupported(DataFlavor flavor) {
               return Arrays.asList(flavors).contains(flavor);
 }
}
 

