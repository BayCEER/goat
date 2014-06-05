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
 * Created on 11. November 2002, 16:26
 */

package de.unibayreuth.bayeos.goat.chart;

import java.awt.geom.GeneralPath;
import java.util.prefs.Preferences;

import org.jfree.chart.labels.TimeSeriesToolTipGenerator;
import org.jfree.chart.renderer.DefaultXYItemRenderer;
import org.jfree.chart.renderer.StandardXYItemRenderer;

import de.unibayreuth.bayeos.goat.JMainFrame;

/**
 *
 * @author  oliver
 */
public class DefaultItemRenderer extends StandardXYItemRenderer {
        
    private final static Preferences pref  = Preferences.userNodeForPackage(JMainFrame.class);
    private static GeneralPath p;
    
    
   
    /** Creates a new instance of StatusRenderer */
    public DefaultItemRenderer() {
        super();
        float size = (float)(pref.getInt("shapesize", 8)/2);
        
        p = new GeneralPath(GeneralPath.WIND_NON_ZERO);
                p.moveTo(- size, size/4);
                p.lineTo( -size/4, size/4);
                p.lineTo( -size/4, size);
                p.lineTo( +size/4, size);
                p.lineTo( +size/4, size/4);
                p.lineTo( +size, size/4);
                p.lineTo( +size, -size/4);
                p.lineTo( +size/4, -size/4);
                p.lineTo( +size/4, -size);
                p.lineTo( -size/4, -size);
                p.lineTo( -size/4, -size/4);
                p.lineTo( -size, -size/4);
                p.closePath();
        setShape(p);
        int rend = pref.getInt("xyitemrenderer",DefaultXYItemRenderer.LINES);
        if (rend == DefaultXYItemRenderer.LINES) {
           setPlotLines(true);
           setPlotShapes(false);
        } else if (rend == DefaultXYItemRenderer.SHAPES_AND_LINES) {
           setPlotLines(true);
           setPlotShapes(true);
       } else if (rend == DefaultXYItemRenderer.SHAPES){
           setPlotLines(false);
           setPlotShapes(true);
       }

        
        if (pref.getBoolean("charttooltips",false)) {
            setToolTipGenerator(new TimeSeriesToolTipGenerator());
        } else {
            setToolTipGenerator(null);
            
        }
        
        setGapThreshold(pref.getDouble("chartthreshold",0.0));
       
    }
    
    
    
        
    
}


