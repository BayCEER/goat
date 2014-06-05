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

import de.unibayreuth.bayeos.goat.ObjektIconFactory;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;

/**
 *
 * @author  oliver
 */
public class ObjektNodeTableCellRenderer extends DefaultTableCellRenderer {
    
    public ObjektNodeTableCellRenderer() {
        setHorizontalAlignment(JLabel.LEFT);        
    }

    public Component getTableCellRendererComponent(JTable table, Object value, 
    boolean isSelected, boolean hasFocus,int row, int col) {
        
        if (value instanceof ObjektNode){
           ObjektNode node = (ObjektNode) value;
           setIcon(ObjektIconFactory.getIcon(node.getObjektart(),false));
           setText(node.getDe());
           if (isSelected) {
            setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
            } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
           
        }        
        return this;
    }
}
