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
package de.unibayreuth.bayeos.goat.list;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.ListCellRenderer;

public class ComboBoxVectorRenderer extends JLabel implements ListCellRenderer {
    private int columnIndex;
        
    public ComboBoxVectorRenderer(int columnIndex){
        super();
        setOpaque(true);
        this.columnIndex = columnIndex;
    }
    
    public java.awt.Component getListCellRendererComponent(javax.swing.JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(((Vector)value).elementAt(columnIndex).toString());
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }
    
}
