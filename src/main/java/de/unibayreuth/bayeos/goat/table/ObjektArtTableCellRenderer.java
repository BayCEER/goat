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
 * ObjektArtTableCellRenderer.java
 *
 * Created on 11. Dezember 2002, 10:50
 */

package de.unibayreuth.bayeos.goat.table;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayeos.goat.ObjektIconFactory;

/**
 *
 * @author  oliver
 */
public class ObjektArtTableCellRenderer extends DefaultTableCellRenderer {
    
    private int indexObjektArt ;
    private boolean enabled;

    /** Creates a new instance of ObjektArtTableCellRenderer */
    public ObjektArtTableCellRenderer(int indexObjektArt) {
        this(indexObjektArt,true);
    }

    public ObjektArtTableCellRenderer(int indexObjektArt, boolean enabled) {
        this.indexObjektArt = indexObjektArt;
        this.enabled = enabled;
        setHorizontalAlignment(JLabel.LEFT);        
    }

    public Component getTableCellRendererComponent(JTable table, Object value, 
    boolean isSelected, boolean hasFocus,int row, int col) {
        String oart = (String) table.getModel().getValueAt(row,indexObjektArt);
        setIcon(ObjektIconFactory.getIcon(ObjektArt.get(oart),false));
        setText((String)value);
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        setEnabled(enabled);
        return this;
    }
}
