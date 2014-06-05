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
 * ObjektArtListCellRenderer.java
 *
 * Created on 14. November 2002, 16:22
 */

package de.unibayreuth.bayeos.goat.list;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import de.unibayreuth.bayeos.goat.ObjektIconFactory;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;

/**
 *
 * @author  oliver
 */
public class ObjektNodeListCellRenderer extends DefaultListCellRenderer  {
   
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list,value, index, isSelected, cellHasFocus);
        ObjektNode o = (ObjektNode)(value);
        setText(o.getDe());
        setIcon(ObjektIconFactory.getIcon(o.getObjektart(),false));
        if (isSelected) {
            setForeground(list.getSelectionForeground());
            super.setBackground(list.getSelectionBackground());
        } else {
            setForeground(list.getForeground());
            setBackground(list.getBackground());
        }
        return this;            
    }
    
}
