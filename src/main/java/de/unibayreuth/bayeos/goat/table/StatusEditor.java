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
 * StatusEditor.java
 *
 * Created on October 21, 2002, 1:42 PM
 */

package de.unibayreuth.bayeos.goat.table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.TableCellEditor;

import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import de.unibayreuth.bayeos.goat.Constants;

/**
 *
 * @author  oliver
 */
public class StatusEditor extends JComboBox implements TableCellEditor, Constants {
    
    private XmlRpcClient xmlClient;
    
    private ArrayList idIndex = new ArrayList();
    
    protected EventListenerList listenerList = new EventListenerList();
    protected ChangeEvent changeevent = new ChangeEvent(this);
    
      
    /** Creates a new instance of StatusEditor */
    public StatusEditor(XmlRpcClient xmlClient, int tableId) throws StatusEditorException {
        this.xmlClient = xmlClient;
        int i = 0;
        try {
        Vector params = new Vector();
        params.add(new Integer(tableId));
        Vector vReturn = (Vector)xmlClient.execute("LookUpTableHandler.getStatus",params);
        Iterator it = vReturn.iterator();
        while(it.hasNext()){
          Vector row = (Vector)it.next();
          Integer id = (Integer)row.elementAt(0);
          String bez = (String)row.elementAt(1);
          addItem(bez);   
          idIndex.add(id);
        }
        setBorder(BorderFactory.createEmptyBorder());
        
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
                fireEditingStopped();
            }
        });
        
        } catch (XmlRpcException e) {
            throw new StatusEditorException("Could not create StatusEditor", e);
        }
        
        
    }
    
    
    protected void fireEditingStopped(){
        CellEditorListener listener;
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length;i++){
            if (listeners[i] == CellEditorListener.class){
                listener = (CellEditorListener) listeners[i+1];
                listener.editingStopped(changeevent);
            }
        }
    }
    
    
    protected void fireEditingCanceled(){
        CellEditorListener listener;
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length;i++){
            if (listeners[i] == CellEditorListener.class){
                listener = (CellEditorListener) listeners[i+1];
                listener.editingCanceled(changeevent);
            }
        }
    }
    
        
    /** Adds a listener to the list that's notified when the editor
     * stops, or cancels editing.
     *
     * @param	l		the CellEditorListener
     *
     */
    public void addCellEditorListener(CellEditorListener l) {
        listenerList.add(CellEditorListener.class,l);
    }
    
    /** Tells the editor to cancel editing and not accept any partially
     * edited value.
     *
     */
    public void cancelCellEditing() {
        fireEditingCanceled();
        
    }
    
    /** Returns the value contained in the editor.
     * @return the value contained in the editor
     *
     */
    public Object getCellEditorValue() {
       return idIndex.get(getSelectedIndex());
    }
    
    /**  Sets an initial <code>value</code> for the editor.  This will cause
     *  the editor to <code>stopEditing</code> and lose any partially
     *  edited value if the editor is editing when this method is called. <p>
     *
     *  Returns the component that should be added to the client's
     *  <code>Component</code> hierarchy.  Once installed in the client's
     *  hierarchy this component will then be able to draw and receive
     *  user input.
     *
     * @param	table		the <code>JTable</code> that is asking the
     * 				editor to edit; can be <code>null</code>
     * @param	value		the value of the cell to be edited; it is
     * 				up to the specific editor to interpret
     * 				and draw the value.  For example, if value is
     * 				the string "true", it could be rendered as a
     * 				string or it could be rendered as a check
     * 				box that is checked.  <code>null</code>
     * 				is a valid value
     * @param	isSelected	true if the cell is to be rendered with
     * 				highlighting
     * @param	row     	the row of the cell being edited
     * @param	column  	the column of the cell being edited
     * @return	the component for editing
     *
     */
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        Integer id = null;
        if (value instanceof Byte) {
            id = new Integer(((Byte)value).intValue());
        } else if (value instanceof Integer){
            id = (Integer)value;
        }
        setSelectedIndex(idIndex.indexOf(id));
        return this;
    }
    
    /** Asks the editor if it can start editing using <code>anEvent</code>.
     * <code>anEvent</code> is in the invoking component coordinate system.
     * The editor can not assume the Component returned by
     * <code>getCellEditorComponent</code> is installed.  This method
     * is intended for the use of client to avoid the cost of setting up
     * and installing the editor component if editing is not possible.
     * If editing can be started this method returns true.
     *
     * @param	anEvent		the event the editor should use to consider
     * 				whether to begin editing or not
     * @return	true if editing can be started
     * @see #shouldSelectCell
     *
     */
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }
    
    /** Removes a listener from the list that's notified
     *
     * @param	l		the CellEditorListener
     *
     */
    public void removeCellEditorListener(CellEditorListener l) {
        listenerList.remove(CellEditorListener.class, l);
    }
    
    /** Returns true if the editing cell should be selected, false otherwise.
     * Typically, the return value is true, because is most cases the editing
     * cell should be selected.  However, it is useful to return false to
     * keep the selection from changing for some types of edits.
     * eg. A table that contains a column of check boxes, the user might
     * want to be able to change those checkboxes without altering the
     * selection.  (See Netscape Communicator for just such an example)
     * Of course, it is up to the client of the editor to use the return
     * value, but it doesn't need to if it doesn't want to.
     *
     * @param	anEvent		the event the editor should use to start
     * 				editing
     * @return	true if the editor would like the editing cell to be selected;
     *    otherwise returns false
     * @see #isCellEditable
     *
     */
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }
    
    /** Tells the editor to stop editing and accept any partially edited
     * value as the value of the editor.  The editor returns false if
     * editing was not stopped; this is useful for editors that validate
     * and can not accept invalid entries.
     *
     * @return	true if editing was stopped; false otherwise
     *
     */
    public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }
    
}
