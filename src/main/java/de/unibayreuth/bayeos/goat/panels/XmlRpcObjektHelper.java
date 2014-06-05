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
 * XmlRpcObjektHelper.java
 *
 * Created on 12. Februar 2003, 18:08
 */

package de.unibayreuth.bayeos.goat.panels;

import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import de.unibayreuth.bayceer.bayeos.client.Client;
import de.unibayreuth.bayceer.bayeos.objekt.Objekt;
import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayceer.bayeos.objekt.ObjektFactory;
import de.unibayreuth.bayeos.utils.MsgBox;

/**
 *
 * @author  oliver
 */
public class XmlRpcObjektHelper implements ObjektHelper {
    
    private XmlRpcClient xmlClient;
    
    protected final static Logger logger = Logger.getLogger("XmlRpcObjektNodeHelper.class");
    
    
    /** Creates a new instance of XmlRpcObjektHelper */
    public XmlRpcObjektHelper() {
              
    }
    
    public Objekt createObjekt(ObjektArt art) {
        try {
        logger.debug("createObjekt(" + art + ")");
        Vector params = new Vector(1);
        params.addElement(art);
        Vector row = (Vector)Client.getXmlRpcClient().execute("ObjektHandler.createObjekt",params);
        return ObjektFactory.getObjekt(row);
        } catch (XmlRpcException e){
            MsgBox.error(e.getMessage());
            return null;
        }        
    }
    
    public boolean deleteObjekt(Integer Id) {
        try {
        logger.debug("deleteObjekt(" + Id + ")");
        Vector params = new Vector(1);
        params.addElement(Id);
        Vector row = (Vector)Client.getXmlRpcClient().execute("ObjektHandler.deleteObjekt",params);
        return ((Boolean)row.elementAt(0)).booleanValue();
        } catch (XmlRpcException e){
            MsgBox.error(e.getMessage());
            return false;
        }        
    }
    
    public Objekt getObjekt(Integer Id, ObjektArt objektArt) {       
       try {
        logger.debug("getObjekt(" + Id + ")");
        Vector params = new Vector(2);
        params.addElement(Id);
        params.addElement(objektArt.toString());
        Vector row = (Vector)Client.getXmlRpcClient().execute("ObjektHandler.getObjekt",params);
        return ObjektFactory.getObjekt(row);
        } catch (XmlRpcException e){
            MsgBox.error(e.getMessage());
            return null;
        } catch (ClassCastException e) {
            MsgBox.error("ClassCastException");
            return null;
        }
        
        
    }
    
    public String getUrl(Integer Id, ObjektArt objektArt) {       
       try {
        logger.debug("getUrl(" + Id + "," + objektArt + ")");
        Vector params = new Vector(2);
        params.addElement(Id);
        params.addElement(objektArt.toString());
        return (String)Client.getXmlRpcClient().execute("ObjektHandler.getExternUrl",params);        
        } catch (XmlRpcException e){
            MsgBox.error(e.getMessage());
            return null;
        }        
    }
    
    
    public boolean updateObjekt(Integer obj_id, ObjektArt art, Vector attributes) {
        try{
        logger.debug("updateObjekt(" + obj_id + ")");
        Vector params = new Vector(3);
        params.addElement(obj_id);
        params.addElement(art.toString());
        params.addElement(attributes);
        Boolean bol = (Boolean)Client.getXmlRpcClient().execute("ObjektHandler.updateObjekt",params);
        return (bol).booleanValue();
        } catch (XmlRpcException e){
            MsgBox.error(e.getMessage());
            return false;
        }      
    }    
  
   
    
}
