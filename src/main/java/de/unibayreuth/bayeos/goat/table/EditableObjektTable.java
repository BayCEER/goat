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
 * Deleteable.java
 *
 * Created on 31. Oktober 2002, 08:31
 */

package de.unibayreuth.bayeos.goat.table;

import java.util.Vector;

import de.unibayreuth.bayeos.goat.tree.ObjektNode;

/**
 *
 * @author  oliver
 */
public interface EditableObjektTable extends ObjektTable {
    
    public boolean removeRows(final ObjektNode objektNode ,int[] rows) ;
    
    public boolean addRow(final ObjektNode objektNode ,final Vector values) ;
    
    public boolean updateRows(final ObjektNode objektNode ,final int rows[], final Vector values) ;
    
    public boolean updateRow(final ObjektNode objektNode, final int row, final Vector values);
    
}
