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


import java.nio.ByteBuffer;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.collections.primitives.ArrayByteList;
import org.apache.commons.collections.primitives.ArrayDoubleList;
import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.ByteList;
import org.apache.commons.collections.primitives.DoubleList;
import org.apache.commons.collections.primitives.IntList;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.StatusFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.TimeFilter;
import de.unibayreuth.bayeos.goat.Constants;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;
import de.unibayreuth.bayeos.utils.MsgBox;



public class MassenTableModel extends DefaultTableModel implements Constants, EditableObjektTable {
    
    final static Logger logger = Logger.getLogger(MassenTableModel.class.getName()); 

    
    private static final String columnName[] = {"Datetime","Value","Status"};
    private static final Class columnClass[] = {java.util.Date.class,Double.class,Integer.class};
    private static final short rowLength = 9;
    private static final short columnCount = 3;
    private boolean columnWritable[] = new boolean[3];
    private static int rowCount;
        
    
    protected ByteList statusList;
    protected IntList vonList;
    protected DoubleList wertList;
    
    private XmlRpcClient xmlClient; 
        
    public MassenTableModel(final XmlRpcClient xmlClient)  {
      this.xmlClient = xmlClient;
    }

    public String getColumnName(int column) {
       return columnName[column];
    }
        
    public Class getColumnClass(int column) {
        return columnClass[column];
    }

    public int getColumnCount() {
        return columnCount;
    }        
    
    public int getRowCount() {
        return (vonList==null)?0:vonList.size();
    }
    
    public Object getValueAt(int aRow, int aColumn) {
        switch (aColumn) {
        case 0: 
            return new java.util.Date(vonList.get(aRow)*1000L);
        case 1:
            return new Double(wertList.get(aRow));
        case 2: 
            return new Byte(statusList.get(aRow));
        default:
            return null;
        }
    }
      
    private void removeRows(int[] r){
        ByteList stat = new ArrayByteList(r.length);
        IntList von = new ArrayIntList(r.length);
        DoubleList wert = new ArrayDoubleList(r.length);
        
        for (int i=0;i<r.length;i++){
          stat.add(statusList.get(r[i]));
          von.add(vonList.get(r[i]));
          wert.add(wertList.get(r[i]));
        }
        statusList.removeAll(stat);
        vonList.removeAll(von);
        wertList.removeAll(wert);
    }
    
    
   public void setValueAt(Object value, int aRow, int aColumn) {
        logger.debug("Row:" + aRow + " Column:" + aColumn + " Value: " + value);
        
        switch (aColumn) {
        case 0:
            logger.debug("" + vonList.get(aRow));
            int time = (int)(((java.util.Date)value).getTime()/1000);
            vonList.set(aRow,time);
            logger.debug("" + vonList.get(aRow));
            break;
        case 1:
            wertList.set(aRow,((Double)value).doubleValue());break;
        case 2: 
           statusList.set(aRow,((Byte)value).byteValue());break;
        
        }
        
    }
    
   public boolean updateRow(ObjektNode objektNode, int row, Vector values) {
         boolean bol = false;
         try {
         
         // get update columns
         java.util.Date new_von = (java.util.Date)values.elementAt(0);
         Double wert = (Double)values.elementAt(1);
         Integer status = (Integer)values.elementAt(2);
         
         java.util.Date old_von = (java.util.Date)getValueAt(row,0);
         
         Vector params = new Vector();
         params.add(objektNode.getId());
         params.add(old_von);
         params.add(status);
         params.add(wert);         
         params.add(new_von);
         
         bol  = ((Boolean)xmlClient.execute("MassenTableHandler.updateRow",params)).booleanValue();
         
         // Update display values 
         setValueAt(new_von,row,0);              
         setValueAt(wert,row,1) ;              
         setValueAt(new Byte(status.byteValue()),row,2) ;              
         fireTableDataChanged();
         
         } catch (XmlRpcException x) {
             MsgBox.error(x.getMessage());
             return false;
         }
         
         return bol;
   }       
    
   public boolean updateRows(final ObjektNode objektNode,final int rows[], final Vector values)  {
         boolean bol = false;
         try {
         
         // get update columns
         Integer status = (Integer)values.elementAt(0);
         
         // get keys
         Vector vonDates = new Vector();
         for (int r = 0; r < rows.length; r++){
                vonDates.add(getValueAt(rows[r],0));
         }
         Vector params = new Vector();
         params.add(objektNode.getId());
         params.add(vonDates);
         params.add(status);
         
         bol  = ((Boolean)xmlClient.execute("MassenTableHandler.updateRows",params)).booleanValue();
         
         // Update display values 
         for (int r = 0; r < rows.length; r++){
              setValueAt(new Byte(status.byteValue()),rows[r],2) ;              
         }
         
         fireTableDataChanged();
         
         } catch (XmlRpcException x) {
             MsgBox.error(x.getMessage());
             return false;
         }
         
         return bol;
     }
    
   public boolean removeRows(ObjektNode objektNode, int[] rows) {
         boolean bol = false;
         try {
         
         
         // Update DB values 
         Vector vonDates = new Vector();
         for (int r = 0; r < rows.length; r++){
                vonDates.add(getValueAt(rows[r],0));
         }
         Vector params = new Vector();
         params.add(objektNode.getId());
         params.add(vonDates);
         bol  = ((Boolean)xmlClient.execute("MassenTableHandler.removeRows",params)).booleanValue();
         
         removeRows(rows);
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

         // Update display values 
         
         java.util.Date von = (java.util.Date)values.elementAt(0);
         Double wert = (Double)values.elementAt(1);
         Integer status = (Integer)values.elementAt(2);
             
         Vector params = new Vector();
         
         params.add(objektNode.getId());
         params.add(von);   
         params.add(wert);  
         params.add(status); 
         
         bol  = ((Boolean)xmlClient.execute("MassenTableHandler.addRow",params)).booleanValue();
    
         
         vonList.add((int)(von.getTime()/1000));
         wertList.add(wert.doubleValue());
         statusList.add(status.byteValue());
         
         fireTableDataChanged();
         
         } catch (XmlRpcException x) {
             MsgBox.error(x.getMessage());
             return false;
         }
         
         return bol;
         
     }     
    
   public boolean load(ObjektNode objekt, TimeFilter tFilter, StatusFilter sFilter) {
      try {        
      
      Vector params = new Vector();
      params.add(objekt.getId());
      params.add(tFilter.getVector());
      params.add(sFilter.getVector());
      Vector vReturn = (Vector)xmlClient.execute("MassenTableHandler.getRows",params);
      // Rows als byte[]
      byte[] ba = (byte[])vReturn.elementAt(1);
      
      statusList = new ArrayByteList(ba.length/rowLength);
      vonList = new ArrayIntList(ba.length/rowLength);
      wertList = new ArrayDoubleList(ba.length/rowLength);
      
      ByteBuffer b = ByteBuffer.wrap(ba);
      while(b.hasRemaining()){
          vonList.add(b.getInt());
          wertList.add((double)b.getFloat());
          statusList.add(b.get());
      }
      vReturn = null;
      logger.debug("MassenTableModel filled with " + getRowCount() + " records.");
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
