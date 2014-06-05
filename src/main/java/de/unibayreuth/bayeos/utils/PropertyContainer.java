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
 * PropertyContainer.java
 *
 * Created on February 26, 2002, 8:46 PM
 */

package de.unibayreuth.bayeos.utils;

/**
 * An interface for a simple implementation of the property container
 * pattern, documented in SanFrancisco Design Patterns.
 * @author  pmonday@stereobeacon.com
 */
public interface PropertyContainer {
   
    /**
     * Retrieve a value by a particular token.
     * @param token is a key that can be used to retrieve the value
     * @return Object is the value associated with the token.  It
     *    will not be null.
     */
    public Object getPropertyBy(String token);
    
    /**
     * Retrieve all property keys currently in use.
     * @return String[] is an array of all valid token names.
     */
    public String[] getPropertyKeys();
    
}
