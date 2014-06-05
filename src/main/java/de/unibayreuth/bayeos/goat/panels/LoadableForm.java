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
package de.unibayreuth.bayeos.goat.panels;

import de.unibayreuth.bayeos.goat.tree.ObjektNode;



/**
 * <p>
 * 
 * @author 
 * </p>
 */
public interface LoadableForm {

    public boolean loadData();
    
    public void setBorderText(String Bezeichung);
    public String getBorderText();
    
    public ObjektNode getObjektNode();
    public void setObjektNode(ObjektNode node);
    
    public void setEditable(boolean enabled);
    public boolean getEditable();
    


} // end DefaultForm





