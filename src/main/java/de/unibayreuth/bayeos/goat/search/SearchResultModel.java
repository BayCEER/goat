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
package de.unibayreuth.bayeos.goat.search;


/**
 * An adaptor, transforming the XmlRpc interface to the TableModel interface.
 *
 * @author oliver
 */

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import de.unibayreuth.bayeos.goat.Constants;
import de.unibayreuth.bayeos.utils.MsgBox;


public class SearchResultModel extends DefaultTableModel implements Constants {
    
    final static Logger logger = Logger.getLogger(SearchResultModel.class.getName()); 

    private Vector rows ;
    private Vector metaData;   
    private XmlRpcClient xmlClient;
    
  
    public SearchResultModel(final XmlRpcClient xmlClient) {
      this.xmlClient = xmlClient;
    }

    public String getColumnName(int column) {
        switch(column) {
         case 0: return "Name";
         case 1: return "id";
         case 2: return "id_super";
         case 3: return "uname";
         default: return "";
        }
     }
        
    public Class getColumnClass(int column) {
        switch(column) {
        case 0: return String.class;
        case 1: return Integer.class;
        case 2: return Integer.class;
        case 3: return String.class;
        default: return Object.class;
        }
    }

    public int getColumnCount() {
        return 1;
    }        
    
    public int getRowCount() {
        if (rows == null) return 0;
        return rows.size();
    }

    public Object getValueAt(int aRow, int aColumn) {
        Vector row = (Vector)rows.elementAt(aRow);
        return row.elementAt(aColumn);
    }    
    
    public void loadData(String name)  {
       try {
       Vector params = new Vector(1);
       params.add(name);
       rows = (Vector)xmlClient.execute("TreeHandler.findNode",params);
       rows.trimToSize();
       logger.debug("Model filled with " + getRowCount() + " records.");
       } catch(XmlRpcException e){
         MsgBox.error(e.getMessage());
      }
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }    
     
    
    
}
