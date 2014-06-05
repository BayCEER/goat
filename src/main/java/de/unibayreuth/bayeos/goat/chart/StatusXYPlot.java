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
 * StatusXYPlot.java
 *
 * Created on 14. Mai 2003, 09:43
 */

package de.unibayreuth.bayeos.goat.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.prefs.Preferences;

import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.XYItemRenderer;
import org.jfree.data.XYDataset;

import de.unibayreuth.bayeos.goat.JMainFrame;

/**
 *
 * @author  oliver
 */
public class StatusXYPlot extends XYPlot {
    LegendItemCollection legendcol = null;
    
    /** Creates a new instance of StatusXYPlot */
    public StatusXYPlot(XYDataset data,
                  ValueAxis domainAxis,
                  ValueAxis rangeAxis,
                  XYItemRenderer renderer) {
                  super(data,domainAxis,rangeAxis,renderer);
        legendcol = new LegendItemCollection();             
        Preferences pref = Preferences.userNodeForPackage(JMainFrame.class);
        addLegendItem("not set",StatusColors.getNullColor());
        addLegendItem("valid",StatusColors.getOkColor());
        addLegendItem("invalid",StatusColors.getInvalidColor());
        addLegendItem("others",StatusColors.getOthersColor());
                
    }
    
    private void addLegendItem(String text, Color col){
        
       
        legendcol.add(new LegendItem(text,text,new Rectangle(10,10),true,col,col, new BasicStroke(2.0f)));
        
    }
    
    /** Returns the legend items for the plot.
     *
     * @return the legend items.
     *
     */
    public LegendItemCollection getLegendItems() {
        return legendcol;
    }
    
    
}
