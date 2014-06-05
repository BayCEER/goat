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
 * ImageFactory.java
 *
 * Created on 29. Mai 2002, 14:52
 */

package de.unibayreuth.bayeos.utils;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;


/**
 *
 * @author  oliver
 */
public final class ImageFactory {
    
    protected final static Logger logger = Logger.getLogger("ImageFactory.class");
    
    private static ImageFactory imageFactory = null;
    private static ClassLoader classLoader = null;
    
    /** Never create a new instance of ImageFactory */
    private ImageFactory() {
        classLoader = this.getClass().getClassLoader();
    }
    
    public static Image getImage(String resource) throws ImageFactoryException {
        try {
        checkFactory();
        logger.debug("Constructing Image from Resource: " + resource);
        return new ImageIcon(classLoader.getResource(resource)).getImage();
        } catch(Exception e) {
            throw new ImageFactoryException(e.getMessage(),e);
        }
    }
    
    public static Icon getIcon(String resource) throws ImageFactoryException {
        try {
        checkFactory();
        logger.debug("Constructing Icon from Resource: " + resource);
        return new ImageIcon(classLoader.getResource(resource));
        } catch (Exception e) {
            throw new ImageFactoryException(e.getMessage(),e);
        }
    }
    
    private static void checkFactory(){
         if (imageFactory == null) {
             imageFactory = new ImageFactory();
         }
     }

}
