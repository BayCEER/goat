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
 * ObjektHelper.java
 *
 * Created on 12. Februar 2003, 17:59
 */

package de.unibayreuth.bayeos.goat.panels;

import java.util.Vector;

import de.unibayreuth.bayceer.bayeos.objekt.Objekt;
import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;

/**
 *
 * @author  oliver
 */
public interface ObjektHelper {
    
    Objekt getObjekt(Integer Id, ObjektArt objektArt);
    
    Objekt createObjekt(ObjektArt objektArt);
    
    boolean updateObjekt(Integer obj_id,ObjektArt art,Vector attributes);
        
    boolean deleteObjekt(Integer Id);
        
}
