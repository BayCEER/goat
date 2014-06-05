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
 * PropertyContainerImpl.java
 *
 * Created on February 26, 2002, 8:56 PM
 */

package de.unibayreuth.bayeos.utils;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author  default
 */
public abstract class PropertyContainerImpl 
    implements PropertyContainer, Serializable 
{
    
    protected Hashtable ivProperties = new Hashtable(2);
    
    /** Creates a new instance of PropertyContainerImpl */
    public PropertyContainerImpl() {
    }
    
    /**
     * Add a property associated with a token name.
     * If the token already exists, the value will be replaced.
     * If the token does not exist, it will be added with the value.
     * @param value is an object that cannot be null
     * @param token is a key that can be used to retrieve the value
     */
    public void addPropertyBy(Object value, String token) {
        if(value==null || token==null) return;
        if(ivProperties.containsKey(token)){
            ivProperties.remove(token);
        }
        ivProperties.put(token, value);
    }
    
    /**
     * Retrieve a value by a particular token.
     * @param token is a key that can be used to retrieve the value
     * @return Object is the value associated with the token.  It
     *   will not be null.
     */
    public Object getPropertyBy(String token) {
        if(token==null) return null;
        return ivProperties.get(token);
    }
    
    /**
     * Retrieve all property keys currently in use.
     * @return String[] is an array of all valid token names.
     */
    public String[] getPropertyKeys() {
        String keys[] = null;
        synchronized(ivProperties){
            int s = ivProperties.size();
            keys = new String[s];
            Enumeration e = ivProperties.keys();
            int i = 0;
            while(e.hasMoreElements()){
                keys[i] = (String)e.nextElement();
                i++;
            }
        }
        return keys;
    }
    
    /**
     * Remove a value associated with a particular token.
     * @param token is a key associated with a value that was added
     */
    public void removeProperty(String token) {
        if(token==null) return;
        ivProperties.remove(token);
    }
    
}
