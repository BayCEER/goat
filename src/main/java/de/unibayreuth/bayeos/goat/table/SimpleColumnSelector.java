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
 * SimpleColumnSelector.java
 *
 * Created on 4. August 2003, 12:41
 */

package de.unibayreuth.bayeos.goat.table;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

/**
 *
 * @author  oliver
 */
public class SimpleColumnSelector extends JTable {
    
    /** Creates a new instance of ColumnSelector */
    public SimpleColumnSelector(TableModel model) {
        super(model);
        setColumnSelectionAllowed(true);
        setRowSelectionAllowed(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
        getTableHeader().addMouseListener(new MouseAdapter(){
           public void mousePressed(MouseEvent e) {
               JTable t = ((JTableHeader)e.getSource()).getTable();
               int i = t.getColumnModel().getColumnIndexAtX(e.getX());
               t.getColumnModel().getSelectionModel().setSelectionInterval(i,i);
           }
        });
    }
    
}
