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
 * UpdateEvent.java
 *
 * Created on 15. Dezember 2003, 12:21
 */

package de.unibayreuth.bayeos.utils.date;

import java.util.EventObject;

/**
 *
 * @author  oliver
 */
public class DateChangedEvent extends EventObject{
    
    /** Creates a new instance of UpdateEvent */
    public DateChangedEvent(Object source) {
        super(source);
    }
    
}
