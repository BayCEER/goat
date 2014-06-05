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

import org.apache.log4j.Logger;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;


/*
 * DefaultObjektNode.java
 *
 * Created on 29. August 2002, 11:27
 */

/**
 *
 * @author  oliver
 */
public class DefaultObjektNode extends javax.swing.tree.DefaultMutableTreeNode {
    
    private final ObjektNodeHelper helper;
    private boolean isLoaded = false;
    
    protected final static Logger logger = Logger.getLogger("DefaultObjektNode.class");
           
    /** Creates a new instance of DefaultObjektNode */
    public DefaultObjektNode(final Object userObject, final ObjektNodeHelper helper) {
        super(userObject);
        this.helper = helper;
    }
    
    public int getChildCount() {    	
      if(!isLoaded) {
	    loadChildren();            
	  }
      return super.getChildCount();         		
    }
    
    
    
    protected void loadChildren() {
       if(isLoaded) return;
       try {
        if (logger.isDebugEnabled()) {
                logger.debug("loadChildren(" + getUserObject() + ")");
        }
	DefaultObjektNode  newNode;
	ObjektNode[] obj = helper.getChilds((ObjektNode)getUserObject());
      	for(int counter = 0; counter < obj.length ; counter++) {
	    newNode = new DefaultObjektNode(obj[counter],helper);
	    /* Don't use add() here, add calls insert(newNode, getChildCount())
	       so if you want to use add, just be sure to set hasLoaded = true
	       first. */
	    insert(newNode, counter);
	}
	/* This node has now been loaded, mark it so. */
	isLoaded = true;
        } catch (ObjektNodeHelperException e) {
           logger.error(e.getMessage());
       }
       
    }
    
       
    protected void unloadChildren() {
       if(!isLoaded) return;
       if (logger.isDebugEnabled()) {
                logger.debug("unloadChildren(" + getUserObject() + ")");
        }
	removeAllChildren();        
	/* This node has now been unloaded, mark it so. */
	isLoaded = false;
    }


     /** Getter for property isLoaded.
      * @return Value of property isLoaded.
      *
      */
     public boolean isIsLoaded() {
         return isLoaded;
     }     
    
     /** Setter for property isLoaded.
      * @param isLoaded New value of property isLoaded.
      *
      */
     public void setLoaded(boolean isLoaded) {
         this.isLoaded = isLoaded;
     }     
          
     public boolean isLeaf(){    	 
         ObjektNode o = (ObjektNode)getUserObject();
         return !o.getHasChild().booleanValue();
     }
     
     public boolean getAllowsChildren() {
         ObjektNode o = (ObjektNode)getUserObject();
         if ( o.getObjektart().equals(ObjektArt.MESSUNG_MASSENDATEN) || o.getObjektart().equals(ObjektArt.MESSUNG_LABORDATEN)){
             return false;
         } else {
             return true;
         }
     }
     
}
