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
package de.unibayreuth.bayeos.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import de.unibayreuth.bayceer.bayeos.xmlrpc.formats.DateFormat;
import de.unibayreuth.bayceer.bayeos.xmlrpc.formats.NumericFormat;
import de.unibayreuth.bayeos.goat.Constants;
/**
* ExcelAdapter enables Copy-Paste Clipboard functionality on JTables.
* The clipboard data format used by the adapter is compatible with
* the clipboard format used by Excel. This provides for clipboard
* interoperability between enabled JTables and Excel.

* This is the line that does all the magic!
  ExcelAdapter myAd = new ExcelAdapter(jTable1);
 **/

public class ExcelAdapter implements ActionListener, Constants
   {
   private String rowstring,value;
   private Clipboard system;
   private StringSelection stsel;
   private JTable table ;
   private final String ACTION_COPY = "Copy";
   /**
    * The Excel Adapter is constructed with a
    * JTable on which it enables Copy-Paste and acts
    * as a Clipboard listener.
    */

public ExcelAdapter(JTable myJTable) 
   {
      table = myJTable;
      KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK,false);
      table.registerKeyboardAction(this,ACTION_COPY,copy,JComponent.WHEN_FOCUSED);
      system = Toolkit.getDefaultToolkit().getSystemClipboard();
   }
   /**
    * Public Accessor methods for the Table on which this adapter acts.
    */
public JTable getJTable() {return table;}
public void setJTable(JTable table) {this.table=table;}
   /**
    * This method is activated on the Keystrokes we are listening to
    * in this implementation. Here it listens for Copy ActionCommands.
    */
public void actionPerformed(ActionEvent e)
   {
      if (e.getActionCommand().equals(ACTION_COPY))
      {
         StringBuffer sbf=new StringBuffer();
         int numcols=table.getColumnCount();
         int numrows=table.getSelectedRowCount();
         int[] rowsselected = table.getSelectedRows();
         for (int r=0;r<numrows;r++)
         {
            for (int c=0;c<numcols;c++)
            {
               Object value = table.getValueAt(rowsselected[r],c) ;
               if (value instanceof java.util.Date) {
                 sbf.append(DateFormat.defaultDateFormat.format(value));      
               } else if(value instanceof java.lang.Double || value instanceof java.lang.Float){
                 sbf.append(NumericFormat.defaultDecimalFormat.format(value));        
               } else {
                 sbf.append(value);      
               }
               
               if (c<numcols-1) sbf.append("\t");
            
            }
            sbf.append("\n");
         }
         stsel  = new StringSelection(sbf.toString());
         //system = Toolkit.getDefaultToolkit().getSystemClipboard();
         system.setContents(stsel,stsel);
      }
         
   }
}
