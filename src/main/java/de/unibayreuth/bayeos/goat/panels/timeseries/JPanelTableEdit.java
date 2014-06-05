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
 * JPanelTableEdit.java
 *
 * Created on 29. Juni 2004, 15:49
 */

package de.unibayreuth.bayeos.goat.panels.timeseries;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import de.unibayreuth.bayeos.goat.tree.ObjektNode;

/**
 *
 * @author  oliver
 */
public class JPanelTableEdit extends JPanelTable {
    public final static String ACTION_ROW_ADD = "row_add";
    public final static String ACTION_ROWS_REMOVE = "rows_remove";
    public final static String ACTION_ROWS_PROPERTIES = "rows_properties";
    
    protected AbstractButton rowAddButton, rowsRemoveButton, rowsPropertiesButton;
    
    protected boolean editable;
    
    
    
    /** Creates a new instance of JPanelTableEdit */
    public JPanelTableEdit() {
        
        toolBarData.addSeparator();
        rowAddButton = new JButton();
        prepareButton(rowAddButton, ACTION_ROW_ADD, "de/unibayreuth/bayeos/goat/New16.gif","Add a new row to the table ...");
        toolBarData.add(rowAddButton);
        
        rowsRemoveButton = new JButton();
        prepareButton(rowsRemoveButton, ACTION_ROWS_REMOVE, "de/unibayreuth/bayeos/goat/Delete16.gif","Remove the selected rows ...");
        toolBarData.add(rowsRemoveButton);     
        
        rowsPropertiesButton = new JButton();
        prepareButton(rowsPropertiesButton, ACTION_ROWS_PROPERTIES, "de/unibayreuth/bayeos/goat/Properties16.gif","Update the selected rows ...");
        toolBarData.add(rowsPropertiesButton);
        
        
    }
    
    
    
    public void setEditable(boolean value){
        this.editable = value;
        rowAddButton.setEnabled(value);
        rowsRemoveButton.setEnabled(value);
        rowsPropertiesButton.setEnabled(value);
    }
    
    /**
     * Getter for property writable.
     * @return Value of property writable.
     */
    public boolean isEditable() {
        return editable;
    }    
    
    /**
     * Getter for property rowAddButton.
     * @return Value of property rowAddButton.
     */
    public javax.swing.AbstractButton getRowAddButton() {
        return rowAddButton;
    }    
    
    /**
     * Setter for property rowAddButton.
     * @param rowAddButton New value of property rowAddButton.
     */
    public void setRowAddButton(javax.swing.AbstractButton rowAddButton) {
        this.rowAddButton = rowAddButton;
    }
    
    public void setObjektNode(ObjektNode objektNode) {
        super.setObjektNode(objektNode);
        setEditable(objektNode.getCheck_write().booleanValue());
    }    
    
    public void setModel(javax.swing.table.TableModel m) {
        super.setModel(m);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
		public void valueChanged(ListSelectionEvent e) {
                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    rowsPropertiesButton.setEnabled(!lsm.isSelectionEmpty() && editable);
                    rowsRemoveButton.setEnabled(!lsm.isSelectionEmpty() && editable);
                }

           }); 
           
        sorter.getModel().addTableModelListener(new TableModelListener(){
           public void tableChanged(TableModelEvent e){
               sorter.reallocateIndexes();
               sorter.fireTableChanged(e);
           }
        });
       
    }
    
}
