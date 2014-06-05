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
 * JDefaultDateSpinner.java
 *
 * Created on 15. November 2002, 13:57
 */

package de.unibayreuth.bayeos.utils;

import java.util.Calendar;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

/**
 *
 * @author  oliver
 */
public class JDefaultDateSpinner extends JSpinner {
    
    private SpinnerDateModel model;
    
    private JSpinner.DateEditor editor;
       
    /** Creates a new instance of DefaultDateSpinner */
    public JDefaultDateSpinner() {
      model = new SpinnerDateModel();
      model.setCalendarField(Calendar.DAY_OF_YEAR);
      setModel(model);
      editor = new JSpinner.DateEditor(this);
      setEditor(editor);
    }
    
    public void setDate(java.util.Date date) {
        model.setValue(date);
    }

    public java.util.Date getDate() {
        return model.getDate();
    }
    
}
