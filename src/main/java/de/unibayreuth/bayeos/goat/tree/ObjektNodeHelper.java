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

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/*
 * ObjektNodeHelper.java
 *
 * Created on 29. August 2002, 11:19
 */

/**
 *
 * @author  oliver
 */
public interface ObjektNodeHelper {
    
    public ObjektNode[] getChilds(ObjektNode node) throws ObjektNodeHelperException;
    public ObjektNode getRoot(String art) throws ObjektNodeHelperException;
    public boolean renameNode(ObjektNode node, String name) throws ObjektNodeHelperException;
    public boolean deleteNode(ObjektNode node) throws ObjektNodeHelperException;
    public ObjektNode newNode(ObjektNode parent, String name, String art) throws ObjektNodeHelperException;
    public boolean moveNode(ObjektNode node,ObjektNode target) throws ObjektNodeHelperException;    
    public boolean linkNode(ObjektNode node,ObjektNode target) throws ObjektNodeHelperException;    
    public Integer copyNode(ObjektNode node,ObjektNode target) throws ObjektNodeHelperException;    
    public TreePath getTreePath(DefaultTreeModel model, Integer objId, String art) throws ObjektNodeHelperException;
    
    
        
}
