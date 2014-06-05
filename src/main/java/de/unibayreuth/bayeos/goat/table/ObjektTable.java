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
 * Executable.java
 *
 * Created on 31. Oktober 2002, 08:31
 */

package de.unibayreuth.bayeos.goat.table;

import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.StatusFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.TimeFilter;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;


/**
 *
 * @author  oliver
 */
public interface ObjektTable {
    
   public boolean load(final ObjektNode objekt, final TimeFilter tFilter, final StatusFilter sFilter) ;

}
