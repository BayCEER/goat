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

import de.unibayreuth.bayeos.goat.Constants;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;
import de.unibayreuth.bayeos.utils.MsgBox;


public class ReferenceTableModel extends DefaultTableModel implements Constants {
    
    final static Logger logger = Logger.getLogger(RightTableModel.class.getName()); 
    
    // Visible Rows 
    public final static int INDEX_TYPE = 0;
    public final static int INDEX_DESCRIPTION = 1;   
    public final static int INDEX_VON = 2;
    public final static int INDEX_BIS = 3;  
        
    //public final static int COLUMN_COUNT = 8;
    public final static int COLUMN_COUNT = 4;    
    
    // Hidden Rows 
    public final static int INDEX_VERWEIS_ID = 4;
    public final static int INDEX_ID_VON = 5;
    public final static int INDEX_UNAME = 6;
    public final static int INDEX_ID_AUF = 7;
   
    private Vector rows ;
    
    private String[] ColumnNames = {"Type","Description","Start","End","Id_Verweis","Id_von","Uname","Id_auf"};
    private Class[] ColumnClasses = {String.class,String.class,java.util.Date.class,java.util.Date.class,Integer.class,Integer.class,String.class, Integer.class};
       
    private XmlRpcClient xmlClient;
    
    private ObjektNode objektNode;
   
    public ReferenceTableModel(final XmlRpcClient xmlClient) {
      this.xmlClient = xmlClient;     
   }
   
    public boolean loadData(ObjektNode objektNode, boolean inherited) {
       try {
       Vector params = new Vector(1);
       params.add(objektNode.getId());
       rows = (Vector)xmlClient.execute("ObjektHandler." + (inherited?"getInheritedReferences":"getDirectReferences"),params);
       if (logger.isDebugEnabled()) { 
        logger.debug("ReferenceTableModel filled with " + getRowCount() + " records.");
       }
       this.objektNode = objektNode;
       } catch(XmlRpcException e){
           MsgBox.error(e.getMessage());
           logger.error(e.getMessage());return false;
       }
       return true;
    }

    public String getColumnName(int column) {
        return ColumnNames[column];
    }
        
    public Class getColumnClass(int column) {
       return ColumnClasses[column];
    }

    public int getColumnCount() {
       return COLUMN_COUNT;
    }        
    
    public int getRowCount() {
        if (rows == null) return 0;
        return rows.size();
    }

    public Object getValueAt(int aRow, int aColumn) {
        Vector row = (Vector)rows.elementAt(aRow);
        if (row == null) return null;
        return row.elementAt(aColumn);
    }
    
    public boolean isCellEditable(int aRow, int aColumn){
        return ((aColumn == INDEX_VON || aColumn == INDEX_BIS) && objektNode.hasFullAccess());
    }
    
    public void addRow(ObjektNode fromNode,ObjektNode toNode) {
       if (logger.isDebugEnabled()) { 
       logger.debug("addRow(" + fromNode.getDe() + "," + toNode.getDe() + ")");
       }
       Vector rowData = null;
       try {
        Vector params = new Vector(2);
        params.add(fromNode.getId());
        params.add(toNode.getId());
        params.add(fromNode.getObjektart().toString());
        rowData = (Vector)xmlClient.execute("ObjektHandler.createReference",params);
       } catch(XmlRpcException e){
         MsgBox.error(e.getMessage());
         logger.error(e.getMessage());return;
      }
      rows.addElement(rowData);
      fireTableDataChanged();
    }
    
    public void removeRow(int aRow) {
       if (logger.isDebugEnabled()) { 
       logger.debug("removeRow(" + aRow + ")");
       }
       Vector row = (Vector)rows.elementAt(aRow);
       try {
       Vector params = new Vector(3);
       params.add((Integer)row.elementAt(INDEX_VERWEIS_ID));
       params.add((String)row.elementAt(INDEX_UNAME));
       Boolean bol = (Boolean)xmlClient.execute("ObjektHandler.deleteReference",params);
       } catch(XmlRpcException e){
           MsgBox.error(e.getMessage());
           logger.error(e.getMessage());return;
      }
      rows.removeElementAt(aRow);
      fireTableDataChanged();
    }
    
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {                   
        if (logger.isDebugEnabled()) {
            logger.debug("setValue[" + rowIndex + "][" + columnIndex + "]=" + aValue);    
        }
        Vector row = (Vector)rows.elementAt(rowIndex);
        try {
            Vector params = new Vector();
            params.add((Integer)row.elementAt(INDEX_ID_AUF));
            params.add((Integer)row.elementAt(INDEX_VERWEIS_ID));
            params.add((String)row.elementAt(INDEX_UNAME));
            if (columnIndex == INDEX_VON){
                params.add((java.util.Date)aValue);
                params.add((java.util.Date)row.elementAt(INDEX_BIS));
            } else if (columnIndex == INDEX_BIS){
                params.add((java.util.Date)row.elementAt(INDEX_VON));
                params.add((java.util.Date)aValue);
            } else {
                return;
            }
            Boolean bol = (Boolean)xmlClient.execute("ObjektHandler.updateReference",params);
            row.setElementAt(aValue,columnIndex);
        } catch(XmlRpcException e){
            MsgBox.error(e.getMessage());
            logger.error(e.getMessage());return;
        }
        fireTableDataChanged();
    }    
    


}
