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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;

import de.unibayreuth.bayceer.bayeos.client.Client;
import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.TimeFilter;
import de.unibayreuth.bayeos.goat.JMainFrame;

/**
 *
 * @author  oliver
 */
public class XmlRpcObjektNodeHelper implements ObjektNodeHelper  {
        
    private Vector webFilter;
    protected final static Logger logger = Logger.getLogger("XmlRpcObjektNodeHelper.class");
    private static final Preferences pref = Preferences.userNodeForPackage(JMainFrame.class); 
    
    /** Creates a new instance of XmlRpcObjektNodeHelper */    
    
    
    public XmlRpcObjektNodeHelper() {        
        this.webFilter = new Vector(1);
    }
    
    private void addFilterParams(Vector params){
         if (pref.getBoolean("tree_use_filter", false)){            
            params.addElement(new Boolean(pref.getBoolean("tree_only_active", false)));

            if (pref.getBoolean("tree_missing_label", false)){
              params.addElement(pref.get("tree_missing_interval", "Day"));    
            } else {
              params.add(null)  ;              
            }
            if (pref.getBoolean("tree_time_filter", false)){
                TimeFilter t = new TimeFilter(new java.util.Date(pref.getLong("tree_time_filter_start",0)), 
                new java.util.Date(pref.getLong("tree_time_filter_end",0)));
                params.add(t.getVector());
            } else {
              params.add(null)  ;              
            }
        } else {
            params.add(null);
            params.add(null);
            params.add(null);
        }
    }
    
    public ObjektNode getRoot(String art) throws ObjektNodeHelperException {
        try {
        logger.debug("getRoot()");
        Vector params = new Vector();
        params.addElement(art);
        addFilterParams(params);
        Vector row = (Vector)Client.getXmlRpcClient().execute("TreeHandler.getRoot",params);
        if (row == null) throw new ObjektNodeHelperException("No root node available", null);
        return new ObjektNode(row);
        } catch (XmlRpcException e){
            throw new ObjektNodeHelperException(e.getMessage(), e);
        } catch (ClassCastException e) {
            throw new ObjektNodeHelperException(e.getMessage(), e);
        }
    }
    
    public ObjektNode[] getChilds(ObjektNode node) throws ObjektNodeHelperException {
      try {
       logger.debug("getChilds(" + node.getDe() + ")");
       Vector params = new Vector();
       params.addElement(node.getId());
       params.addElement(node.getObjektart().toString());              
       addFilterParams(params);
       Vector rows = (Vector)Client.getXmlRpcClient().execute("TreeHandler.getChilds",params);
       ObjektNode[] obj = new ObjektNode[rows.size()];
       int i = 0;
       for (Enumeration er=rows.elements(); er.hasMoreElements(); ) {
           ObjektNode o =  new ObjektNode(((Vector)er.nextElement()));
           obj[i++] = o;
       }
       return obj;
        } catch (XmlRpcException e){
            throw new ObjektNodeHelperException(e.getMessage(), e);
        }
        
      
    }
    
    public boolean renameNode(ObjektNode o, String name) throws ObjektNodeHelperException {
        logger.debug("renameNode()");
        Vector params = new Vector();
        params.addElement(o.getId());
        params.addElement(o.getObjektart().toString());
        params.addElement(name);
        try {
        Boolean bol = (Boolean)Client.getXmlRpcClient().execute("TreeHandler.renameNode",params);
        o.setDe(name);
        return bol.booleanValue();
        } catch (XmlRpcException e){
            throw new ObjektNodeHelperException(e.getMessage(), e);
        }     
     }
    
     public boolean deleteNode(ObjektNode node) throws ObjektNodeHelperException  {
        logger.debug("deleteNode()");
        Vector params = new Vector();
        params.addElement(node.getId());
        try {
        Boolean bol = (Boolean)Client.getXmlRpcClient().execute("TreeHandler.deleteNode",params);
        return bol.booleanValue();
        } catch (XmlRpcException e){
            throw new ObjektNodeHelperException(e.getMessage(), e);
        } 
         
     }
     
