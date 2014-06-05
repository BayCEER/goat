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
 * StatusRenderer.java
 *
 * Created on 17. Oktober 2002, 15:37
 */



package de.unibayreuth.bayeos.goat.table;

import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.apache.xmlrpc.XmlRpcClient;

import de.unibayreuth.bayeos.utils.LookUpTableFactory;



/**
 *
 * @author  oliver
 */
public class StatusRenderer extends JComboBox implements TableCellRenderer {
    
    private XmlRpcClient xmlClient;
    
        
    private Map idIndex = new HashMap();
    
    /** Creates a new instance of StatusRenderer */
    public StatusRenderer()  {
        int i=0;
        Vector vReturn = LookUpTableFactory.getStatus();
        Iterator it = vReturn.iterator();
        while(it.hasNext()){
          Vector row = (Vector)it.next();
          Integer id = (Integer)row.elementAt(0);
          String bez = (String)row.elementAt(1);
          addItem(bez);   
          idIndex.put(id, new Integer(i++));
        }
        setBorder(BorderFactory.createEmptyBorder());
    }
    
    /**  Returns the component used for drawing the cell.  This method is
     *  used to configure the renderer appropriately before drawing.
     *
     * @param	table		the <code>JTable</code> that is asking the
     * 				renderer to draw; can be <code>null</code>
     * @param	value		the value of the cell to be rendered.  It is
     * 				up to the specific renderer to interpret
     * 				and draw the value.  For example, if
     * 				<code>value</code>
     * 				is the string "true", it could be rendered as a
     * 				string or it could be rendered as a check
     * 				box that is checked.  <code>null</code> is a
     * 				valid value
     * @param	isSelected	true if the cell is to be rendered with the
     * 				selection highlighted; otherwise false
     * @param	hasFocus	if true, render cell appropriately.  For
     * 				example, put a special border on the cell, if
     * 				the cell can be edited, render in the color used
     * 				to indicate editing
     * @param	row	        the row index of the cell being drawn.  When
     * 				drawing the header, the value of
     * 				<code>row</code> is -1
     * @param	column	        the column index of the cell being drawn
     *
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        // Status id 
        int id = 0;
        if (value instanceof Byte) {
         id = ((Byte)value).intValue();    
        } else if (value instanceof Integer) {
         id = ((Integer)value).intValue();    
        }
            
        // Index of combo
        Integer index = (Integer)idIndex.get(new Integer(id));
        setSelectedIndex(index.intValue());
        return this;
    }
    
}
