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
 * JMainOptionPane.java
 * 
 * Eine einfache Klasse zur Ausgahe von
 * Meldungen via JOptionPane 
 * für alle Klassen die nicht von Component abgeleitet
 * werden.
 * Created on 24. Juli 2003, 13:22
 */

package de.unibayreuth.bayeos.utils;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 *
 * @author  oliver
 */
public class MsgBox {
    
    private static Component parentComponent;
    private static MsgBox instance;
    private static String title;
    
    /** Creates a new instance of JMainOptionPane */
    private MsgBox() {
    }
    
    public static MsgBox getInstance() {
        if (instance == null) {
          instance  = new MsgBox();
        };
        return instance;
    }
    
    
    public static void info(String msg){
        info(instance.parentComponent, msg);
    }
    
    public static void info(Component parent, String msg){
        JOptionPane.showMessageDialog(parent,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void warn(String msg){
        warn(instance.parentComponent, msg);
    }
        
    public static void warn(Component parent, String msg){
        JOptionPane.showMessageDialog(parent,msg,title,JOptionPane.WARNING_MESSAGE);
    }
    
    public static void error(String msg){
        error(instance.parentComponent, msg);
    }
    
    public static void error(Component parent, String msg){
        JOptionPane.showMessageDialog(parent,msg,title,JOptionPane.ERROR_MESSAGE);
    }

    public static void setComponent(Component parentComponent){
        getInstance().parentComponent = parentComponent;
    }
    
    public static void setTitle(String title){
        getInstance().title = title;
    }
    
    
    
    
    
}
