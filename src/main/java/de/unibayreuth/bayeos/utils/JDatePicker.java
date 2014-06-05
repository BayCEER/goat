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
 * JDatePicker.java
 *
 * Created on 23. August 2002, 12:07
 */

package de.unibayreuth.bayeos.utils;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.toedter.calendar.JCalendar;

/**
 *
 * @author  oliver
 */
public class JDatePicker extends javax.swing.JDialog implements PropertyChangeListener {
    
    private boolean okPressed ;
    private Calendar calendar;
    private JComponent jComponent;
    private JCalendar jCalendar;
    private JPanel buttonPanel;
    private JButton closeButton;
    
    
    
    private java.util.Date date;
    
    /** Creates a new instance of JDatePicker */
    public JDatePicker(JComponent jComponent, java.util.Date value) {
        super((JDialog)SwingUtilities.getRoot(jComponent));
        this.date = value;    
        setTitle("Please choose a date value");
        setModal(true);
        this.jComponent = jComponent;
        jCalendar = new JCalendar();
        Calendar c = new GregorianCalendar();
        c.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        c.setTime(value);
        jCalendar.setCalendar(c);
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton = new JButton("Ok"));
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okPressed = true;
                setVisible(false);
           }
        });
        
        getContentPane().add(jCalendar,BorderLayout.CENTER);
        getContentPane().add(buttonPanel,BorderLayout.SOUTH);
        pack();
        if (jComponent != null) this.setLocation(jComponent.getLocationOnScreen());
        show();
    }
    
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        System.out.println("Property Changed");
           if (evt.getPropertyName().equals("calendar")) {
                calendar = (Calendar) evt.getNewValue();
           }
    }    
    
    public java.util.Date getDate(){
        return jCalendar.getCalendar().getTime();
    }
    
    public boolean isOkPressed(){
        return okPressed;
        
    }
    
    


        
}
