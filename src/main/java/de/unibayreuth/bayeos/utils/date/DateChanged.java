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
 * TextChangedSupport.java
 *
 * Created on 15. Dezember 2003, 12:28
 */

package de.unibayreuth.bayeos.utils.date;

/**
 *
 * @author  oliver
 */
public interface DateChanged {
    
    // This methods allows classes to register for TextChangedEvents
    public void addDateChangedListener(DateChangedListener listener) ;
    
    // This methods allows classes to unregister for TextChangedEvents
    public void removeDateChangedListener(DateChangedListener listener);
    
    // This private class is used to fire MyEvents
    public void fireDateChanged(DateChangedEvent evt) ;
    
}
