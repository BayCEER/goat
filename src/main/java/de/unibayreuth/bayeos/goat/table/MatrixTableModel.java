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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.AggregateFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.StatusFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.TimeFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.types.XmlRpcType;
import de.unibayreuth.bayeos.goat.Constants;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;
import de.unibayreuth.bayeos.utils.MsgBox;



public class MatrixTableModel extends DefaultTableModel implements Constants, MatrixTable {
    
    final static Logger logger = Logger.getLogger(MatrixTableModel.class.getName()); 
        
    private ArrayList colNames, colClasses, colBytes;
    private int colCount = 0;
    private int rowCount = 0;
    private int rowSice = 0; // in Bytes
    ByteBuffer rows;
    
    private XmlRpcClient xmlClient; 
        
    public MatrixTableModel(final XmlRpcClient xmlClient)  {
      this.xmlClient = xmlClient;
      colNames = new ArrayList();
      colClasses = new ArrayList();
      colBytes = new ArrayList();
    }

    public String getColumnName(int column) {
       return (String)colNames.get(column);
    }
        
    public Class getColumnClass(int column) {
       return (Class)colClasses.get(column);
    }

    public int getColumnCount() {
        return colCount;
    }        
    
    public int getRowCount() {
        if (rows == null) return 0;
        return rows.capacity()/rowSice;
    }
    
    private int getByteShift(int aColumn){
        int x = 0;
        while(aColumn-- > 0){
            x = x + ((Integer)colBytes.get(aColumn)).intValue() ;
        }
        return x;
    }

    public Object getValueAt(int aRow, int aColumn) {     
        int index = aRow * rowSice + getByteShift(aColumn);
        Class c = getColumnClass(aColumn);
        if (c == Integer.class) {
           return new Integer(rows.getInt(index));
        } else if (c == Double.class){
           return new Double(rows.getFloat(index));
        } else if (c== java.util.Date.class){
           return new java.util.Date(rows.getInt(index) * 1000L);   
        } else {
            MsgBox.error("Data type not supported in matrix.");
            return null;
        }
    }
      
    
   public boolean isCellEditable(int rowIndex, int columnIndex) {
       return false;
   }   
   
   
   private void setColumnTypes(Vector meta) throws XmlRpcException {
       colClasses = new ArrayList();
       colBytes = new ArrayList();
       colCount = rowSice = 0;
       Iterator it = meta.iterator();       
       while (it.hasNext()){
           Vector row = (Vector)it.next();
           int type = ((Integer)row.elementAt(2)).intValue();
           switch(type){
               case XmlRpcType.INTEGER:
                   colClasses.add(Integer.class);
                   colBytes.add(new Integer(4));
                   rowSice += 4;
                   break;
               case XmlRpcType.DOUBLE:
                   colClasses.add(Double.class);
                   colBytes.add(new Integer(4));
                   rowSice += 4;
                   break;
               case XmlRpcType.DATE:
                   colClasses.add(java.util.Date.class);
                   colBytes.add(new Integer(4));
                   rowSice += 4;
                   break;
               default: throw new XmlRpcException(0,"Type: " + type + " not supported.");
           }
           colCount++;
       }
                             
   }
   
   private Vector getIds(Vector s){
       Iterator it = s.iterator();
       Vector ret = new Vector(s.capacity());
       while(it.hasNext()){
           ret.add( ((ObjektNode)it.next()).getId());
       }
       return ret;
   }
    
   public boolean load(final Vector objekts, final TimeFilter tFilter, final AggregateFilter aFilter, final Boolean withAggrCounts) {
      try {
      Vector params = new Vector();
      params.add(objekts);
      params.add(tFilter.getVector());
      params.add(aFilter.getVector());
      params.add(withAggrCounts);
      params.add("binary");
      Vector vReturn = (Vector)xmlClient.execute("MatrixHandler.getAggr",params);
      setColumnTypes((Vector)vReturn.elementAt(0));
      setColumnNames(objekts);
      rows = ByteBuffer.wrap((byte[])vReturn.elementAt(1));
      logger.debug("MatrixTableModel filled with " + rows.capacity()/rowSice);
      } catch (XmlRpcException e){
          MsgBox.error(e.getMessage());
          return false;
      }
      return true;
   }  
   
   
   private void setBytes(byte[] b){
      rows = ByteBuffer.wrap(b);
      rowCount = rows.capacity()/rowSice;      
   }
   
   private void setColumnNames(Vector nodes){
      colNames = new ArrayList();
      colNames.add("Datetime");
      Iterator it = nodes.iterator();
      while (it.hasNext()){
          colNames.add( ((ObjektNode)it.next()).getDe());
      }
   }
   
   public boolean load(Vector objekts, TimeFilter tFilter, StatusFilter sFilter, Boolean withStatusCols) {
       try {
      Vector params = new Vector();
      params.add(getIds(objekts));
      params.add(tFilter.getVector());
      params.add(sFilter.getVector());
      params.add(withStatusCols);
      params.add("binary");
      Vector vReturn = (Vector)xmlClient.execute("MatrixHandler.getOrg",params);
      setColumnTypes((Vector)vReturn.elementAt(0));
      setColumnNames(objekts);
      setBytes((byte[])vReturn.elementAt(1));
      logger.debug("MatrixTableModel filled with " + rowCount);
      } catch (XmlRpcException e){
          MsgBox.error(e.getMessage());
          return false;
      }
      return true;
   }   


}
