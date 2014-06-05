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

import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.StatusFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.TimeFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.types.XmlRpcType;
import de.unibayreuth.bayeos.goat.Constants;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;
import de.unibayreuth.bayeos.utils.MsgBox;


public class LaborTableModel extends DefaultTableModel implements Constants, EditableObjektTable {
    
    final static Logger logger = Logger.getLogger(LaborTableModel.class.getName()); 

    private Vector rows ;
    private Vector metaData;
   
    private XmlRpcClient xmlClient;
    
  
    public LaborTableModel(final XmlRpcClient xmlClient) {
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
    
    public boolean updateRows(final ObjektNode objektNode, final int rows[], final Vector values)  {
         boolean bol = false;
         try {
         
         // update of other columns not allowed here
         Integer status = (Integer)values.elementAt(0);
         
         // Update DB values 
         Vector bisDates = new Vector();
         for (int r = 0; r < rows.length; r++){
                bisDates.add(getValueAt(rows[r],LAB_COL_BIS));
         }
         Vector params = new Vector();
         params.add(objektNode.getId());
         params.add(bisDates);
         params.add(status);
         bol  = ((Boolean)xmlClient.execute("LaborTableHandler.updateRows",params)).booleanValue();
         
         // Update display values 
         for (int r = 0; r < rows.length; r++){
              setValueAt(status,rows[r],LAB_COL_STAT_ID) ;
         }
         
         } catch (XmlRpcException x) {
             MsgBox.error(x.getMessage());
         }
         
         return bol;
    }  
    
    public boolean removeRows(ObjektNode objektNode, int[] r) {
        boolean bol = false;
         try {
         
         
         // Update DB values 
         Vector bisDates = new Vector();
         Vector rv = new Vector();
         for (int i=0;i<r.length;i++){
                bisDates.add(getValueAt(r[i],3));
                rv.add(rows.get(r[i]));
         }
         Vector params = new Vector();
         params.add(objektNode.getId());
         params.add(bisDates);
         bol  = ((Boolean)xmlClient.execute("LaborTableHandler.removeRows",params)).booleanValue();         
         
         rows.removeAll(rv);
         
         fireTableDataChanged();
         
         } catch (XmlRpcException x) {
             MsgBox.error(x.getMessage());
             return false;
         }
         
         return bol;
         
    }    
   
    public boolean addRow(ObjektNode objektNode, Vector values) {
        boolean bol = false;
         try {

         Vector param = new Vector();
         param.add(objektNode.getId());
         param.add(values);
         bol  = ((Boolean)xmlClient.execute("LaborTableHandler.addRow",param)).booleanValue();
    
         // Update display values 
         values.add(0, objektNode.getId());
         rows.add(values);
         fireTableDataChanged();
         
         } catch (XmlRpcException x) {
             MsgBox.error(x.getMessage());
             return false;
         }
         
         return bol;
         
    }    
    
    public boolean load(ObjektNode objekt, TimeFilter tFilter, StatusFilter sFilter) {
      try { 
       Vector params = new Vector(4);
       params.add(objekt.getId());
       params.add(tFilter.getVector());
       params.add(sFilter.getVector());
       Vector vReturn = (Vector)xmlClient.execute("LaborTableHandler.getRows",params);
       metaData = (Vector)vReturn.elementAt(0);
       metaData.trimToSize();
       rows = (Vector)vReturn.elementAt(1);  
       rows.trimToSize();
       logger.debug("LaborTableModel filled with " + getRowCount() + " records.");
       return true;
       } catch (XmlRpcException r) {
           MsgBox.error(r.getMessage());
           return false;
       }
    }    

    public boolean updateRow(ObjektNode objektNode, int row, Vector values) {
         boolean bol = false;
         try {
         
             
         // get update columns
         Integer status = (Integer)values.elementAt(0);
         java.util.Date von = (java.util.Date)values.elementAt(1);
         java.util.Date bis = (java.util.Date)values.elementAt(2);
         Double wert = (Double)values.elementAt(3);
         String lbnr  = (String)values.elementAt(4);
         Double gen = (Double)values.elementAt(5);
         Double nachw = (Double)values.elementAt(6);
         String bem  = (String)values.elementAt(7);
         
         Vector param = new Vector();
         param.add(objektNode.getId());
         param.add(getValueAt(row,LAB_COL_BIS)); // old date
         param.add(values); 
         
         bol  = ((Boolean)xmlClient.execute("LaborTableHandler.updateRow",param)).booleanValue();
         
         // Update display values 
         setValueAt(status,row,1);              
         setValueAt(von,row,2);              
         setValueAt(bis,row,3);              
         setValueAt(wert,row,4);              
         setValueAt(lbnr,row,5);              
         setValueAt(gen,row,6);              
         setValueAt(nachw,row,7);              
         setValueAt(bem,row,8);              
         
         fireTableDataChanged();
         
         } catch (XmlRpcException x) {
             MsgBox.error(x.getMessage());
             return false;
         }
         
         return bol;
        
    }    
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
       return false;
    }

	
        
    
    

    
    



}
