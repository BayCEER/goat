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
 * JDateField.java
 *
 * Created on 23. August 2002, 10:02
 */

package de.unibayreuth.bayeos.utils;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import de.unibayreuth.bayceer.bayeos.xmlrpc.formats.DateFormat;
import de.unibayreuth.bayeos.goat.Constants;

/**
 *
 * @author  oliver
 */
public class JDateField extends javax.swing.JPanel implements Constants  {
    
    private JFormattedTextField jTextField;
    private JDatePicker jDatePicker;
    private JButton jButton;
    private Calendar calendar;
    
    protected final static Logger logger = Logger.getLogger("JDateField.class");
    
    
    /** Creates a new instance of JDateField */
    public JDateField(String label, java.util.Date date) {
        // setLayout(new BorderLayout());
        add(new JLabel(label,JLabel.CENTER));
        
        jTextField = new JFormattedTextField(DateFormat.defaultDateFormat) ;
        jTextField.setValue(date);
        
        add(jTextField);
        try {
        jButton = new JButton(ImageFactory.getIcon("de/unibayreuth/bitoek/utils/JDayChooserColor16.gif"));
        jButton.setToolTipText("Set date value ...");
        } catch (ImageFactoryException i) {
            logger.warn("Can't set button's icon.");
        }
        jButton.setMargin(new Insets(0,0,0,0));
        add(jButton);
        
        jButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                if (!jTextField.getText().equals("")) {
                    jDatePicker = new JDatePicker(JDateField.this,DateFormat.defaultDateFormat.parse(jTextField.getText()));
                    if (jDatePicker.isOkPressed()){
                      jTextField.setText(DateFormat.defaultDateFormat.format(jDatePicker.getDate()));
                    }
                }
                
                } catch(ParseException ex){
                    logger.debug(ex.getMessage());
                    MsgBox.error(JDateField.this,ex.getMessage());
                }
                
                }
            
        });
        
        
    }
    
    public void setDate(java.util.Date date) {
        jTextField.setText(DateFormat.defaultDateFormat.format(date));        
    }
    
    public java.util.Date getDate() {
        try {
        return DateFormat.defaultDateFormat.parse(jTextField.getText());
        } catch(ParseException e) {
            MsgBox.warn(e.getMessage());
            return null;
        }
    }
        

    
}