     public ObjektNode newNode(ObjektNode parent, String name, String art) throws ObjektNodeHelperException {
        try {
        logger.debug("newNode()");
        Vector params = new Vector();
        params.addElement(art);
        params.addElement(name);      
        params.addElement(parent.getId());
        Vector row = (Vector)Client.getXmlRpcClient().execute("TreeHandler.newNode",params);
        return new ObjektNode(row);
        } catch (XmlRpcException e){
            throw new ObjektNodeHelperException(e.getMessage(), e);
        }     
                  
     }
     
     
    
     private Vector getInheritedParentIds(Integer id, String art) throws ObjektNodeHelperException {
        logger.debug("getInheritedParentIds()");
        Vector params = new Vector();
        params.addElement(id);
        params.addElement(art);
        try {
        Vector row = (Vector)Client.getXmlRpcClient().execute("TreeHandler.getInheritedParentIds",params);
        if (row == null || ((String)row.elementAt(0)).equalsIgnoreCase("null")) 
            throw new ObjektNodeHelperException("No parent available", null);
        return row;
        } catch (XmlRpcException e){
            throw new ObjektNodeHelperException(e.getMessage(), e);
        }     
       
   }

     public boolean isLeaf(Object object) throws ObjektNodeHelperException  {
      ObjektNode node = (ObjektNode)((DefaultMutableTreeNode)object).getUserObject();
      if (node.getObjektart() == ObjektArt.MESSUNG_MASSENDATEN  || node.getObjektart() == ObjektArt.MESSUNG_LABORDATEN) {
         return true;
     } else {
         return false;
     }
     }
     
     public TreePath getTreePath(DefaultTreeModel model, Integer objId, String art) throws ObjektNodeHelperException {
       DefaultObjektNode node = (DefaultObjektNode)model.getRoot();
              
       logger.debug("getTreePath(" + objId + ")");

       Vector parentIds = getInheritedParentIds(objId,art);      
       Collections.reverse(parentIds);
       parentIds.add(String.valueOf(objId));
       
       List list = new ArrayList();
       Iterator it = parentIds.iterator();
       
       // Root Knoten
       if (it.hasNext()) {
          node.loadChildren();
          list.add(node);
       } 

       while(it.hasNext()){
        int id = Integer.valueOf((String)it.next()).intValue();
        // child des letzten knotens vergleichen
        if (node.getChildCount() >= 0) {             
         for (Enumeration e=node.children(); e.hasMoreElements(); ) {
          DefaultObjektNode child = (DefaultObjektNode)e.nextElement();
          ObjektNode objekt = (ObjektNode)child.getUserObject();
          if ( objekt.getId().intValue() == id){                    
           child.loadChildren();
           list.add(child);
           node = child;
           break;
          };
         }
        }
       }
      TreePath path = new TreePath(list.toArray());
      return path;
     }
     
     public boolean moveNode(ObjektNode o, ObjektNode parent) throws ObjektNodeHelperException {
        logger.debug("moveNode(" + o.getId() + ";" + parent.getId() + ")");
        Vector params = new Vector();
        params.addElement(o.getId());
        params.addElement(parent.getId());        
        try {
        return ((Boolean)Client.getXmlRpcClient().execute("TreeHandler.moveNode",params)).booleanValue();
        } catch (XmlRpcException e){
            throw new ObjektNodeHelperException(e.getMessage(), e);
        }     
         
     }   
     
     public boolean linkNode(ObjektNode o, ObjektNode parent) throws ObjektNodeHelperException {
        logger.debug("linkNode(" + o.getId() + ";" + parent.getId() + ")");
        Vector params = new Vector();
        params.addElement(o.getId());
        params.addElement(parent.getId());        
        try {
        return ((Boolean)Client.getXmlRpcClient().execute("TreeHandler.linkNode",params)).booleanValue();
        } catch (XmlRpcException e){
            throw new ObjektNodeHelperException(e.getMessage(), e);
        }     
         
     }  
     
     public Integer copyNode(ObjektNode o, ObjektNode parent) throws ObjektNodeHelperException {
        logger.debug("copyNode(" + o.getId() + ";" + parent.getId() + ")");
        Vector params = new Vector();
        params.addElement(o.getId());
        params.addElement(parent.getId());               
        try {
            return (Integer)Client.getXmlRpcClient().execute("TreeHandler.copyNode",params);
        } catch (XmlRpcException e){
            throw new ObjektNodeHelperException(e.getMessage(), e);
        }     
         
     }     
     
        
     
     
    
     
     
}
