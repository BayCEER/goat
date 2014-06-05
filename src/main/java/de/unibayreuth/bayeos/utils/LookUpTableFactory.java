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
 * LookUpUtils.java
 *
 * Created on 6. Mai 2004, 11:47
 */

package de.unibayreuth.bayeos.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;

import de.unibayreuth.bayceer.bayeos.client.Client;

/**
 *
 * @author  oliver
 */
public final class LookUpTableFactory {
    private static LookUpTableFactory factory = null;
    protected final static Logger logger = Logger.getLogger("LookUpTableFactory.class");
      
    private static Vector aggrf, aggri, status,intervalTypes, crs;
    
    
    /** Creates a new instance of LookUpUtils */
    private  LookUpTableFactory() {
             
    }
    
    public static HashMap getHash(Vector v){
        HashMap m = new HashMap(v.size());
        Iterator it = v.iterator();
        while(it.hasNext()){
            Vector r = (Vector)it.next(); 
            m.put(r.elementAt(0),r.elementAt(1));
        }
        return m;
    }
    
    public static Vector getAggregationsFunktionen(){
     if (aggrf == null) aggrf = getAggrf();
     return aggrf;
    }
    
    public static Vector getAggregationsIntervalle(){
     if (aggri == null) aggri = getAggri();
     return aggri;
    }
    
    public static Vector getStatus(){
        if (status == null) status = getStat();
        return status;
    }
    
    public static Vector getCRS(){
    	if (crs == null) crs = getCoordinateRef();
    	return crs;
    }
    
    private static Vector getCoordinateRef() {
    	try {
            return (Vector)Client.getInstance().getXmlRpcClient().execute("LookUpTableHandler.getCRS",new Vector());        
            } catch(XmlRpcException e){
                MsgBox.error(e.getMessage());
                return null;           
            }    	
	}

	private static Vector getStat(){
        try {
        return (Vector)Client.getInstance().getXmlRpcClient().execute("LookUpTableHandler.getStatus",new Vector());        
        } catch(XmlRpcException e){
            MsgBox.error(e.getMessage());
            return null;
        } 
    }
    
    public static Vector getIntervalTyps(){
        if (intervalTypes == null) intervalTypes = getInterval();
        return intervalTypes;
    }
    
    private static Vector getInterval(){
        try {
        return (Vector)Client.getInstance().getXmlRpcClient().execute("LookUpTableHandler.getIntervalTypes",new Vector());        
        } catch(XmlRpcException e){
            MsgBox.error(e.getMessage());
            return null;
        } 
    }
    
    
    private static Vector getAggrf(){
        try {
        return (Vector)Client.getInstance().getXmlRpcClient().execute("LookUpTableHandler.getAgrFunktionen", new Vector());        
        } catch(XmlRpcException e){
            MsgBox.error(e.getMessage());
            return null;
        } 
    }
    
    private static Vector getAggri(){
        try {
        return (Vector)Client.getInstance().getXmlRpcClient().execute("LookUpTableHandler.getAgrIntervalle", new Vector());        
        } catch(XmlRpcException e){
            MsgBox.error(e.getMessage());
            return null;
        } 
    }
    
    
      
    public static void initFactory(){
        factory = new LookUpTableFactory();
    }
    
    public static void disposeFactory(){
    	factory = null;
    }

	public static Vector getTimeZones() {
		try {
	        return (Vector)Client.getInstance().getXmlRpcClient().execute("LookUpTableHandler.getTimeZones",new Vector());        
	        } catch(XmlRpcException e){
	            MsgBox.error(e.getMessage());
	            return null;
	        }
	}
            
    

}
