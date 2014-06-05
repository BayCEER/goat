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


import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.IntList;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.AggregateFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.StatusFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.TimeFilter;
import de.unibayreuth.bayeos.goat.Constants;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;
import de.unibayreuth.bayeos.utils.MsgBox;



public class OctetMatrixTableModel extends DefaultTableModel implements Constants, MatrixTable {
    
    final static Logger logger = Logger.getLogger(OctetMatrixTableModel.class.getName()); 
        
    private int rowCount = 0;
    private ArrayList colClasses = new ArrayList();
    private ArrayList colNames = new ArrayList();
    
    private IntList vonList = new ArrayIntList();
    private ArrayList wertList = new ArrayList();
        
    private XmlRpcClient xmlClient; 
        
    public OctetMatrixTableModel(final XmlRpcClient xmlClient)  {
      this.xmlClient = xmlClient;
      
    }
    
    
    public String getColumnName(int column) {
       return (String)colNames.get(column);
    }
        
    public Class getColumnClass(int column) {
       return (Class)colClasses.get(column);
    }

    public int getColumnCount() {
        return colClasses.size();
    }        
    
    public int getRowCount() {
        return rowCount;
    }
    
    
   public boolean isCellEditable(int rowIndex, int columnIndex) {
       return false;
   }   
   
   
   private void setColumnClasses(Vector nodes) {
       colClasses = new ArrayList();
       colClasses.add(java.util.Date.class);
       Iterator it = nodes.iterator();       
       while (it.hasNext()){
           it.next();
           colClasses.add(Double.class);
              
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
    
   
   
   private void setColumnNames(Vector nodes){
      colNames = new ArrayList();
      colNames.add("VON");
      Iterator it = nodes.iterator();
      while (it.hasNext()){
          colNames.add( ((ObjektNode)it.next()).getDe());
      }
   }
   
  
   
   public boolean load(Vector objekts, TimeFilter tFilter, StatusFilter sFilter, Boolean withStatus) {
     
     try {
      Vector params = new Vector();
      params.add(getIds(objekts));
      params.add(tFilter.getVector());
      params.add(sFilter.getVector());
      params.add(withStatus);
      params.add("obj_stream");
      setColumnClasses(objekts);
      setColumnNames(objekts);
      readStream(xmlClient.executeStream("OctetMatrixHandler.getMatrixOrg",params),objekts);
      } catch (IOException i){
          MsgBox.error(i.getMessage());
          return false;
      } catch (XmlRpcException e){
          MsgBox.error(e.getMessage());
          return false;
      }
      return true;
   }   
      
   private void readStream(InputStream in, Vector objekts) throws IOException {
       int von = 0;
       rowCount = 0;
       ObjectInputStream oin = new ObjectInputStream(in);   
       try {
            while (true) { 
             vonList.add(oin.readInt());
             for (int c=0;c<objekts.size();c++){
               wertList.add(oin.readObject());
             }
             rowCount++;       
            }
       } catch (EOFException i){
            if (logger.isDebugEnabled()){
                logger.debug("Stream with " + rowCount + " rows red.");
            }
       } catch (ClassNotFoundException e){
            logger.error(e.getMessage());
       }
       
   }

   public boolean load(Vector objekts, TimeFilter tFilter, AggregateFilter aFilter, Boolean withCounts) {
     try {
      Vector params = new Vector();
      params.add(getIds(objekts));
      params.add(tFilter.getVector());
      if (aFilter == null) {
          params.add(null);
      } else {
          params.add(aFilter.getVector());
      }
      params.add(withCounts);
      params.add("obj_stream");
      setColumnClasses(objekts);
      setColumnNames(objekts);
      readStream(xmlClient.executeStream("OctetMatrixHandler.getMatrixAggr",params),objekts);
      } catch (IOException i){
          MsgBox.error(i.getMessage());
          return false;
      } catch (XmlRpcException e){
          MsgBox.error(e.getMessage());
          return false;
      } 
      return true;
   }   

   public Object getValueAt(int rowIndex, int columnIndex) {
       if (vonList.size() == 0) return null;
       if (columnIndex == 0){
           return new java.util.Date(vonList.get(rowIndex)*1000L);
       } else {
           int i = columnIndex - 1 + (rowIndex*(getColumnCount() -1));
           return wertList.get(i);
       }
   }
   
}
