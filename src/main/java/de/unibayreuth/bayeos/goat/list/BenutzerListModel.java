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
 * BenutzerListModel.java
 *
 * Created on 12. Dezember 2002, 13:17
 */

package de.unibayreuth.bayeos.goat.list;

import java.io.IOException;
import java.util.Vector;

import javax.swing.DefaultListModel;

import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektFactory;

/**
 *
 * @author  oliver
 */
public class BenutzerListModel extends DefaultListModel {
    
    private Vector benutzer = new Vector();
        
    /** Creates a new instance of BenutzerListModel */
    public BenutzerListModel(XmlRpcClient xmlClient) throws XmlRpcException, IOException {
        benutzer = (Vector)xmlClient.execute("LookUpTableHandler.getGruppen", new Vector());
        benutzer.addAll((Vector)xmlClient.execute("LookUpTableHandler.getBenutzer", new Vector()));
    }
    
    public Object getElementAt(final int index) {
        return (Object)ObjektFactory.getObjekt((Vector)benutzer.elementAt(index));
    }
    
    public int getSize() {
        return benutzer.size();
    }
}
