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
 * JTableXYDataset.java
 * 
 * Created on September 25, 2002, 3:12 PM
 */

package de.unibayreuth.bayeos.goat.chart;

import javax.swing.table.TableModel;

import org.jfree.data.AbstractDataset;
import org.jfree.data.DomainInfo;
import org.jfree.data.Range;
import org.jfree.data.XYDataset;

import de.unibayreuth.bayeos.goat.Constants;
import de.unibayreuth.bayeos.goat.table.LaborTableModel;
import de.unibayreuth.bayeos.goat.table.MassenTableModel;

/**
 *
 * @author  oliver
 */
public class TableModelXYDataset extends AbstractDataset implements XYDataset, DomainInfo, Constants {
    
    private TableModel tableModel;
    
    private int seriesCount;
   
    
    /** Creates a new instance of JTableXYDataset */
    
    public TableModelXYDataset(TableModel tableModel) {
        this.tableModel = tableModel;
        this.seriesCount = tableModel.getColumnCount() - 1;
    }
    
    public TableModelXYDataset(TableModel tableModel, boolean isSingleSerie){
        this.tableModel = tableModel;
        this.seriesCount = 1;
    }
    
    public int getItemCount(int series) {
        return tableModel.getRowCount();
    }
    
    public int getSeriesCount() {
        return seriesCount;
    }
    
    public String getSeriesName(int series) {
        return tableModel.getColumnName(series + 1);
    }
    
    public Number getXValue(int series, int row) {
      // Zeitwert als Long
      if (tableModel.getRowCount() == 0) return null;
      if (tableModel instanceof LaborTableModel) {
        return new Long(((java.util.Date)tableModel.getValueAt(row,LAB_COL_BIS)).getTime());          
      } else if (tableModel instanceof MassenTableModel) {
        return new Long(((java.util.Date)tableModel.getValueAt(row,MAS_COL_VON)).getTime());          
      } else {
        return new Long(((java.util.Date)tableModel.getValueAt(row,0)).getTime()); 
      }
      
    }
    
    public Number getYValue(int series, int row) {
      if (tableModel.getRowCount() == 0) return null;
      
      int colIndex;
      if (tableModel instanceof LaborTableModel) {
    	colIndex = LAB_COL_WERT;    	          
      } else if (tableModel instanceof MassenTableModel){
        colIndex = MAS_COL_WERT;
      } else {
        colIndex = series +1;
      }
      
      // BugFix: NaN and Inf Values in Massendaten.wert 
      // o.a.: 29.01.2013
      
      Number n = (Number)tableModel.getValueAt(row, colIndex);      
      if (n instanceof Double){
    	  Double d = (Double)n ;
    	  if (d.isNaN() || d.isInfinite()){
    		  return null;
    	  } else {
    		  return d;
    	  }
      } else if (n instanceof Float){
    	  Float f = (Float)n ;
    	  if (f.isNaN() || f.isInfinite()){
    		  return null;
    	  } else {
    		  return f;
    	  }
      } else {
    	  return null;
      }
      
     
        
      
    }
    
   
    public Number getMinimumDomainValue() {
        return getXValue(1,0);
    }
    
    public Number getMaximumDomainValue() {
        return getXValue(1,tableModel.getRowCount()-1);
    }
    
    public org.jfree.data.Range getDomainRange() {        
        Number lvalue, uvalue;
        lvalue = getMinimumDomainValue();
        uvalue = getMaximumDomainValue();
        
        if (uvalue != null && lvalue != null) {
           return new Range(lvalue.doubleValue(), uvalue.doubleValue());
        } else {
           return null;
        }
    }

    
}
