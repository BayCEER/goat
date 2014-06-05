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
 * IDTableRenderer.java
 *
 * Created on 6. Mai 2004, 13:46
 */

package de.unibayreuth.bayeos.goat.table;

import java.util.HashMap;

import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author  oliver
 */

public class IDTableRenderer extends DefaultTableCellRenderer {    
    private HashMap map;
    
    public IDTableRenderer(HashMap map){        
        super();
        this.map = map;
    }
    
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable tab, Object value, boolean isSelected, boolean hasFocus, int row, int column) {          
        if (isSelected) {
            setForeground(tab.getSelectionForeground());
            super.setBackground(tab.getSelectionBackground());
        } else {
            setForeground(tab.getForeground());
            setBackground(tab.getBackground());
        }
        if (value!=null) {
          setText((String)map.get(value));
        } else {
          setText("");
        }
        return this;
    }
    
}

