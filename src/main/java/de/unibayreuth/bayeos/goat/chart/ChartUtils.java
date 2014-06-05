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
 * ChartUtils.java
 *
 * Created on 14. Juli 2004, 11:51
 */

package de.unibayreuth.bayeos.goat.chart;

import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;



/**
 *
 * @author  oliver
 */
public class ChartUtils {

    private static final Logger logger = Logger.getLogger(ChartUtils.class);    
    
   
     
    public static String getNumberAxisLabel(XmlRpcClient xmlClient, Integer Id){
        Vector param = new Vector();
        param.add(Id);
        String label;
        try {
            label = (String)xmlClient.execute("MessungHandler.getNumberAxisLabel",param);
        } catch (XmlRpcException e){
            logger.error(e.getMessage());
            label = "";
        }
        return label;
    }
}
