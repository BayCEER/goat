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
package de.unibayreuth.bayeos.goat.table;


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

import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.AggregateFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.TimeFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.types.XmlRpcType;
import de.unibayreuth.bayeos.goat.Constants;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;
import de.unibayreuth.bayeos.utils.MsgBox;


public class AggregationTableModel extends DefaultTableModel implements Constants, AggregateTable {
    
    final static Logger logger = Logger.getLogger(AggregationTableModel.class.getName()); 

    private Vector rows ;
    private Vector metaData;
   
    private XmlRpcClient xmlClient;
    
  
    public AggregationTableModel(final XmlRpcClient xmlClient) {
      this.xmlClient = xmlClient;
    }

    public String getColumnName(int column) {
        Vector col = (Vector)metaData.elementAt(column);
        return (String)col.elementAt(POS_NAME);
    }
        
    public Class getColumnClass(int column) {
        Vector col = (Vector)metaData.elementAt(column);
        int type = ((Integer)col.elementAt(POS_TYPE)).intValue();
        switch(type) {
        case XmlRpcType.STRING:
            return String.class;
        case XmlRpcType.BOOLEAN:
            return Boolean.class;
        case XmlRpcType.INTEGER:
            return Integer.class;
        case XmlRpcType.DOUBLE:
            return Double.class;
        case XmlRpcType.DATE:
           return java.util.Date.class;
        default:
           return String.class;
        }
    }

    public int getColumnCount() {
        if (metaData == null) return 0;
       return metaData.size();
    }        
    
    public int getRowCount() {
        if (rows == null) return 0;
        return rows.size();
    }

    public Object getValueAt(int aRow, int aColumn) {
        Vector row = (Vector)rows.elementAt(aRow);
        return row.elementAt(aColumn);
    }
    
    public void setValueAt(Object value, int aRow, int aColumn) {
        Vector row = (Vector)rows.elementAt(aRow);
        row.setElementAt(value, aColumn);
    }
    
    public boolean load(ObjektNode objektNode,final TimeFilter tFilter,final AggregateFilter aFilter) {
       try {
       Vector params = new Vector(3);
       params.add(objektNode.getId());
       params.add(tFilter.getVector());
       params.add(aFilter.getVector());
       
       Vector vReturn = (Vector)xmlClient.execute("AggregationTableHandler.getRows",params);
       metaData = (Vector)vReturn.elementAt(0);
       metaData.trimToSize();
       rows = (Vector)vReturn.elementAt(1);  
       rows.trimToSize();
       logger.debug("AggregationTableModel filled with " + getRowCount() + " records.");
       return true;
       } catch(XmlRpcException e){
         MsgBox.error(e.getMessage());
         return false;
      }
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }    
    
    
}
