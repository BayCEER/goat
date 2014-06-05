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
package de.unibayreuth.bayeos.goat.panels.attributes;

import java.awt.BorderLayout;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.unibayreuth.bayceer.bayeos.objekt.Mess_Einheit;
import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.goat.panels.UpdatePanel;
import de.unibayreuth.bayeos.utils.MsgBox;

/*
 * dpMessungen.java
 *
 * Created on 10. Februar 2003, 13:35
 */

/**
 *
 * @author  oliver
 */
public class JEinheitPanel extends UpdatePanel {
    
        
    final static Logger logger = Logger.getLogger(JEinheitPanel.class.getName()); 
    
    private EinheitForm frm;
           
    /** Creates new form dpMessungen */
    public JEinheitPanel(JMainFrame app) {       
        super(app);
        frm = new EinheitForm();
        add(frm,BorderLayout.CENTER);
    }
    
    
    public boolean loadData() {
        try {
        Mess_Einheit ein = (Mess_Einheit)helper.getObjekt(objektNode.getId(),objektNode.getObjektart());
        if (ein == null) return false;
        frm.setId(ein.getId());
        frm.setObjektArt(ein.getObjektart());
        frm.setBeschreibung(ein.getBeschreibung());
        frm.setCTime(ein.getCtime());
        frm.setUTime(ein.getUtime());
        frm.setUBenutzer(ein.getUbenutzer());
        frm.setCBenutzer(ein.getCbenutzer());
        boolean enabled = ein.getCheck_write().booleanValue();
        frm.setSymbol(ein.getSymbol());
        frm.setEditable(enabled); // Fields
        setEditable(enabled); // Buttons
        return true;
        } catch (ClassCastException c) {
          logger.error(c.getMessage());
          return false;
        }
    }    
       
   
    
    public boolean updateData() {
        try {
        Vector attrib = new Vector();     
        attrib.add(objektNode.getDe());
        attrib.add(frm.getBeschreibung());
        attrib.add(frm.getSymbol());
        return helper.updateObjekt(frm.getId(),frm.getObjektArt(),attrib);
        } catch (NumberFormatException n) {
            logger.error(n.getMessage());
            MsgBox.warn(JEinheitPanel.this,"Wrong number format.");
            logger.warn(n.getMessage());
            return false;
        }
       
        
    }
        
        
        
        
        
    
   
}
