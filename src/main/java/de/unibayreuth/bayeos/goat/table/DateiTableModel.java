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

import java.sql.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.Base64;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import de.unibayreuth.bayeos.goat.Constants;
import de.unibayreuth.bayeos.utils.MsgBox;


public class DateiTableModel extends DefaultTableModel implements Constants {
    
    final static Logger logger = Logger.getLogger(DateiTableModel.class.getName()); 

    private Vector rows ;
    private Vector metaData;
    private Integer id_obj;
   
    private XmlRpcClient xmlClient;
    
  
    public DateiTableModel(final XmlRpcClient xmlClient) {
      this.xmlClient = xmlClient;
    }

    public String getColumnName(int column) {
        switch(column) {
         case 0: return "Thumb";
         case 1: return "Name";
         case 2: return "Description";
         case 3: return "Modified";
         case 4: return "Id";
         
         default: return "";
        }
     }
        
    public Class getColumnClass(int column) {
        switch(column) {
        case 0: return ImageIcon.class;
        case 1: return String.class;
        case 2: return String.class;
        case 3: return Date.class;
        case 4: return Integer.class;        
        default: return Object.class;
        }
    }

    public int getColumnCount() {
        return 3;
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
       logger.debug("setValueAt(" + aRow + "," + aColumn + ")");
       Vector row = (Vector)rows.elementAt(aRow);
       try {      
           Boolean n;
           Vector params = new Vector();
           if (aColumn == 1) {
              // id, name, id_obj
              params.add((Integer)row.elementAt(4));     
              params.add((String)value);
              params.add(id_obj);
              n = (Boolean)xmlClient.execute("DateiHandler.updateName",params);
           } else if (aColumn == 2) {
              // id, beschreibung, id_obj
              params.add((Integer)row.elementAt(4));     
              params.add((String)value);
              params.add(id_obj);
              n = (Boolean)xmlClient.execute("DateiHandler.updateBeschreibung",params);
           } else {
               return;
           }
       } catch(XmlRpcException e){
           MsgBox.error(e.getMessage());return;
      }
      row.setElementAt(value, aColumn);
    }
        
      
    public boolean loadData(Integer id)  {
       try {
       Vector params = new Vector(1);
       params.add(id);
       rows = (Vector)xmlClient.execute("DateiHandler.getDateien",params);
       rows.trimToSize();
       Iterator it = rows.iterator();
       while(it.hasNext()){
           Vector row = (Vector)it.next();
           String thumb = (String)row.elementAt(0);
           if (thumb != null) { 
               ImageIcon i = new ImageIcon( Base64.decode(thumb.getBytes()) );                      
               row.setElementAt(i,0);                              
           }
       }
       logger.debug("DateiTableModel filled with " + getRowCount() + " records.");
       this.id_obj = id;
       return true;
   
       } catch(XmlRpcException e){
         MsgBox.error(e.getMessage());
         return false;
      }
    }
    
     public void addRow(String name, java.util.Date modified, String base64) {
       logger.debug("addRow(" + name + ")");
       Vector row;
       try {
       Vector params = new Vector(6);
       params.add(name);
       params.add(modified);
       params.add(base64);      
       params.add(id_obj);
       row = (Vector)xmlClient.execute("DateiHandler.insertDatei", params);
       } catch(XmlRpcException e){
               MsgBox.error(e.getMessage());return;
       }
       
       Integer id = (Integer)row.elementAt(0) ;
       String thumb = (String)row.elementAt(1);
       
       Vector rowData = new Vector();
       if (thumb != null) {
        
			rowData.add(new ImageIcon( Base64.decode(thumb.getBytes()) ));
		
       } else {
         rowData.add(new ImageIcon());
       }
       
       rowData.add(name);
       rowData.add("");
       rowData.add(modified);
       rowData.add(id);              
       rows.addElement(rowData);
      fireTableDataChanged();
        
    }
     
    public void removeRow(int aRow) {
       logger.debug("removeRow(" + aRow + ")");
       Vector row = (Vector)rows.elementAt(aRow);
       try {
       Vector params = new Vector(1);
       params.add((Integer)row.elementAt(4)); //id
       params.add(id_obj);
       Boolean bol = (Boolean)xmlClient.execute("DateiHandler.deleteDatei",params);
       } catch(XmlRpcException e){
         MsgBox.error(e.getMessage());return;
      }
      rows.removeElementAt(aRow);
      fireTableDataChanged();
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
        
    }
    
}
