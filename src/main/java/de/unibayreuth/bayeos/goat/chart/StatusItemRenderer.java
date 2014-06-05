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
 * DefaultItemRenderer.java
 *
 * Created on 14. Juli 2004, 12:11
 */

package de.unibayreuth.bayeos.goat.chart;

import java.awt.Color;
import java.awt.Paint;

import javax.swing.table.TableModel;

/**
 *
 * @author  oliver
 */
public class StatusItemRenderer extends DefaultItemRenderer {
    TableModel model;
    int renderColumn = 0;
    private Color nullColor, invalidColor, okColor, othersColor;
    
    /** Creates a new instance of DefaultItemRenderer */
    public StatusItemRenderer(TableModel model, int renderColumn) {
        this.model = model;
        this.renderColumn = renderColumn;
                
        nullColor = StatusColors.getNullColor();
        invalidColor = StatusColors.getInvalidColor();
        okColor = StatusColors.getOkColor();
        othersColor = StatusColors.getOthersColor();
        
    }
    
    public Paint getItemPaint(int series, int item) {
        Object obj = model.getValueAt(item, renderColumn);
        int value = 0;
        if (obj instanceof Integer) {
            value = ((Integer)obj).intValue();
        } if (obj instanceof Byte) {
            value = ((Byte)obj).intValue();
        }
        switch (value) {
            // Noch Kein Status Vergeben
            case 0 : return nullColor;
            // Messwert Okay
            case 1 : 
            case 2 : return okColor;
            // Messwert ungültig
            case 7 : return invalidColor;
            // alle übrigen 
            default : return othersColor;
        }
    }
    
}
