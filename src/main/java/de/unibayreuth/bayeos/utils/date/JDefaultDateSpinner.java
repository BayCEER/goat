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

package de.unibayreuth.bayeos.utils.date;
import java.util.Calendar;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeListener;

/**
 *
 * @author  oliver
 */
public class JDefaultDateSpinner extends JSpinner implements ChangeListener, DateChanged  {
    
    private SpinnerDateModel model;
    private JSpinner.DateEditor editor;
    // Create the listener list
    protected javax.swing.event.EventListenerList listenerList =
    new javax.swing.event.EventListenerList();
    
    
    /** Creates a new instance of DefaultDateSpinner */
    public JDefaultDateSpinner() {
      model = new SpinnerDateModel();
      model.setCalendarField(Calendar.DAY_OF_YEAR);
      setModel(model);
      editor = new JSpinner.DateEditor(this);
      setEditor(editor);
      addChangeListener(this);
    }       
    
    public void setDate(java.util.Date value) {
        model.setValue((value==null)?new java.util.Date():value);
    }

    public java.util.Date getDate() {
        return model.getDate();
    }
    
    // This methods allows classes to register for TextChangedEvents
    public void addDateChangedListener(DateChangedListener listener) {
        listenerList.add(DateChangedListener.class, listener);
    }
    
    // This methods allows classes to unregister for TextChangedEvents
    public void removeDateChangedListener(DateChangedListener listener) {
        listenerList.remove(DateChangedListener.class, listener);
    }
    
    // This private class is used to fire MyEvents
    public void fireDateChanged(DateChangedEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==DateChangedListener.class) {
                ((DateChangedListener)listeners[i+1]).changed(evt);
            }
        }
    }
        
    public void stateChanged(javax.swing.event.ChangeEvent changeEvent) {
        fireDateChanged(new DateChangedEvent(this));
    }
    
    
}
