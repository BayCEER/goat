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
 * StatusItem.java
 *
 * Created on 18. November 2003, 09:23
 */

package de.unibayreuth.bayeos.goat.chart;

import java.awt.Color;
import java.util.prefs.Preferences;

import de.unibayreuth.bayeos.goat.JMainFrame;



/**
 *
 * @author  oliver
 */
public class StatusColors {
    
    private static final Preferences pref  = Preferences.userNodeForPackage(JMainFrame.class);;    
    
    //  Status Colors
    public static Color getNullColor(){
        return new Color(pref.getInt("status_color_null",Color.BLACK.getRGB()));
    }
    
    public static Color getInvalidColor(){
        return new Color(pref.getInt("status_color_invalid",Color.RED.getRGB()));
    }
    
    public static Color getOkColor(){
        return new Color(pref.getInt("status_color_ok",Color.GREEN.getRGB()));
    }

    public static Color getOthersColor(){
        return new Color(pref.getInt("status_color_others",Color.GRAY.getRGB()));
    }
    
    public static void setNullColor(Color c){
        pref.putInt("status_color_null", c.getRGB());
    }
    
    public static void setInvalidColor(Color c){
        pref.putInt("status_color_invalid", c.getRGB());
    }
    
    public static void setOkColor(Color c){
        pref.putInt("status_color_ok", c.getRGB());
    }
    
    public static void setOthersColor(Color c){
        pref.putInt("status_color_others", c.getRGB());
    }
    
}
