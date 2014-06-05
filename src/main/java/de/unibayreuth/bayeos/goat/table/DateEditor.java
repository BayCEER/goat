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
 * DateEditor.java
 *
 * Created on 3. Dezember 2003, 15:24
 */

package de.unibayreuth.bayeos.goat.table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.TableCellEditor;

import de.unibayreuth.bayceer.bayeos.xmlrpc.formats.DateFormat;
import de.unibayreuth.bayeos.goat.Constants;
import de.unibayreuth.bayeos.utils.MsgBox;

/**
 *
 * @author  oliver
 */
public class DateEditor extends JTextField implements TableCellEditor , Constants {
    
    protected EventListenerList listenerList = new EventListenerList();
    protected ChangeEvent changeEvent = new ChangeEvent(this);
    private static SimpleDateFormat fmt = DateFormat.defaultDateFormat;
        
    public DateEditor() {
        super();
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fireEditingStopped();
            }
        });
    }
    
    public DateEditor(SimpleDateFormat fmt){
        this.fmt = fmt;
    }
    
    public void addCellEditorListener(CellEditorListener listener) {
        listenerList.add(CellEditorListener.class, listener);
    }
    
    public void removeCellEditorListener(CellEditorListener listener) {
        listenerList.remove(CellEditorListener.class, listener);
    }
    
    protected void fireEditingStopped() {
        CellEditorListener listener;
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == CellEditorListener.class) {
                listener = (CellEditorListener) listeners[i + 1];
                listener.editingStopped(changeEvent);
            }
        }
    }
    
    protected void fireEditingCanceled() {
        CellEditorListener listener;
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == CellEditorListener.class) {
                listener = (CellEditorListener) listeners[i + 1];
                listener.editingCanceled(changeEvent);
            }
        }
    }
    
    public void cancelCellEditing() {
        fireEditingCanceled();
    }
    
    public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }
    
    public boolean isCellEditable(EventObject event) {
        return true;
    }
    
    public boolean shouldSelectCell(EventObject event) {
        return true;
    }
    
    public Object getCellEditorValue() {
        try {
          if (getText().length() == 0) {
              return null;
          } else {
              return fmt.parse(getText());    
          }  
        } catch (java.text.ParseException e)   {
            MsgBox.warn(this,"Please enter a valid date format !");
            return null;
        }
    }
    
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value == null){
            setText(null);
        } else {
            setText(fmt.getInstance().format(value));
        }
        
        return this;
    }
    
}


