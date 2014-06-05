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
 * CSVAdapter.java
 *
 * Created on 5. Mai 2004, 08:59
 */

package de.unibayreuth.bayeos.utils;

import java.awt.Component;
import java.awt.Cursor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JTable;

import org.apache.log4j.Logger;

import de.unibayreuth.bayceer.bayeos.xmlrpc.formats.DateFormat;
import de.unibayreuth.bayceer.bayeos.xmlrpc.formats.NumericFormat;
import de.unibayreuth.bayeos.goat.Constants;

/**
 *
 * @author  oliver
 */
public class CSVAdapter implements Constants {
    
    private JTable table;
    private Component parent;  
    private final static Logger logger = Logger.getLogger(CSVAdapter.class.getName()); 
    private final static JFileChooser myFileChooser = new JFileChooser();
    
    
    /** Creates a new instance of CSVAdapter */
    public CSVAdapter(Component parent, JTable table) {
        this.parent = parent;
        this.table = table;
        myFileChooser.setFileFilter(new DefaultFileFilter("csv","Comma separated format"));
    }
    
    /**
     * Getter for property table.
     * @return Value of property table.
     */
    public javax.swing.JTable getTable() {
        return table;
    }    
    
    /**
     * Setter for property table.
     * @param table New value of property table.
     */
    public void setTable(javax.swing.JTable table) {
        this.table = table;
    }
    
    public void save(String name){
            PrintWriter printWriter = null;
            
            myFileChooser.setSelectedFile(new File(name +".csv"));    
            int returnVal = myFileChooser.showSaveDialog(parent);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
              File file = myFileChooser.getSelectedFile();
              logger.info("Exporting table to file " + file.getAbsolutePath());
              try {
               parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
               FileOutputStream outputFile = new FileOutputStream(file);
               OutputStreamWriter writer = new OutputStreamWriter(outputFile);
               BufferedWriter bufferedWriter = new BufferedWriter(writer);
               printWriter = new PrintWriter(bufferedWriter,true);
               
               StringBuffer headerRow = new StringBuffer();
               for (int column = 0; column < table.getColumnCount(); column++){
            	   headerRow.append(table.getColumnName(column));            	   
            	   if (column != table.getColumnCount() -1) headerRow.append(';');
               }
               printWriter.println(headerRow);
               
               for (int row=0 ; row < table.getRowCount(); row++){
                   StringBuffer sbf = new StringBuffer("");
                   for (int column = 0; column < table.getColumnCount(); column++){
                    Object value = table.getValueAt(row,column);
                    if (value != null) {
                    if (value instanceof java.util.Date) {
                       sbf.append(DateFormat.defaultDateFormat.format(value));      
                    } else if(value instanceof java.lang.Double || value instanceof java.lang.Float){
                       sbf.append(NumericFormat.defaultDecimalFormat.format(value));        
                    } else {
                       sbf.append(value);      
                    }
                    }
                    if (column != table.getColumnCount() -1) sbf.append(';');
                   }
                   printWriter.println(sbf.toString());
               }
               logger.info("Table exported.");
               } catch (IOException i) {
                  logger.error(i.getMessage());
                  MsgBox.error("Error in exporting file " + file.getAbsolutePath() + ".\n" + i.getMessage());
               } catch (IllegalArgumentException a) {
                  logger.error(a.getMessage());
                   MsgBox.error("Error in exporting file " + file.getAbsolutePath() + ".\n" + a.getMessage());
               } finally {
                 printWriter.close();
                 parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
               }
          } 

            
         
            
           
         }
    
    
}
