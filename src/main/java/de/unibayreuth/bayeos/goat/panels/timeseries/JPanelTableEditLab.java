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
 * JPanelTableEditM.java
 *
 * Created on 30. Juni 2004, 10:38
 */

package de.unibayreuth.bayeos.goat.panels.timeseries;

import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import de.unibayreuth.bayeos.goat.table.EditableObjektTable;

/**
 *
 * @author  oliver
 */
public class JPanelTableEditLab extends JPanelTableEdit {
    
    protected JDialogEditMessungLabor jDialogRow;
    
    /** Creates a new instance of JPanelTableEditM */
    public JPanelTableEditLab() {
        
     jDialogRow = new JDialogEditMessungLabor();
     jDialogRow.setLocationRelativeTo(this);
     
     rowAddButton.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            addRow();
            repaint();
         }
     });
     
     rowsPropertiesButton.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            updateRow();
            repaint();
         }
     });
     
     rowsRemoveButton.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
           JOptionPane pane = new JOptionPane("Do you really want to delete the selected rows ?",JOptionPane.WARNING_MESSAGE,JOptionPane.OK_CANCEL_OPTION);
           JDialog dialog = pane.createDialog(JPanelTableEditLab.this, "Goat");
           dialog.show();
            Integer i = (Integer)pane.getValue();
            if (i.intValue() == JOptionPane.OK_OPTION) {
               int[] r = sorter.getRows(table.getSelectedRows());                                
               ((EditableObjektTable)sorter.getModel()).removeRows(objektNode,r);               
               repaint();
            }
            
         }
     });
        
    }
    
    private void addRow(){
        jDialogRow.setTitle("Add row");
        jDialogRow.initForm();
        jDialogRow.show();
        
        if (jDialogRow.isOkPressed()){          
            try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Vector row = new Vector();
            row.add(jDialogRow.getStatus());
            row.add(jDialogRow.getVon());
            row.add(jDialogRow.getBis());
            row.add(jDialogRow.getWert());
            row.add(jDialogRow.getLbnr());
            row.add(jDialogRow.getGen());
            row.add(jDialogRow.getNachw());
            row.add(jDialogRow.getBem());

            if (((EditableObjektTable)sorter.getModel()).addRow(objektNode,row)){
                int last_index = sorter.getRowCount() -1 ;
                table.getSelectionModel().setSelectionInterval(last_index,last_index);
                Rectangle rect =  table.getCellRect(last_index,sorter.getColumnCount() -1 , true);
                table.scrollRectToVisible(rect);
                               
            }
 
            } finally {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }
   }
    
     private void updateRow(){
           jDialogRow.initForm();
           
           int[] r = table.getSelectedRows();
           int[] s = sorter.getRows(r);
           
           
           if (r.length == 1) {
             jDialogRow.setManyValues(false);
             jDialogRow.setTitle("Update row");                        
             jDialogRow.setStatus((Integer)(table.getValueAt(r[0],1)));            
             jDialogRow.setVon((java.util.Date)table.getValueAt(r[0],2));
             jDialogRow.setBis((java.util.Date)table.getValueAt(r[0],3));
             jDialogRow.setWert((Double)table.getValueAt(r[0],4));
             jDialogRow.setLbnr((String)table.getValueAt(r[0],5));
             jDialogRow.setGen((Double)table.getValueAt(r[0],6));
             jDialogRow.setNachw((Double)table.getValueAt(r[0],7));
             jDialogRow.setBem((String)table.getValueAt(r[0],8));
             
           } else {
             jDialogRow.setTitle("Update rows");           
             jDialogRow.setManyValues(true);
           }
           
           jDialogRow.show();
           if (jDialogRow.isOkPressed()){          
            try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Vector row = new Vector();
            if (r.length == 1){
               row.add(jDialogRow.getStatus());
               row.add(jDialogRow.getVon());
               row.add(jDialogRow.getBis());
               row.add(jDialogRow.getWert());
               row.add(jDialogRow.getLbnr());
               row.add(jDialogRow.getGen());
               row.add(jDialogRow.getNachw());
               row.add(jDialogRow.getBem());
               ((EditableObjektTable)sorter.getModel()).updateRow(objektNode,s[0],row);
            } else {
               row.add(jDialogRow.getStatus());
               ((EditableObjektTable)sorter.getModel()).updateRows(objektNode,s,row);    

            }
            } finally {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
           }
       
     }
    
}
