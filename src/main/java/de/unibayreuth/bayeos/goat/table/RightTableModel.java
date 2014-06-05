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


public class RightTableModel extends DefaultTableModel implements Constants {
    
    final static Logger logger = Logger.getLogger(RightTableModel.class.getName()); 
    
    
    public final static int INDEX_USER_NAME = 0;
    public final static int INDEX_READ = 1;
    public final static int INDEX_WRITE = 2;
    public final static int INDEX_EXEC = 3;
    public final static int INDEX_INHERIT_PERM = 4;

    public final static int COLUMN_COUNT = 5;
    
    // Hidden
    public final static int INDEX_EDITABLE = 5;
    public final static int INDEX_OBJEKTART = 6;
    public final static int INDEX_ID_BENUTZER = 7;
    
   
    private Vector rows ;
    
    
    private String[] ColumNames = {"User","Read","Write","Execute","Inherit","Editable","Uname","id_benutzer"};
    private Class[] ColumnClasses = {String.class,Boolean.class,Boolean.class,Boolean.class,Boolean.class,
    Boolean.class, String.class,Integer.class};
    
    private XmlRpcClient xmlClient;
        
    private Integer id_obj;
    
    
    public RightTableModel(final XmlRpcClient xmlClient) {
      this.xmlClient = xmlClient;
    }
    
    public boolean loadData(final Integer id_obj) { 
      try {
       Vector params = new Vector(1);
       params.add(id_obj);
       Vector vReturn = (Vector)xmlClient.execute("RightHandler.getRights",params);
       rows = (Vector)vReturn.elementAt(0);  
       rows.trimToSize();
       logger.debug("RightTableModel filled with " + getRowCount() + " records.");
       this.id_obj = id_obj;
       } catch(XmlRpcException e){
         logger.error(e.getMessage()); 
         return false;
       }
       return true;
    }

    public String getColumnName(int column) {
        return ColumNames[column];
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
        if (aColumn == INDEX_USER_NAME) return false;
        return ((Boolean)getValueAt(aRow,INDEX_EDITABLE)).booleanValue();
    }
    
    public boolean isRowRemovable(int aRow){
        Vector row = (Vector)rows.elementAt(aRow);
        if (row == null) return false;
        return ((Boolean)row.elementAt(INDEX_EDITABLE)).booleanValue();
    }
    
    public void setValueAt(Object value, int aRow, int aColumn) {
       Vector row = (Vector)rows.elementAt(aRow);
       try {
       Vector params = new Vector(6);
       params.add(id_obj);
       params.add((Integer)row.elementAt(INDEX_ID_BENUTZER));
       if (aColumn == INDEX_READ) {
           params.add("read");
       } else if (aColumn == INDEX_WRITE) {
           params.add("write");
       } else if (aColumn == INDEX_EXEC) {
           params.add("exec");
       } else if (aColumn == INDEX_INHERIT_PERM) {
           params.add("inherit");
       } else {
           return;
       }
       params.add((Boolean)value);
       Boolean bol = (Boolean)xmlClient.execute("RightHandler.updateRight",params);
       } catch(XmlRpcException e){
           logger.error(e.getMessage());return;
      }
      row.setElementAt(value, aColumn);
    }
    
    public void addRow(Vector rowData) {
       logger.debug("addRow(" + rowData + ")");
       try {
       Vector params = new Vector(6);
       params.add(id_obj);
       params.add((Integer)rowData.elementAt(INDEX_ID_BENUTZER));
       params.add((Boolean)rowData.elementAt(INDEX_READ));
       params.add((Boolean)rowData.elementAt(INDEX_WRITE));
       params.add((Boolean)rowData.elementAt(INDEX_EXEC));
       params.add((Boolean)rowData.elementAt(INDEX_INHERIT_PERM));
       Boolean bol = (Boolean)xmlClient.execute("RightHandler.createRight",params);
       } catch(XmlRpcException e){
              logger.error(e.getMessage());return;
       }
      rows.addElement(rowData);
      fireTableDataChanged();
        
    }
    
    public void removeRow(int aRow) {
       logger.debug("removeRow(" + aRow + ")");
       Vector row = (Vector)rows.elementAt(aRow);
       try {
       Vector params = new Vector(2);
       params.add(id_obj);
       params.add((Integer)row.elementAt(INDEX_ID_BENUTZER));
       Boolean bol = (Boolean)xmlClient.execute("RightHandler.deleteRight",params);
       } catch(XmlRpcException e){
         logger.error(e.getMessage());return;
      }
      rows.removeElementAt(aRow);
      fireTableDataChanged();
    }


}
